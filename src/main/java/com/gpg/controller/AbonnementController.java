package com.gpg.controller;

import java.time.LocalDate;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.gpg.dto.AbonnementDto;
import com.gpg.dto.FactureQDtoOut;
import com.gpg.entities.Abonnement;
import com.gpg.entities.Facture_Quotidienne;
import com.gpg.service.AbonnementService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/abonnement")
public class AbonnementController {
	@Autowired
	AbonnementService abonnementService;
	
	
	 @PreAuthorize("hasAuthority('caissier')")
	@PostMapping(value="/save")
	public void add_company(@RequestBody AbonnementDto abonnement) {

		abonnementService.save(abonnement);
	}
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping(value="/listeFactures")

		public int get_facture() {
			
			
      return abonnementService.getListFactureQ();
			
		}
		

}
