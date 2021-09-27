package com.gpg.controller;


import java.io.ByteArrayOutputStream;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;



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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpg.dto.Company1Dto;
import com.gpg.dto.CompanyDto;
import com.gpg.entities.Adress;
import com.gpg.entities.Company;
import com.gpg.entities.Provider;
import com.gpg.repository.AdressRepo;
import com.gpg.repository.CompanyRepository;
import com.gpg.repository.ProviderNormalRepo;
import com.gpg.service.CompanyService;

@RestController

@CrossOrigin(origins = "*")
public class CompanyController {
	private final CompanyService companyService;
	private final CompanyRepository companyRepo;
	private final ProviderNormalRepo providerNormalRepo;
	@Autowired
	AdressRepo addressRepo;

	@Autowired

	public CompanyController(CompanyService companyService, CompanyRepository companyRepo,
			ProviderNormalRepo providerNormalRepo) {
		
		this.companyService = companyService;
		this.companyRepo = companyRepo;
		this.providerNormalRepo = providerNormalRepo;
	}
	@PreAuthorize("hasAuthority('admin')")
	@GetMapping("/setStatusCompany/{id}")
	public void setStatusProvider(@PathVariable(value = "id") Long companyId) {
		companyService.setStatusProvider(companyId);
	}


	

	@PostMapping("/add_company")
	@PreAuthorize("hasAuthority('admin')")
	public void add_company(@RequestParam("file") MultipartFile file, @RequestParam("company") String companyDto)
			throws IOException {
		CompanyDto companydto = new ObjectMapper().readValue(companyDto, CompanyDto.class);
		companydto.setPicByte(compressBytes(file.getBytes()));
		companyService.add_company(companydto);
	}
	@GetMapping("/get_image/{email}")
	@PreAuthorize("hasAuthority('provider')")
	public byte[] get_image(@PathVariable(value = "email") String providerEmail) {
		Provider providerNormal= providerNormalRepo.findByEmail(providerEmail).get();
		Company company= companyRepo.findById(providerNormal.getProviderCompany().getId()).get();
	
		byte[] image1=company.getPicByte();
		byte[] image2=this.decompressBytes(image1);
		return image2;
		
	}
	@GetMapping("/get_company/{email}")
	@PreAuthorize("hasAuthority('provider')")
	public Company get_company(@PathVariable(value = "email") String providerEmail) {
		Provider providerNormal= providerNormalRepo.findByEmail(providerEmail).get();
		Company company= companyRepo.findById(providerNormal.getProviderCompany().getId()).get();
		return company;
		
	}
	@GetMapping("/get_compan/{name}")
	@PreAuthorize("hasAuthority('caissier')")
	public Company getCompany(@PathVariable(value = "name") String name) {
	Company company= companyRepo.findByName(name).get();
	company.setPicByte(decompressBytes( company.getPicByte()));
	return company;
		
		
	}
	
	@GetMapping("/getCompanyById/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public Company getCompanyById(@PathVariable(value = "id") Long id) {
	Company company= companyRepo.findById(id).get();
	company.setPicByte(decompressBytes( company.getPicByte()));
	return company;	
	}
	
	
	@PutMapping("/updateCompany/{id}")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<Company> updateProvider(@PathVariable(value = "id") Long companyId,
			 @RequestBody CompanyDto companyDto) {

		Company company = companyRepo.findById(companyId)
				.orElseThrow(()->new IllegalArgumentException("Id not found"));
		Adress adress= addressRepo.findById(companyDto.getId())
				.orElseThrow(()->new IllegalArgumentException("Id not found"));
		company.setName(companyDto.getName());
		List<Adress> list = new ArrayList<>();
	//	Adress adress1= new Adress(companyDto.getStreet(),companyDto.getPostalCode(),companyDto.getRegion());
		adress.setPostalCode(companyDto.getPostalCode());
		adress.setStreet(companyDto.getStreet());
		adress.setRegion(companyDto.getRegion());
		list.add(adress);
		//company.setAddress(list);
		//addressRepo.save(adress);
		company.setAddress(list);
		final Company updatedCompany = companyRepo.save(company);
		return ResponseEntity.ok(updatedCompany);
	}

	@GetMapping("/companies")
	@PreAuthorize("hasAuthority('admin')")
	public List<Company> getAllEmployees() {
		List<Company> list= companyRepo.findAll().stream().filter(pr->pr.isActive()).collect(Collectors.toList());
		return list;
	}
	
	@GetMapping("/companiesquotidien")
	@PreAuthorize("hasAuthority('caissier')")
	public List<Company> getAllCompaniesQuotidienneActif() {
		List<Company> list= companyRepo.findAll().stream().filter(pr->(pr.isActive() & pr.getType().equals("quotidiene"))).collect(Collectors.toList());
		return list;
	}

	@GetMapping("/com")
	@PreAuthorize("hasAuthority('admin')")
	public List<Company> getAll() {
		List<Company>list= companyRepo.findAll();
	    list.forEach(l->{
	    	l.setPicByte(decompressBytes(l.getPicByte())); });
	    return list;
	}

	@CrossOrigin(origins = "*")
	@GetMapping(path = "/Imgarticles/{id}")

	public byte[] getPhoto(@PathVariable(value = "id") Long id) throws Exception {
		Company company = companyRepo.findById(id).get();
		company.setPicByte(decompressBytes(company.getPicByte()));
		return company.getPicByte();

	}

	// compress the image bytes before storing it in the database
	public static byte[] compressBytes(byte[] data) {
		System.out.println("Compressed Image Byte Size - " + data.length);
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		deflater.finish();
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		try {
			outputStream.close();
		} catch (IOException e) {
		}
		System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
		
		return outputStream.toByteArray();
	}
	

	// uncompress the image bytes before returning it to the angular application
	public  byte[] decompressBytes(byte[] data) {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		try {
			while (!inflater.finished()) {
				int count = inflater.inflate(buffer);
				outputStream.write(buffer, 0, count);
			}
			outputStream.close();
		} catch (IOException ioe) {
		} catch (DataFormatException e) {
		}
		return outputStream.toByteArray();
	}
}
