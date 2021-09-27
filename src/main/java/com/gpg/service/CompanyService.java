package com.gpg.service;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.gpg.dto.CompanyDto;
import com.gpg.entities.Company;

import com.gpg.repository.CompanyRepository;

@Service
public class CompanyService {
	@Autowired
	CompanyRepository companyRepository;

	public void add_company(CompanyDto companyDto) {
		
		Company company= companyDto.companyDtoToCompany(companyDto);
		companyRepository.save(company);
		
	}
	 public void setStatusProvider(Long id) {
		  Company company= companyRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
		 if(company.isActive()==true) {
			 company.setActive(false);}
		 else
			 company.setActive(true);
		 companyRepository.save(company);
	 }
	
	
	
}
