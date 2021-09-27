package com.gpg.service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gpg.entities.AppUser;
import com.gpg.entities.Caissier;
import com.gpg.repository.UserRepo;
import com.gpg.security.SecurityUtilis;

@Service
public class UserService {

@Autowired
private SecurityUtilis securityUtils;
@Autowired
UserRepo userRepo;
@Autowired
private JavaMailSender mailSender;
@Autowired
private BCryptPasswordEncoder bCryptPasswordEncoder;

public AppUser getCurrentUser() {
	
	return securityUtils.getloggedUser();
}
public void updatePassword(String password, String email) {
	AppUser user=userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email not found"));
	 String hashPW=bCryptPasswordEncoder.encode(password);
	user.setPassword(hashPW);
	
	userRepo.save(user);
	
}

public String resetPassword2(String code, String email) {
	AppUser user=userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email not found"));
	String code1= user.getResetPassword();
	if(code1.equals(code)) {
		return "code correct";
	}
	else return "code incorrect";
}
public String resetPassword(String email) throws UnsupportedEncodingException, MessagingException {
	
	
	if(userRepo.findByEmail(email).isPresent()) {
		AppUser user=userRepo.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("email not found"));
	    int leftLimit = 97; // letter 'a'
	    int rightLimit = 122; // letter 'z'
	    int targetStringLength = 10;
	    Random random = new Random();
	    StringBuilder buffer = new StringBuilder(targetStringLength);
	    for (int i = 0; i < targetStringLength; i++) {
	        int randomLimitedInt = leftLimit + (int) 
	          (random.nextFloat() * (rightLimit - leftLimit + 1));
	        buffer.append((char) randomLimitedInt);
	    }
	    String generatedString = buffer.toString();
	   
		    user.setResetPassword(generatedString);
		    userRepo.save(user);
		    sendVerificationEmail(user, generatedString);
         return "vérifier votre boite email";
	}
	else
		return "email incorrect";
}


public void sendVerificationEmail(AppUser user, String siteUrl)
		throws UnsupportedEncodingException, MessagingException {
	
	String subject="Code de mot de passe oublié";
	String senderName="GPG Team";
	String mailContent=siteUrl;

	MimeMessage message = mailSender.createMimeMessage();
	MimeMessageHelper helper= new MimeMessageHelper(message);
	helper.setFrom("werdinesrine97@gmail.com", senderName);
	helper.setTo(user.getEmail());
	helper.setSubject(subject);
	helper.setText(mailContent, true);
	mailSender.send(message);
	
	
}


}
