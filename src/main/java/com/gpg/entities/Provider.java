package com.gpg.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public  class Provider extends AppUser {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ManyToOne(fetch = FetchType.EAGER)
	private Company providerCompany;
   
     
     private String matriculeFiscal;
     private String fixe;
     private boolean client;
     private double tva;
     private String siteWeb;
 
     
	public Provider(String firstName, String lastName, String email, String mobile1, String mobile2,
			String password, Company providerCompany, String siteWeb, String matriculeFiscal, String fixe,  boolean ui,	double tva) {
		super(firstName,lastName,email, mobile1, mobile2, password);
		this.providerCompany = providerCompany;
		this.siteWeb = siteWeb;
		this.matriculeFiscal = matriculeFiscal;
		this.fixe = fixe;
		this.client= ui;
		this.tva = tva;
	}


	public boolean isClient() {
		return client;
	}


	public void setClient(boolean client) {
		this.client = client;
	}



	public Provider() {
		super();
		this.setActive(true);
		Role role=new Role("provider");
		List<Role> list= new ArrayList<>();
		list.add(role);
		this.setRoles(list);}
	
	




	public Provider(String firstName, String lastName, String email, String mobile1, String mobile2, String password,Company providerCompany, String siteWeb, String matriculeFiscal, String fixe) {
		super(firstName, lastName, email, mobile1, mobile2, password);
	this.setActive(true);
	Role role=new Role("provider");
	List<Role> list= new ArrayList<>();
	list.add(role);
	this.setRoles(list);
	this.providerCompany = providerCompany;
	this.siteWeb = siteWeb;
	this.matriculeFiscal = matriculeFiscal;
	this.fixe = fixe;
	}



	public Company getProviderCompany() {
		return providerCompany;
	}
	public void setProviderCompany(Company providerCompany) {
		this.providerCompany = providerCompany;
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
	
     
     
     
     
}
