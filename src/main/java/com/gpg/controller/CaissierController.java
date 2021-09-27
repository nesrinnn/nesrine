package com.gpg.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.gpg.dto.CaissierDto;
import com.gpg.entities.Caissier;
import com.gpg.service.CaissierService;

@RequestMapping("/caissier")

@RestController
@CrossOrigin(origins = "*")
public class CaissierController {
	@Autowired
	CaissierService caissierService;
	@ResponseBody
	 @PreAuthorize("hasAuthority('admin')")
	 @PostMapping(value="/save", produces = MediaType.APPLICATION_JSON_VALUE)
	 public void saveCaissier(@RequestBody CaissierDto caissierDto, HttpServletRequest request)throws UnsupportedEncodingException, MessagingException  {
		
		 caissierService.saveCaissier(caissierDto, request);
		 
	 }
	
	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/getAll")
	 public List<Caissier> saveCaissier() {
		
	return caissierService.getAll();
		 
	 }
	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/getCaissierByEmail/{id}")
	 public Caissier getCaissierByEmail(@PathVariable(value = "id") Long caissierId) {
		 return caissierService.getCaissierById(caissierId);
	 }
	  
	 @PreAuthorize("hasAuthority('admin')")
	 @GetMapping("/setStatusCaissier/{id}")
	 public void setStatusCaissier(@PathVariable(value = "id") Long caissierId) {
		  caissierService.setStatusCaissier(caissierId);
	 }
	 
	 @PreAuthorize("hasAuthority('admin')")
	 @PutMapping("/update/{id}")
	 public void update(@PathVariable(value = "id") Long caissierId, @RequestBody Caissier caissier ) {
		  caissierService.update(caissierId,caissier);
	 }
}


	

