package com.gpg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpg.dto.FactureQDto;
import com.gpg.dto.FactureQDtoOut;
import com.gpg.entities.Facture;
import com.gpg.entities.Facture_Provider;
import com.gpg.entities.Facture_Quotidienne;
import com.gpg.entities.Payement;
import com.gpg.entities.Provider;
import com.gpg.service.FactureQService;

@RequestMapping("/factureQ")

@RestController
@CrossOrigin(origins = "*")
public class FactureQController {
	@Autowired
	FactureQService factureQService;

	@PreAuthorize("hasAuthority('caissier')")
	@PostMapping(value = "/save")
	public void add_facture(@RequestBody FactureQDto factureQDto) {

		factureQService.save(factureQDto);
	}
	

	@PreAuthorize("hasAuthority('caissier')")
	@GetMapping(value = "/getNonVuFacture")
	public List<Object> get_facture() {

		return factureQService.get_facture();
	}
	//vu mais inactive facture
	@PreAuthorize("hasAuthority('caissier')")
	@GetMapping(value = "/getInactiveFactureQ")
	public List<Facture_Quotidienne> getFacture() {

		return factureQService.getFacture();
	}
	
	@PreAuthorize("hasAuthority('caissier')")
	@GetMapping(value = "/getInactiveFacturep")
	public List<Facture_Provider> getFacture1() {

		return factureQService.getFacture1();
	}

	@PreAuthorize("hasAuthority('caissier')")
	@GetMapping(value = "/facuteone/{id}")
	public FactureQDtoOut get(@PathVariable(value = "id") Long factureId) {

		return factureQService.get_f(factureId);
	}

	@PutMapping("/update/{id}")
	@PreAuthorize("hasAuthority('caissier')")
	public void updateFacture(@PathVariable(value = "id") Long factureId,
			@Validated @RequestBody FactureQDto factureDto) {
		factureQService.update(factureId, factureDto);

	}
	
	@GetMapping("/setVuFacture/{facture}")
	@PreAuthorize("hasAuthority('caissier')")
	public int updateFacture(@PathVariable(value = "facture") Long  factureId ) {
		return factureQService.updateFactureSeen(factureId);

	}

}
