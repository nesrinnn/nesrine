package com.gpg.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gpg.dto.ChartFactureDto;
import com.gpg.dto.DaschboardFactureEtat;
import com.gpg.entities.Facture;
import com.gpg.entities.Payement;
import com.gpg.entities.Status;
import com.gpg.service.FactureService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/allFacture")

public class FactureController {
	@Autowired
	FactureService factureService;
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping(value="/getAll")
	 public List<Facture> getAll(){
		
		 return factureService.getAll();
		 
	 }
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping(value="/updateAll")
	 public void updateAll(){
		
		factureService.updateAll();
		 
	 }
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping(value="/payement/{id}")
	 public Optional<List<Payement>> getPayement(@PathVariable(value = "id") Long  factureId){
		 return factureService.getPayement(factureId);
	 }
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping(value="/setStatusPayement/{id}")
	 public void getStatus(@PathVariable(value = "id") Long  payementId) {
		  factureService.setStatusPay(payementId);
	 }
	 @PreAuthorize("hasAuthority('caissier')")
		@GetMapping(value="/updateLate")
		 public void updateLate() {
		 factureService.updateLate();
	 }
	 @PreAuthorize("hasAuthority('caissier')" +"||hasAuthority('admin')")
		@GetMapping(value="/getFactureMonth/{id}")
		 public List<DaschboardFactureEtat> chartFactureMonth(@PathVariable(value = "id") Long  year) {
		
		 return factureService.chartFactureEtats(year);
	 }
	 @PreAuthorize("hasAuthority('caissier')"+"||hasAuthority('admin')")
		@GetMapping(value="/getLatePourcent/{id}")
		 public List<ChartFactureDto> chartPourcentLate(@PathVariable(value = "id") Long  year) {
		
		 return factureService.chartPourcentLate(year);
	 }
	 @PreAuthorize("hasAuthority('caissier')"+"||hasAuthority('admin')")
		@GetMapping(value="/getPourcentSomme/{id}")
		 public List<ChartFactureDto> chartPourcentSomme(@PathVariable(value = "id") Long  year){
		 return factureService.chartPourcentSomme(year);
	 }
	 @PreAuthorize("hasAuthority('caissier')")
		@PostMapping(value="/savePayement/{id}")
		 public void savePayement(@RequestBody Payement payement,@PathVariable(value = "id") Long  id) {
		 
		  factureService.savePayement(payement,id);
	 } 
	 
}
