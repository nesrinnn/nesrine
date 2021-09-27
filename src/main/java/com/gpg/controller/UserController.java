package com.gpg.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gpg.dto.UserDtoOut;
import com.gpg.entities.AppUser;
import com.gpg.entities.Caissier;
import com.gpg.entities.CaissierExcel;
import com.gpg.entities.JwtInfo;
import com.gpg.entities.Requete;
import com.gpg.repository.CaissierRepository;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.UserRepo;
import com.gpg.repository.jwtRepository;
import com.gpg.security.JWTAuthentificationFilter;
import com.gpg.security.SecurityUtilis;
import com.gpg.service.UserService;


@RestController
@RequestMapping(value = "/user")
@CrossOrigin("*")
public class UserController {
	@Autowired
	private UserService userService;
	@Autowired 
	CaissierRepository caissierRepository;

	
	@GetMapping("/getLoggedUser")
	public ResponseEntity<UserDtoOut> getLoggedUser() {
		return ResponseEntity.ok().body(UserDtoOut.mapToUserDtoOut(userService.getCurrentUser()));

	}
	@GetMapping("/resetPassword/{email}")
	public String resetPassword(@PathVariable(value = "email") String caissierEmail) throws UnsupportedEncodingException, MessagingException {
	
		return userService.resetPassword(caissierEmail);
	}
	
	@GetMapping("/resetPassword2")
	public String resetPassword2(@RequestParam("code") String code, @RequestParam("email") String email)  {
	
		return userService.resetPassword2(code, email);
	}
	@GetMapping("/updatePassword/{email}")
	public void updatePassword(@PathVariable(value = "email") String email, @RequestParam("password") String password)  {
	System.out.println(password);
		userService.updatePassword(password,email);
	}
	 @PreAuthorize("hasAuthority('admin')")
	@GetMapping("/export/excel")
		 public void exportToExcel (HttpServletResponse response) throws IOException{
		response.setContentType("application/octet-stream");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
		String currentDateTime = dateFormat.format(new Date());
		String headerKey = "Content-Disposition";
		String HeaderValue ="attachment; firstName-users_" + currentDateTime + ".xlsx";
		response.setHeader(headerKey, HeaderValue);
		List<Caissier> listCaissiers= caissierRepository.findAll();
		CaissierExcel excel = new CaissierExcel(listCaissiers);
		excel.export(response);
		
		
	 }
	
}
