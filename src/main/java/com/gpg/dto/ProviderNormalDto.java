package com.gpg.dto;

import java.util.ArrayList;

import java.util.List;

import javax.persistence.Column;

import com.gpg.entities.Company;
import com.gpg.entities.Provider;

import com.gpg.entities.Role;
import com.gpg.repository.CompanyRepository;


public class ProviderNormalDto {
	private String password;
    private String siteWeb;
    private String matriculeFiscal;
	private String firstName;
	private String lastName;
	private double tva;
	private Long id;
	private Role role;
	private boolean oui;
 // @Column(unique = true)
	private String email;

	private String mobile1;

	private String mobile2;

	private String fixe;




	
	
 	public boolean isOui() {
		return oui;
	}

	public void setOui(boolean oui) {
		this.oui = oui;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ProviderNormalDto(String firstName, String lastName, String email, String mobile1, String mobile2, String password,
			String siteWeb, String matriculeFiscal, String fixe, double tva, Long id, Role role, boolean oui) {

		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile1 = mobile1;
		this.mobile2 = mobile2;
		this.password = password;
		this.siteWeb = siteWeb;
		this.matriculeFiscal = matriculeFiscal;
		this.fixe = fixe;
		this.tva = tva;
		this.id = id;
		this.role=role;
		this.oui=oui;	}

	public ProviderNormalDto() {
	
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSiteWeb() {
		return siteWeb;
	}

	public void setSiteWeb(String siteWeb) {
		this.siteWeb = siteWeb;
	}

	public String getMatriculeFiscal() {
		return matriculeFiscal;
	}

	public void setMatriculeFiscal(String matriculeFiscal) {
		this.matriculeFiscal = matriculeFiscal;
	}

	public String getFixe() {
		return fixe;
	}

	public void setFixe(String fixe) {
		this.fixe = fixe;
	}

	public double getTva() {
		return tva;
	}

	public void setTva(double tva) {
		this.tva = tva;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public Provider ProviderDtoToProvider(ProviderNormalDto providerDto, CompanyRepository companyRepository) {
		Provider provider;
		Company company= companyRepository.findById(this.id)
				.orElseThrow(()->new IllegalArgumentException("Id not found"));
		provider= new Provider(firstName, lastName, email, mobile1, mobile2, password, company, siteWeb, matriculeFiscal, fixe, oui, tva);
		 provider.setActive(true);
			Role role=new Role("provider");
			List<Role> list= new ArrayList<Role>();
			list.add(role);
			provider.setRoles(list);
		return provider;
	}

}
