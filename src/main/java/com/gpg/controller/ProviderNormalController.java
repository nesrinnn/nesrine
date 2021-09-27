package com.gpg.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.http.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.gpg.dto.ProviderNormalDto;
import com.gpg.entities.Adress;
import com.gpg.entities.Company;
import com.gpg.entities.Provider;
import com.gpg.entities.ProviderExcel;
import com.gpg.repository.AdressRepo;
import com.gpg.repository.CompanyRepository;
import com.gpg.repository.ProviderNormalRepo;
import com.gpg.service.ProviderNormalService;

@RestController
@CrossOrigin(origins = "*")
public class ProviderNormalController {
	private final ProviderNormalService providerService;
	private final ProviderNormalRepo providerRepo;
	private final AdressRepo addressRepo;
	private final CompanyRepository companyRepo;
@Autowired
	public ProviderNormalController(ProviderNormalService providerService, ProviderNormalRepo providerRepo,AdressRepo addressRepo,CompanyRepository companyRepo) {
		this.providerService = providerService;
		this.providerRepo=providerRepo;
		this.addressRepo=addressRepo;
		this.companyRepo=companyRepo;
	}

@PostMapping(value="/add_provider")
@ResponseBody
@PreAuthorize("hasAuthority('admin')")
public String add_company(@RequestBody ProviderNormalDto providerDto, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
	return providerService.add_provider(providerDto,request);
}

@PutMapping("/provider/{id}")
@PreAuthorize("hasAuthority('admin')")
public ResponseEntity<Provider> updateProvider(@PathVariable(value = "id") Long providerId,
		@Validated @RequestBody Provider providerDetails) {
	Provider provider = providerRepo.findById(providerId)
			.orElseThrow(()->new IllegalArgumentException("Id not found"));
	provider.setMatriculeFiscal(providerDetails.getMatriculeFiscal());
	provider.setMobile1(providerDetails.getMobile1());
	provider.setMobile2(providerDetails.getMobile2());
	provider.setSiteWeb(providerDetails.getSiteWeb());
	provider.setTva(providerDetails.getTva());
	provider.setFixe(providerDetails.getFixe());
	provider.setEmail(providerDetails.getEmail());
	provider.setLastName(providerDetails.getLastName());
	provider.setFirstName(providerDetails.getFirstName());
	final Provider updatedEmployee = providerRepo.save(provider);
	return ResponseEntity.ok(updatedEmployee);
}
@GetMapping("/providers")
@PreAuthorize("hasAuthority('admin')"+"||hasAuthority('provider')")

public List<Provider> getAllEmployees() {
	return providerRepo.findAll();
	}

@GetMapping("/pro/{email}")
@PreAuthorize("hasAuthority('admin')"+"||hasAuthority('provider')")
public Provider getProviderByEmail(@PathVariable(value = "email") String email) {
	return providerRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	}





@GetMapping("/provider/{id}")
@PreAuthorize("hasAuthority('admin')"+"||hasAuthority('provider')")
public Provider getProvider(@PathVariable(value = "id") Long providerId) {
	return providerRepo.findById(providerId).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	}
@GetMapping("/providerr/{email}")
@PreAuthorize("hasAuthority('provider')"+"||hasAuthority('caissier')")
public Provider getProvider_email(@PathVariable(value = "email") String providerEmail) {
	return providerRepo.findByEmail(providerEmail).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	}

@GetMapping("/address/{id}")
@PreAuthorize("hasAuthority('admin')")
public String getAdress(@PathVariable(value = "id") Long adressId) {	
	Company company= companyRepo.findById(adressId).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	List<Adress> adress=company.getAddress();
	String str= adress.get(0).getStreet();
	String json = new Gson().toJson(str);
    return json;
	}


@GetMapping("/region/{id}")
@PreAuthorize("hasAuthority('admin')")
public String getRegion(@PathVariable(value = "id") Long companyId) {	
	Company company= companyRepo.findById(companyId).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	List<Adress> adress=company.getAddress();
	String str= adress.get(0).getRegion();
	String json = new Gson().toJson(str);
    return json;
	}

@GetMapping("/postalcode/{id}")
@PreAuthorize("hasAuthority('admin')")
public String getPostalCode(@PathVariable(value = "id") Long companyId) {	
	Company company= companyRepo.findById(companyId).orElseThrow(() -> new UsernameNotFoundException("Not found"));
	List<Adress> adress=company.getAddress();
	int str= adress.get(0).getPostalCode();
	String json = new Gson().toJson(str);
    return json;
	}
@GetMapping("/provider/listProviders")
@PreAuthorize("hasAuthority('caissier')")
public Set<Provider> getProviders() {	
	return providerService.getProviders();
	}

@PreAuthorize("hasAuthority('admin')")
@GetMapping("/setStatusProvider/{id}")
public void setStatusProvider(@PathVariable(value = "id") Long providerId) {
	providerService.setStatusProvider(providerId);
}
@PreAuthorize("hasAuthority('admin')")
@GetMapping("/export/excel/provider")
	 public void exportToExcelProvider (HttpServletResponse response) throws IOException{
	response.setContentType("application/octet-stream");
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
	String currentDateTime = dateFormat.format(new Date());
	String headerKey = "Content-Disposition";
	String HeaderValue ="attachment; firstName-users_" + currentDateTime + ".xlsx";
	response.setHeader(headerKey, HeaderValue);
	List<Provider> listProviders= providerRepo.findAll();
	ProviderExcel excel = new ProviderExcel(listProviders);
	excel.export(response);
	
	
 }


}
