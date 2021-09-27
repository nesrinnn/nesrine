package com.gpg.dto;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;

import com.gpg.entities.Adress;
import com.gpg.entities.Company;

public class CompanyDto {
	
	private String name;

	private String type;
	
	private String street;
	
	private int postalCode;
	
	private String region;

	private byte[] picByte;
	private Long id;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public byte[] getPicByte() {
		return picByte;
	}
	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}
	public Company companyDtoToCompany(CompanyDto companyDto) {
		Company company;
		List<Adress> list= new ArrayList<>();
		if(companyDto.getPostalCode()!=0 & companyDto.getStreet()!=null & companyDto.getRegion()!=null) {
		Adress adress= new Adress(companyDto.getStreet(),companyDto.getPostalCode(),companyDto.getRegion());
		list.add(adress);
		 company= new Company(companyDto.getName(),list,companyDto.getType(),companyDto.getPicByte(), true);}
		else
		 company= new Company(companyDto.getName(),companyDto.getType(),companyDto.getPicByte(),true);
		return company;
	}
	public CompanyDto() {
	
	}

	public CompanyDto(String name, String type, String street, int postalCode, String region,byte[] picByte) {
		super();
		this.name = name;
		this.type = type;
		this.street = street;
		this.postalCode = postalCode;
		this.region = region;
		this.picByte=picByte;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public int getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	



	
}
