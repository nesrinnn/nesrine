package com.gpg.service;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.gpg.dto.ProviderNormalDto;
import com.gpg.entities.JwtInfo;
import com.gpg.entities.Provider;
import com.gpg.entities.Requete;
import com.gpg.entities.Utility;
import com.gpg.repository.CompanyRepository;
import com.gpg.repository.ProviderNormalRepo;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.UserRepo;
import com.gpg.repository.jwtRepository;
import com.gpg.security.JwtAuthorizationFilter;

@Service
public class ProviderNormalService {
@Autowired
ProviderNormalRepo providerRepo;
@Autowired
UserRepo userRepo;
@Autowired
CompanyRepository companyRepo;
@Autowired
private JavaMailSender mailSender;
@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired 
jwtRepository jwtRepository;
@Autowired
RequeteRepository requeteRepository;
 public String add_provider(ProviderNormalDto providerDto , HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
	 
	 Provider provider=  providerDto.ProviderDtoToProvider(providerDto, companyRepo);

	
	 if(userRepo.findByMobile1(providerDto.getMobile1()).isPresent()) {
		 System.out.println(1);
			System.out.println(providerDto.getMobile1());
		String aa = "mobile1";
		 String json = new Gson().toJson(aa);
		return json;	
	}
	else if(userRepo.findByMobile2(providerDto.getMobile2()).isPresent()) {
		System.out.println(2);
		System.out.println(providerDto.getMobile2());
		String aa = "mobile2";
		 String json = new Gson().toJson(aa);
		return json;	
	}
	
	else if(userRepo.findByEmail(providerDto.getEmail()).isPresent() ) {
		System.out.println(3);
		System.out.println(providerDto.getEmail());
		String aa = "email";
	 String json = new Gson().toJson(aa);
	return json;
	}

	 else	 {
		 String siteUrl = Utility.getSiteURL(request);
			sendVerificationEmail(provider,siteUrl);
			 String hashPW=bCryptPasswordEncoder.encode(provider.getPassword());
			 provider.setPassword(hashPW);
		 providerRepo.save(provider);
		 String aa = "vrai";
		 String json = new Gson().toJson(aa);
		return json;
		}
	
 }
 
 public void sendVerificationEmail(Provider provider, String siteUrl)
			throws UnsupportedEncodingException, MessagingException {
		
		String subject="Inscription avec succès";
		String senderName="GPG Team";
		String mailContent="<p>Chèr(e) "+ provider.getLastName() +" "+ provider.getFirstName() + ",</p>";
	
		mailContent += "<p>votre login est:"+ provider.getEmail() + "</p>";
		mailContent += "<p>votre mot de passe est:"+ provider.getPassword() + "</p>";

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper= new MimeMessageHelper(message);
		helper.setFrom("werdinesrine97@gmail.com", senderName);
		helper.setTo(provider.getEmail());
		helper.setSubject(subject);
		helper.setText(mailContent, true);
		mailSender.send(message);
		
		
	}
 public Set<Provider> getProviders(){
	
		LocalDateTime currentUtilDate  = LocalDateTime.now();
	 List <Provider> list= providerRepo.findAll();
	
//	 List <Provider> providers= new ArrayList<>();
	
	 Set<Provider> providers= list.stream().filter(c->c.getProviderCompany().getType().equals("quotidiene")).collect(Collectors.toSet());
	/* for(Provider provider:list) {
		 if(provider.getProviderCompany().getType().equals("quotidiene")) {
			 providers.add(provider);
		 }
	 }*/
	JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
		Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"consulter le formulaire de l'ajout d'un nouveau abonnement",jwtInfo);
		requeteRepository.save(requete);
		
	 return providers;
 }

 
 public void setStatusProvider(Long id) {
	 Provider caissier = providerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
	 if(caissier.isActive()==true) {
	 caissier.setActive(false);}
	 else
		 caissier.setActive(true);
	 providerRepo.save(caissier);
 }
}
