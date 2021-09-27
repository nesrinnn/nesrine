package com.gpg.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpg.dto.CompanyDto;
import com.gpg.dto.FacturNQDto;
import com.gpg.entities.Facture;
import com.gpg.entities.Facture_Provider;
import com.gpg.service.FactureNQService;



import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@RequestMapping("/file")

@RestController
@CrossOrigin(origins = "*")
public class FactureNQController {
	@Autowired
	FactureNQService factureNQService;
   
    @PreAuthorize("hasAuthority('provider')")
    @PostMapping("/upload")
    
    public ResponseEntity<String> uploadFiles(@RequestParam("files") MultipartFile multipartFiles,@RequestParam("facture") String factureNQDto) throws IOException {
  
    String filename=factureNQService.add_FactureNQ(factureNQDto,multipartFiles );
     return ResponseEntity.ok().body(filename);
    }
    @PreAuthorize("hasAuthority('caissier')")
    @GetMapping("/getfacture/{id}")
    public Facture_Provider getFactureById(@PathVariable(value = "id") Long factureId) {
    	return factureNQService.getFactureById(factureId);
    	
    }
    @PreAuthorize("hasAuthority('caissier')")
    @PutMapping("/update")
    public void updateFacture(@RequestBody Facture_Provider facture) {
    	factureNQService.updateFacture(facture);
    }
    @PreAuthorize("hasAuthority('caissier')")
    @GetMapping("/getPdf/{pdfName}")
    public byte[] getPdf(@PathVariable(value = "pdfName") String pdfName) throws Exception {
    	
    	return factureNQService.getPdf(pdfName);
    }
  
    
    
}
