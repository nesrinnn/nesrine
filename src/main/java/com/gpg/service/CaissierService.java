package com.gpg.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gpg.dto.CaissierDto;
import com.gpg.entities.Caissier;
import com.gpg.entities.Provider;
import com.gpg.entities.Utility;
import com.gpg.repository.CaissierRepository;

@Service
public class CaissierService {
	@Autowired
	CaissierRepository 	caissierRepository;
	
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	

	
	public void saveCaissier(CaissierDto caissierdto, HttpServletRequest request)throws UnsupportedEncodingException, MessagingException  {
		Caissier caissier = caissierdto.CaissierDtoToCaissier(caissierdto);
		String siteUrl = Utility.getSiteURL(request);
		sendVerificationEmail(caissier,siteUrl);
		 String hashPW=bCryptPasswordEncoder.encode(caissier.getPassword());
		 caissier.setPassword(hashPW);
		caissierRepository.save(caissier);
		
	}
	 public void sendVerificationEmail(Caissier caissier, String siteUrl)
				throws UnsupportedEncodingException, MessagingException {
			
			String subject="Inscription avec succès";
			String senderName="GPG Team";
			String mailContent="<p>Chèr(e) "+ caissier.getLastName() + caissier.getFirstName() + ",</p>";
		
			mailContent += "<p>votre login est:"+ caissier.getEmail() + "</p>";
			mailContent += "<p>votre mot de passe est:"+ caissier.getPassword() + "</p>";

			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper= new MimeMessageHelper(message);
			helper.setFrom("werdinesrine97@gmail.com", senderName);
			helper.setTo(caissier.getEmail());
			helper.setSubject(subject);
			helper.setText(mailContent, true);
			mailSender.send(message);
			
			
		}
	 public List<Caissier> getAll(){
		 return caissierRepository.findAll();
		 
	 }
	 public Caissier getCaissierById(Long id) {
		 return caissierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
	 }
	 public void setStatusCaissier(Long id) {
		 Caissier caissier = caissierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
		 if(caissier.isActive()==true) {
		 caissier.setActive(false);}
		 else
			 caissier.setActive(true);
		 caissierRepository.save(caissier);
	 }
	 public void update(Long id, Caissier caissier) {
		 Caissier caissierr = caissierRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found")); 
		 caissierr.setEmail(caissier.getEmail());
		 caissierr.setMobile1(caissier.getMobile1());
		 caissierr.setMobile2(caissier.getMobile2());
		 caissierr.setPoste(caissier.getPoste());
		 caissierRepository.save(caissierr);
	 }

}
