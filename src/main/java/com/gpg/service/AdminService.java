package com.gpg.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gpg.entities.Admin;
import com.gpg.entities.Role;
import com.gpg.entities.Utility;
import com.gpg.repository.AdminRepository;

import net.bytebuddy.utility.RandomString;

@Service
public class AdminService {
@Autowired
AdminRepository adminRepo;
@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;
@Autowired
private JavaMailSender mailSender;
 public void add_admin(Admin admin, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {

	    admin.setActive(true);
		Role role=new Role("admin");
		List<Role> list= new ArrayList<>();
		list.add(role);
		admin.setRoles(list);
		/*
		 * String randomCode = RandomString.make(64);
		 * admin.setVerificationCode(randomCode);
		 */
	
		
		String siteUrl = Utility.getSiteURL(request);
		sendVerificationEmail(admin,siteUrl);
		 String hashPW=bCryptPasswordEncoder.encode(admin.getPassword());
			admin.setPassword(hashPW);
		adminRepo.save(admin);
	 
 }
public void sendVerificationEmail(Admin admin, String siteUrl)
		throws UnsupportedEncodingException, MessagingException {
	
	String subject="Inscription avec succès";
	String senderName="GPG Team";
	String mailContent="<p>Dear "+ admin.getFirstName() + ",</p>";
	mailContent += "<p>votre login est:"+ admin.getEmail() + "</p>";
	mailContent += "<p>votre login est:"+ admin.getEmail() + "</p>";
	mailContent += "<p>votre mot de passe est:"+ admin.getPassword() + "</p>";

	MimeMessage message = mailSender.createMimeMessage();
	MimeMessageHelper helper= new MimeMessageHelper(message);
	helper.setFrom("werdinesrine97@gmail.com", senderName);
	helper.setTo(admin.getEmail());
	helper.setSubject(subject);
	helper.setText(mailContent, true);
	mailSender.send(message);
	
	
}
}
