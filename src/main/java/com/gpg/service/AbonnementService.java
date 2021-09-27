package com.gpg.service;

import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.gpg.dto.AbonnementDto;
import com.gpg.dto.FactureQDtoOut;
import com.gpg.entities.Abonnement;
import com.gpg.entities.Company;
import com.gpg.entities.Facture_Provider;
import com.gpg.entities.Facture_Quotidienne;
import com.gpg.entities.JwtInfo;
import com.gpg.entities.Payement;
import com.gpg.entities.Provider;
import com.gpg.entities.Requete;
import com.gpg.entities.Status;
import com.gpg.repository.AbonnementRepository;
import com.gpg.repository.CompanyRepository;
import com.gpg.repository.FactureNQRepository;
import com.gpg.repository.FactureQRepository;
import com.gpg.repository.ProviderNormalRepo;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.jwtRepository;
import com.gpg.security.JwtAuthorizationFilter;

@Service
public class AbonnementService {
	@Autowired
	AbonnementRepository abonnementRepository;
	@Autowired 
	FactureQRepository factureQRepository;
	@Autowired
	ProviderNormalRepo providerNormalRepo;
	@Autowired
	FactureNQRepository factureNQRepository;
	@Autowired 
	jwtRepository jwtRepository;
	@Autowired
	RequeteRepository requeteRepository;
	@Autowired
	CompanyRepository companyRepository;
	

	public void save(AbonnementDto abonnementDto) {
		LocalDateTime currentUtilDate  = LocalDateTime.now();

		Company company = companyRepository.findByName(abonnementDto.getEmail())
				.orElseThrow(() -> new IllegalArgumentException("Id not found"));
		Abonnement abonnement = abonnementDto.convert(abonnementDto, company);
		JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
		Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"Ajout d'un nouveau abonnement",jwtInfo);
		requeteRepository.save(requete);
		abonnementRepository.save(abonnement);
	}

	public int getListFactureQ() {
		int ab=0;
		FactureQDtoOut facturedtoOut = new FactureQDtoOut() ;
		List<Double> mois31 = new ArrayList();
	//	mois31.add(01);
		mois31.add(3.0);
		mois31.add(5.0);
		mois31.add(7.0);
		mois31.add(10.0);
		mois31.add(12.0);
		mois31.add(8.0);
		List<Double> mois30 = new ArrayList();
		mois30.add(4.0);
		mois30.add(6.0);
		mois30.add(11.0);
		mois30.add(9.0);
		List<Double> mois28 = new ArrayList();
		mois28.add(2.0);
		
		LocalDate date_echeance = null;
		LocalDate date_facture = null;
		List<Abonnement> list = abonnementRepository.findAll();
        List<Object> listFactureQ=new ArrayList<>();
		for (Abonnement abonnement : list) {

			if (abonnement.getResteDuration() > 0) {
			
				if ((abonnement.getDatefinal().getMonth().getValue() + abonnement.getNb() <= 12)
						& (abonnement.getDatefinal().getMonth().getValue() + abonnement.getNb() == LocalDate.now().getMonth().getValue())) {
					
					System.out.println(2);
					date_facture = LocalDate.of(abonnement.getDatefinal().getYear(),
							(int) (abonnement.getDatefinal().getMonth().getValue() + abonnement.getNb()), 01);
					double nb = date_facture.getMonthValue() + abonnement.getNb();

				
					
					System.out.println(nb-1);
					System.out.println(nb);
					if ((nb-1) <= 12) {
						System.out.println(3);
						System.out.println(mois31.contains(nb-1));
						System.out.println(mois30.contains(nb-1));
						System.out.println(mois28.contains(nb-1));
						System.out.println(nb-1);
					
						if (mois31.contains(nb-1) ) {
							System.out.println(4);
							date_echeance = LocalDate.of(date_facture.getYear(),
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 1), 31);
							
						} else if (mois30.contains(nb-1) ) {
							System.out.println(5);
							date_echeance = LocalDate.of(date_facture.getYear(),
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 1), 30);
						
						} else if ( mois28.contains(nb-1)) {
							System.out.println(6);
							date_echeance = LocalDate.of(date_facture.getYear(),
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 1), 28);
					
						}
					} else {
						System.out.println(7);
System.out.println(nb-13);
						if (mois31.contains(nb - 13) ) {
							System.out.println(8);
							date_echeance = LocalDate.of(date_facture.getYear() + 1,
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 13), 31);
						} else if (mois30.contains(nb - 13) ) {
							System.out.println(9);
							date_echeance = LocalDate.of(date_facture.getYear() + 1,
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 13), 30);
						} else if (nb - 13 == 02) {
							System.out.println(10);
							date_echeance = LocalDate.of(date_facture.getYear() + 1,
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 13), 28);
						}
						else if(nb - 13==01) {
							date_echeance = LocalDate.of(date_facture.getYear()+1 ,
									01, 31);
						}
					}
					// préparation du facture
					
					Facture_Quotidienne factureQ = new Facture_Quotidienne(null, date_facture, date_echeance,
							abonnement.getPrice(),Status.Non_Payee,false,false, 0,null,abonnement.getNbMonth(), abonnement,abonnement.getProvider());
		           listFactureQ.add(factureQ);
					factureQRepository.save(factureQ);
				
	              
					ab=1;

					abonnement.setNb(abonnement.getNbMonth());
					abonnement.setDatefinal(date_facture);
					abonnement.setResteDuration(abonnement.getResteDuration() - factureQ.getNbMonth());
					abonnementRepository.save(abonnement);
					
				} 
				
				
				
				else if ((abonnement.getDatefinal().getMonth().getValue() + abonnement.getNb() > 12)
						& (abonnement.getDatefinal().getMonth().getValue() + abonnement.getNb() - 12 == LocalDate.now()
								.getMonth().getValue())) {
					System.out.println(11);
					date_facture = LocalDate.of(abonnement.getDatefinal().getYear() + 1,
							(int) (abonnement.getDatefinal().getMonth().getValue() + abonnement.getNb() - 12), 01);
					double nb = date_facture.getMonthValue() + abonnement.getNb();
					

					
					if (nb-1 <= 12) {
						System.out.println(12);
						if (mois31.contains(nb-1) ) {
							date_echeance = LocalDate.of(date_facture.getYear(),
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 1), 31);
						} else if (mois30.contains(nb-1) ) {
							System.out.println(13);
							date_echeance = LocalDate.of(date_facture.getYear(),
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 1), 30);
						} else if ( mois28.contains(nb-1)) {
							System.out.println(14);
							date_echeance = LocalDate.of(date_facture.getYear(),
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 1), 28);
						}

					} else {

						if (mois31.contains(nb - 13) ) {
							System.out.println(15);
							date_echeance = LocalDate.of(date_facture.getYear() + 1,
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 13), 31);
						} else if (mois30.contains(nb - 13) ) {
							System.out.println(16);
							date_echeance = LocalDate.of(date_facture.getYear() + 1,
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 13), 30);
						} else if (nb - 13 == 02) {
							System.out.println(17);
							date_echeance = LocalDate.of(date_facture.getYear() + 1,
									(int) (date_facture.getMonthValue() + abonnement.getNb() - 13), 28);
						}
						else if(nb - 13==01) {
							System.out.println(17);
							date_echeance = LocalDate.of(date_facture.getYear() ,
									12, 31);
						}
					}
					// préparation du facture
					
					Facture_Quotidienne factureQ = new Facture_Quotidienne(null, date_facture, date_echeance,
							abonnement.getPrice(),Status.Non_Payee,false, false,0,null, abonnement.getNbMonth(), abonnement,abonnement.getProvider() );
			        listFactureQ.add(factureQ);
					factureQRepository.save(factureQ);
	
					ab=1;

					abonnement.setNb(abonnement.getNbMonth());
					abonnement.setDatefinal(date_facture);
					abonnement.setResteDuration(abonnement.getResteDuration() - factureQ.getNbMonth());
					abonnementRepository.save(abonnement);
					
				}
	
			} 
			else if (abonnement.getResteDuration() <= 0) {
				
				abonnement.setActive(false);
				abonnementRepository.save(abonnement);
				ab=0;
			}

		}
	
		return ab;
		
	

	}
}
