package com.gpg.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpg.entities.Requete;
import com.gpg.service.RequeteService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/requete")
public class RequeteController {

	@Autowired
	RequeteService requeteService;
	
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping("/loginInfo")
		public void saveLoginInfo() {
			
			  requeteService.saveLoginInfo(); 

		 }
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping("/notif")
	 public void saveConsultationNotif() {
		 requeteService.saveConsultationNotif();
	 }
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping("/logout")
	 public void saveInfoLogout() {
		 requeteService.saveInfologout();
	 }
	 @PreAuthorize("hasAuthority('admin')")
		@GetMapping("/allByUser/{id}")
	 public List<List<Requete>>getAllRequetteByUser(@PathVariable(value = "id") Long userId){
		
		 
		 return requeteService.getAllRequetteByUser(userId);
	 }
	

}
