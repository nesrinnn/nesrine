package com.gpg.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpg.dto.ChartFactureDto;
import com.gpg.dto.DaschboardFactureEtat;
import com.gpg.entities.Facture;
import com.gpg.entities.JwtInfo;
import com.gpg.entities.Payement;
import com.gpg.entities.Requete;
import com.gpg.entities.Status;
import com.gpg.repository.FactureRepository;
import com.gpg.repository.PayementRepository;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.jwtRepository;
import com.gpg.security.JwtAuthorizationFilter;

@Service
public class FactureService {
	@Autowired
	FactureRepository factureRepository;
	@Autowired
	PayementRepository payementRepository;
	@Autowired
	jwtRepository jwtRepository;
	@Autowired
	RequeteRepository requeteRepository;

	// get factures
	public List<Facture> getAll() {

		LocalDateTime currentUtilDate = LocalDateTime.now();
		List<Facture> factures = factureRepository.findAll();
		for (Facture facture : factures) {
			List<Payement> list = facture.getPayements();
		
			if(list.size()>0) {
			for (Payement payement : list) {
				LocalDate local = payement.getDate_echeance();

				if (payement.getStatus() == Status.Non_Payee) {

					System.out.println(currentUtilDate.getDayOfMonth() > local.getDayOfMonth());
					if (local.getYear() < currentUtilDate.getYear()) {
						payement.setTest(true);
						payementRepository.save(payement);
					} else if (local.getMonthValue() < currentUtilDate.getMonthValue()
							& local.getYear() == currentUtilDate.getYear()) {
						payement.setTest(true);
						payementRepository.save(payement);
					} else if (local.getMonthValue() == currentUtilDate.getMonthValue()
							& local.getYear() == currentUtilDate.getYear()
							& ((local.getDayOfMonth() - currentUtilDate.getDayOfMonth() <= 5
									&& currentUtilDate.getDayOfMonth() < local.getDayOfMonth())
									|| currentUtilDate.getDayOfMonth() > local.getDayOfMonth())) {
						payement.setTest(true);
						payementRepository.save(payement);
					}

				}

			}
			}
			
		}

		JwtInfo jwtInfo = jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete)
				.orElseThrow(() -> new IllegalArgumentException("jwt not found"));
		Requete requete = new Requete(JwtAuthorizationFilter.urlRequete, currentUtilDate,
				"consulter la liste de tous les factures", jwtInfo);

		requeteRepository.save(requete);
		List<Facture>  facture1=new ArrayList<>();
		for (Facture facture : factures) {
			List<Payement> list = facture.getPayements();
			
			if(list.size()>0) {
				facture1.add(facture);
			}}
			

		return facture1;
	}

	// status de facture
	public void updateAll() {
		List<Facture> factures = factureRepository.findAll();

		for (Facture facture : factures) {
			  double x=0;
			List<Payement> payements = facture.getPayements();
			  for(Payement payement:payements) {
	               x=x+payement.getMontant();
                     }

			long i = payements.stream().filter(c -> c.getStatus().equals(Status.Payee)).count();

			if (i == payements.size() & x==facture.getTotTtc()) {

				facture.setStatus(Status.Payee);
				factureRepository.save(facture);
			} else if ((i > 0 & i != payements.size()) || (i>0 & x<facture.getTotTtc())) {
				facture.setStatus(Status.ENCOURS);
				factureRepository.save(facture);

			} else {
				facture.setStatus(Status.Non_Payee);
				factureRepository.save(facture);
			}

		}
		
		

	}

	public Optional<List<Payement>> getPayement(Long id) {

		List<Facture> list = factureRepository.findAll();

		return list.stream().filter(c -> (c.getId() == id)).map(Facture::getPayements).findAny();
	}

	// set status de payement
	public void setStatusPay(Long id) {
		LocalDateTime currentUtilDate = LocalDateTime.now();
		Payement payement = payementRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id not found"));
		payement.setStatus(Status.Payee);
		payement.setTest(false);
		JwtInfo jwtInfo = jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete)
				.orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
		Requete requete = new Requete(JwtAuthorizationFilter.urlRequete, currentUtilDate,
				"changer le status d'une ligne de paiement", jwtInfo);
		requeteRepository.save(requete);
		payementRepository.save(payement);

	}

	// late de facture
	public void updateLate() {
		LocalDate currentUtilDate = LocalDate.now();
		List<Facture> list = factureRepository.findAll();
		for (Facture facture : list) {

			LocalDate dateEch = facture.getDate_echeance();
			if (facture.getStatus() == Status.Non_Payee || facture.getStatus() == Status.ENCOURS) {

				if (facture.isLate() == false) {
					if (dateEch.getYear() < currentUtilDate.getYear()) {
						// System.out.println(8);
						facture.setLate(true);
						factureRepository.save(facture);
					} else if (dateEch.getYear() == currentUtilDate.getYear()
							& (dateEch.getMonthValue() < currentUtilDate.getMonthValue())) {
						// System.out.println(9);
						facture.setLate(true);
						factureRepository.save(facture);
					} else if (dateEch.getYear() == currentUtilDate.getYear()
							& dateEch.getMonthValue() == currentUtilDate.getMonthValue()
							& dateEch.getDayOfMonth() < currentUtilDate.getDayOfMonth()) {
						// System.out.println(10);
						facture.setLate(true);
						factureRepository.save(facture);
					}

				}
			}
			// else if()

		}

	}

	public List<DaschboardFactureEtat> chartFactureEtats(Long year) {
        ChartFactureDto chart = new ChartFactureDto("en cours",0);
        ChartFactureDto chart1 = new ChartFactureDto("non payée",0);
        ChartFactureDto chart2 = new ChartFactureDto("payée",0);
    	List<ChartFactureDto> listfinalll = new ArrayList<>();
    	listfinalll.add(chart1);
    	listfinalll.add(chart);listfinalll.add(chart2);
		List<DaschboardFactureEtat> listfinall = new ArrayList<>();
		DaschboardFactureEtat janvier = new DaschboardFactureEtat("janvier", listfinalll);
		DaschboardFactureEtat fevrier = new DaschboardFactureEtat("janvier", listfinalll);
		DaschboardFactureEtat mars = new DaschboardFactureEtat("mars", listfinalll);
		DaschboardFactureEtat avril = new DaschboardFactureEtat("avril", listfinalll);
		DaschboardFactureEtat mai = new DaschboardFactureEtat("mai", listfinalll);
		DaschboardFactureEtat juin = new DaschboardFactureEtat("juin", listfinalll);
		DaschboardFactureEtat juillet = new DaschboardFactureEtat("juillet", listfinalll);
		DaschboardFactureEtat aout = new DaschboardFactureEtat("aout", listfinalll);
		DaschboardFactureEtat septembre = new DaschboardFactureEtat("septembre", listfinalll);
		DaschboardFactureEtat octobre = new DaschboardFactureEtat("octobre", listfinalll);
		DaschboardFactureEtat novembre = new DaschboardFactureEtat("novembre", listfinalll);
		DaschboardFactureEtat decembre = new DaschboardFactureEtat("decembre", listfinalll);
		List<Facture> list1 = new ArrayList<>();
		List<Facture> list2 = new ArrayList<>();
		List<Facture> list3 = new ArrayList<>();
		List<Facture> list4 = new ArrayList<>();
		List<Facture> list5 = new ArrayList<>();
		List<Facture> list6 = new ArrayList<>();
		List<Facture> list7 = new ArrayList<>();
		List<Facture> list8 = new ArrayList<>();
		List<Facture> list9 = new ArrayList<>();
		List<Facture> list10 = new ArrayList<>();
		List<Facture> list11 = new ArrayList<>();
		List<Facture> list12 = new ArrayList<>();
		List<Facture> list = factureRepository.findAll();

		ChartFactureDto payee1 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours1 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee1 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee2 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours2 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee2 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee3 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours3 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee3 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee4 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours4 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee4 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee5 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours5 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee5 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee6 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours6 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee6 = new ChartFactureDto("non payée", 0);

		ChartFactureDto payee7 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours7 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee7 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee8 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours8 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee8 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee9 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours9 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee9 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee10 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours10 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee10 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee11 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours11 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee11 = new ChartFactureDto("non payée", 0);
		ChartFactureDto payee12 = new ChartFactureDto("payée", 0);
		ChartFactureDto enCours12 = new ChartFactureDto("en cours", 0);
		ChartFactureDto nonPayee12 = new ChartFactureDto("non payée", 0);

		for (Facture facture : list) {
			if (facture.getDate_facture().getMonthValue() == 1) {
				list1.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 2) {
				list2.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 3) {
				list3.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 4) {
				list4.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 5) {
				list5.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 6) {
				list6.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 7) {
				list7.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 8) {

				list8.add(facture);

			} else if (facture.getDate_facture().getMonthValue() == 9) {
				list9.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 10) {
				list10.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 11) {
				list11.add(facture);
			} else if (facture.getDate_facture().getMonthValue() == 12) {
				list12.add(facture);
			}
		}
		
		for (Facture facture : list1) {
			List<ChartFactureDto> listfinal1 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {
			

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours1.setValue(enCours1.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee1.setValue(nonPayee1.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee1.setValue(payee1.getValue() + 1);

			}}
			listfinal1.add(nonPayee1);
			listfinal1.add(enCours1);
			listfinal1.add(payee1);
			janvier.setSeries(listfinal1);
		}
		for (Facture facture : list2) {
			
			List<ChartFactureDto> listfinal2 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {
			
			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours2.setValue(enCours2.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee2.setValue(nonPayee2.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee2.setValue(payee2.getValue() + 1);

			}}
			listfinal2.add(nonPayee2);
			listfinal2.add(enCours2);
			listfinal2.add(payee2);
			fevrier.setSeries(listfinal2);
		}
		for (Facture facture : list3) {
			
			List<ChartFactureDto> listfinal3 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours3.setValue(enCours3.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee3.setValue(nonPayee3.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee3.setValue(payee3.getValue() + 1);

			}}
			listfinal3.add(nonPayee3);
			listfinal3.add(enCours3);
			listfinal3.add(payee3);
			mars.setSeries(listfinal3);
		}
		for (Facture facture : list4) {
		
			List<ChartFactureDto> listfinal4 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours4.setValue(enCours4.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee4.setValue(nonPayee4.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee4.setValue(payee4.getValue() + 1);

			}}
			listfinal4.add(nonPayee4);
			listfinal4.add(enCours4);
			listfinal4.add(payee4);
			avril.setSeries(listfinal4);
		}
		for (Facture facture : list5) {
			
			List<ChartFactureDto> listfinal5 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {
			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours5.setValue(enCours5.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee5.setValue(nonPayee5.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee5.setValue(payee5.getValue() + 1);

			}}
			listfinal5.add(nonPayee5);
			listfinal5.add(enCours5);
			listfinal5.add(payee5);
			mai.setSeries(listfinal5);
		}
		for (Facture facture : list6) {
			
			List<ChartFactureDto> listfinal6 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours6.setValue(enCours6.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee6.setValue(nonPayee6.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee6.setValue(payee6.getValue() + 1);

			}}
			listfinal6.add(nonPayee6);
			listfinal6.add(enCours6);
			listfinal6.add(payee6);
			juin.setSeries(listfinal6);
		}
		for (Facture facture : list7) {
		
			List<ChartFactureDto> listfinal7 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours7.setValue(enCours7.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee7.setValue(nonPayee7.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee7.setValue(payee7.getValue() + 1);

			}}
			listfinal7.add(nonPayee7);
			listfinal7.add(enCours7);
			listfinal7.add(payee7);
			juillet.setSeries(listfinal7);
		}
		for (Facture facture : list12) {

			List<ChartFactureDto> listfinal12 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours12.setValue(enCours12.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee12.setValue(nonPayee12.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee12.setValue(payee12.getValue() + 1);

			}}
			listfinal12.add(nonPayee12);
			listfinal12.add(enCours12);
			listfinal12.add(payee12);
			decembre.setSeries(listfinal12);
		}
		for (Facture facture : list8) {
			

			List<ChartFactureDto> listfinal8 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours8.setValue(enCours8.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {
		
				nonPayee8.setValue(nonPayee8.getValue() + 1);
				
			} else if (facture.getStatus().equals(Status.Payee)) {
				
				payee8.setValue(payee8.getValue() + 1);
				

			}}
			listfinal8.add(nonPayee8);
			listfinal8.add(enCours8);
			listfinal8.add(payee8);
			aout.setSeries(listfinal8);
		}
		for (Facture facture : list9) {
			

			List<ChartFactureDto> listfinal9 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {
			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours9.setValue(enCours9.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {
				
				nonPayee9.setValue(nonPayee9.getValue() + 1);
				
			} else if (facture.getStatus().equals(Status.Payee)) {
				
				payee9.setValue(payee9.getValue() + 1);
			}

			}
			listfinal9.add(nonPayee9);
			listfinal9.add(enCours9);
			listfinal9.add(payee9);
			septembre.setSeries(listfinal9);
		}
	for (Facture facture : list10) {
			

			List<ChartFactureDto> listfinal10 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours10.setValue(enCours10.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {
				
				nonPayee10.setValue(nonPayee10.getValue() + 1);
				
			} else if (facture.getStatus().equals(Status.Payee)) {
				
				payee10.setValue(payee10.getValue() + 1);
				

			}}
			listfinal10.add(nonPayee10);
			listfinal10.add(enCours10);
			listfinal10.add(payee10);
			octobre.setSeries(listfinal10);
		}
		for (Facture facture : list11) {
			
			List<ChartFactureDto> listfinal11 = new ArrayList<>();
			if (facture.getDate_facture().getYear() == year) {

			if (facture.getStatus().equals(Status.ENCOURS)) {

				enCours11.setValue(enCours11.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Non_Payee)) {

				nonPayee11.setValue(nonPayee11.getValue() + 1);

			} else if (facture.getStatus().equals(Status.Payee)) {

				payee11.setValue(payee11.getValue() + 1);

			}}
			listfinal11.add(nonPayee11);
			listfinal11.add(enCours11);
			listfinal11.add(payee11);
			novembre.setSeries(listfinal11);
		}
		listfinall.add(janvier);
		listfinall.add(fevrier);
		listfinall.add(mars);
		listfinall.add(avril);
		listfinall.add(mai);
		listfinall.add(juin);
		listfinall.add(juillet);
		listfinall.add(aout);
		listfinall.add(septembre);
		listfinall.add(octobre);
		listfinall.add(novembre);
		listfinall.add(decembre);

		return listfinall;
	}

	/*public List<ChartFactureDto> chartFactureMonth(int year) {

		ChartFactureDto janvier = new ChartFactureDto("janvier", 0);
		ChartFactureDto fevrier = new ChartFactureDto("février", 0);
		ChartFactureDto mars = new ChartFactureDto("mars", 0);
		ChartFactureDto avril = new ChartFactureDto("avril", 0);
		ChartFactureDto mai = new ChartFactureDto("mai", 0);
		ChartFactureDto juin = new ChartFactureDto("juin", 0);
		ChartFactureDto juillet = new ChartFactureDto("juillet", 0);
		ChartFactureDto aout = new ChartFactureDto("aout", 0);
		ChartFactureDto septembre = new ChartFactureDto("septembre", 0);
		ChartFactureDto octobre = new ChartFactureDto("octobre", 0);
		ChartFactureDto novembre = new ChartFactureDto("novembre", 0);
		ChartFactureDto decembre = new ChartFactureDto("decembre", 0);
		List<ChartFactureDto> listfinal = new ArrayList<>();

		List<Facture> list = factureRepository.findAll();
		for (Facture facture : list) {

			if (facture.getDate_facture().getYear() == year) {
				if (facture.getDate_facture().getMonthValue() == 1) {
					janvier.setValue(janvier.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 2) {
					fevrier.setValue(fevrier.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 3) {
					mars.setValue(mars.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 4) {
					avril.setValue(avril.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 5) {
					mai.setValue(mai.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 6) {
					juin.setValue(juin.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 7) {
					juillet.setValue(juillet.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 8) {

					aout.setValue(aout.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 9) {
					septembre.setValue(septembre.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 10) {
					octobre.setValue(octobre.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 11) {
					novembre.setValue(novembre.getValue() + 1);
				} else if (facture.getDate_facture().getMonthValue() == 12) {
					decembre.setValue(decembre.getValue() + 1);
				}
			}
		}
		listfinal.add(janvier);
		listfinal.add(fevrier);
		listfinal.add(mars);
		listfinal.add(avril);
		listfinal.add(mai);
		listfinal.add(juin);
		listfinal.add(juillet);
		listfinal.add(aout);
		listfinal.add(septembre);
		listfinal.add(octobre);
		listfinal.add(novembre);
		listfinal.add(decembre);
		return listfinal;
	}*/


  public List<ChartFactureDto> chartPourcentLate(Long year){
	  List<ChartFactureDto> listFinal=new ArrayList<>();
	  ChartFactureDto chart = new ChartFactureDto("Payée & Retard", 0);
	  ChartFactureDto chart1 = new ChartFactureDto("Non payée & Retard", 0);
	  ChartFactureDto chart2 = new ChartFactureDto("Payée & Sans Retard", 0);
	  ChartFactureDto chart3 = new ChartFactureDto("En retard & En Cours ", 0);
	  List<Facture> factures = factureRepository.findAll();
	  for(Facture facture:factures) {	if (facture.getDate_facture().getYear() == year) {
		  if(facture.isLate()==true & facture.getStatus().equals(Status.Non_Payee)) {
			  chart1.setValue(chart1.getValue()+1);	  
			  }
		  else  if(facture.isLate()==true & facture.getStatus().equals(Status.ENCOURS)) {
			  chart3.setValue(chart3.getValue()+1);	  
			  }
		  else  if(facture.isLate()==false & facture.getStatus().equals(Status.Payee)) {
			  chart2.setValue(chart2.getValue()+1);	  
			  }
		  else  if(facture.isLate()==true & facture.getStatus().equals(Status.Payee)) {
			  chart.setValue(chart.getValue()+1);	  
			  }}
	  }
	  listFinal.add(chart2);
	  listFinal.add(chart);
	  listFinal.add(chart3);
	  listFinal.add(chart1);
	  return listFinal;
  }
  
public List<ChartFactureDto> chartPourcentSomme(Long year){
	List<ChartFactureDto> listFinal=new ArrayList<>();
	ChartFactureDto janvier = new ChartFactureDto("janvier", 0);
	ChartFactureDto fevrier = new ChartFactureDto("février", 0);
	ChartFactureDto mars = new ChartFactureDto("mars", 0);
	ChartFactureDto avril = new ChartFactureDto("avril", 0);
	ChartFactureDto mai = new ChartFactureDto("mai", 0);
	ChartFactureDto juin = new ChartFactureDto("juin", 0);
	ChartFactureDto juillet = new ChartFactureDto("juillet", 0);
	ChartFactureDto aout = new ChartFactureDto("aout", 0);
	ChartFactureDto septembre = new ChartFactureDto("septembre", 0);
	ChartFactureDto octobre = new ChartFactureDto("octobre", 0);
	ChartFactureDto novembre = new ChartFactureDto("novembre", 0);
	ChartFactureDto decembre = new ChartFactureDto("decembre", 0);
	List<Payement> list=payementRepository.findAll();
	for(Payement payement:list) {
		if (payement.getDate_echeance().getYear() == year) {
		if(payement.getStatus().equals(Status.Payee)) {
			if(payement.getDate_echeance().getMonthValue()==1) {
				janvier.setValue(janvier.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==2) {
				fevrier.setValue(fevrier.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==3) {
				mars.setValue(mars.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==4) {
				avril.setValue(avril.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==5) {
				mai.setValue(mai.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==6) {
				juin.setValue(juin.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==7) {
				juillet.setValue(juillet.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==8) {
				
				aout.setValue(aout.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==9) {
			
				septembre.setValue(septembre.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==10) {
				octobre.setValue(octobre.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==11) {
				novembre.setValue(novembre.getValue()+payement.getMontant());
			}
			else if(payement.getDate_echeance().getMonthValue()==12) {
				decembre.setValue(decembre.getValue()+payement.getMontant());
			}
		}
	}}
	listFinal.add(janvier);
	listFinal.add(fevrier);
	listFinal.add(mars);
	listFinal.add(avril);
	listFinal.add(mai);
	listFinal.add(juin);
	listFinal.add(juillet);
	listFinal.add(aout);
	listFinal.add(septembre);
	listFinal.add(octobre);
	listFinal.add(novembre);
	listFinal.add(decembre);

	return listFinal;
}



public void savePayement(Payement payement,Long id) {
	Facture facture=factureRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
	List<Payement> list = facture.getPayements();
	list.add(payement);
	payementRepository.save(payement);
	facture.setPayements(list);
	facture.setSommeResteImpaye(facture.getSommeResteImpaye()-payement.getMontant());
	factureRepository.save(facture);
	
	
}
}