package com.gpg.service;

import java.io.ByteArrayOutputStream;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpg.dto.FactureQDto;
import com.gpg.dto.FactureQDtoOut;
import com.gpg.entities.Abonnement;
import com.gpg.entities.Company;
import com.gpg.entities.Facture;
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
import com.gpg.repository.FactureRepository;
import com.gpg.repository.ProviderNormalRepo;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.jwtRepository;
import com.gpg.security.JwtAuthorizationFilter;

@Service
public class FactureQService {
	@Autowired
	FactureQRepository factureQRepository;
	@Autowired
	ProviderNormalRepo providerNormalRepo;
	@Autowired
	AbonnementRepository abonnementRepository;
	@Autowired
	FactureNQRepository factureNQRepository;
	@Autowired
	FactureRepository factureRepository;
	@Autowired 
	jwtRepository jwtRepository;
	@Autowired
	RequeteRepository requeteRepository;
@Autowired 
CompanyRepository companyRepository;
	public void save(FactureQDto factureQDto) {
		LocalDateTime currentUtilDate  = LocalDateTime.now();
		Abonnement abonnement = abonnementRepository.findByCle(factureQDto.getCle())
				.orElseThrow(() -> new IllegalArgumentException("cle not found"));
		
		Company provider = companyRepository.findByName(factureQDto.getName())
				.orElseThrow(() -> new IllegalArgumentException("name not found"));
		Facture_Quotidienne facture = factureQDto.convert(factureQDto, provider, abonnement);
		   JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
			Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"ajout du premiére facture internet  ",jwtInfo);
			requeteRepository.save(requete);
		factureQRepository.save(facture);

	}

	public List<Object> get_facture() {
   List<Object> list1=new ArrayList<>();
		List<Facture_Quotidienne> list = factureQRepository.findAll().stream().filter(c ->(  c.isVu()==false))
				.collect(Collectors.toList());
		for(Facture_Quotidienne facture:list) {
			
			Company company=facture.getCompany();
			company.setPicByte(decompressBytes(company.getPicByte()));
			facture.setCompany(company);;
			list1.add(facture);
		}
        List<Facture_Provider> fac= factureNQRepository.findAll().stream().filter( c->(c.isVu()==false)).collect(Collectors.toList());
        for(Facture_Provider f:fac)
        {
        	list1.add(f);
        	
        }
        System.out.println(list1);
		return list1;
	}
	
	
	  
	public List<Facture_Quotidienne> getFacture()  {
		LocalDateTime currentUtilDate  = LocalDateTime.now();
		
		List<Facture_Quotidienne> list = factureQRepository.findAll().stream().filter(c ->( c.isActv() == false ))
				.collect(Collectors.toList());
       
       /* JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
		Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"consultation des factures qui n'ont pas encore été  complétes  ",jwtInfo);
		requeteRepository.saveAndFlush(requete);*/
		return list;
	}
	  
		public List<Facture_Provider> getFacture1()  {
			LocalDateTime currentUtilDate  = LocalDateTime.now();
			
			
	        List<Facture_Provider> fac= factureNQRepository.findAll().stream().filter( c->( c.isActv() == false )).collect(Collectors.toList());
	      
	       
	       /* JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
			Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"consultation des factures qui n'ont pas encore été  complétes  ",jwtInfo);
			requeteRepository.saveAndFlush(requete);*/
			return fac;
		}

	public FactureQDtoOut get_f(long factureId) {
		LocalDateTime currentUtilDate  = LocalDateTime.now();
		FactureQDtoOut ff = new FactureQDtoOut();
		Facture_Quotidienne facture = factureQRepository.findById(factureId)
				.orElseThrow(() -> new IllegalArgumentException("id not found"));
		FactureQDtoOut factureQOut = ff.convert(facture);
		  JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
			Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"consulter la facture qui a comme identifiant  "+factureId,jwtInfo);
			requeteRepository.save(requete);
		return factureQOut;
	}
	
	public void update(long factureId,FactureQDto factureDto){
		LocalDateTime currentUtilDate  = LocalDateTime.now();
	 
		Facture facture =	factureQRepository.findById(factureId)
		.orElseThrow(() -> new IllegalArgumentException("id not found"));
		facture.setVu(true);
		facture.setActv(true);
		facture.setNum(factureDto.getNum());
		facture.setPayements(factureDto.getPayements());
		facture.setSommeResteImpaye(factureDto.getSommeResteImpaye());
		if(factureDto.getStatus().equals("Payee"))
		{
			facture.setStatus(Status.Payee);
		}
		else if(factureDto.getStatus().equals("ENCOURS")) {
			facture.setStatus(Status.ENCOURS);
		}
		else if(factureDto.getStatus().equals("Non_Payee")) {
			facture.setStatus(Status.Non_Payee);
		}
		 JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
			Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"update la facture la facture qui a comme identifiant  "+factureId,jwtInfo);
			requeteRepository.save(requete);
		
		factureRepository.save(facture);
		
	}
	
	public int updateFactureSeen(long  id) {
		Facture facture =	factureRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("id not found"));
		
			facture.setVu(true);
			System.out.println(facture.isVu());
			factureRepository.save(facture);
			return 1;
		}
	
	
	
	
	// compress the image bytes before storing it in the database
		public static byte[] compressBytes(byte[] data) {
			System.out.println("Compressed Image Byte Size - " + data.length);
			Deflater deflater = new Deflater();
			deflater.setInput(data);
			deflater.finish();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			while (!deflater.finished()) {
				int count = deflater.deflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			try {
				outputStream.close();
			} catch (IOException e) {
			}
			System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
			
			return outputStream.toByteArray();
		}
		

		// uncompress the image bytes before returning it to the angular application
		public  byte[] decompressBytes(byte[] data) {
			Inflater inflater = new Inflater();
			inflater.setInput(data);
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
			byte[] buffer = new byte[1024];
			try {
				while (!inflater.finished()) {
					int count = inflater.inflate(buffer);
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
			} catch (IOException ioe) {
			} catch (DataFormatException e) {
			}
			return outputStream.toByteArray();
		}
	}

