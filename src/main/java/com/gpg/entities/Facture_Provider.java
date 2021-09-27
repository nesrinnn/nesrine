package com.gpg.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@Entity
public class Facture_Provider extends Facture {
	private float totTva;
	private float totHt;
    
	

	@JsonIgnore
	@JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
	@OneToMany(mappedBy = "facture",  cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<ProductItem> productItem = new ArrayList<>();
	private String rib; 
	private String nomBanque;
	private String numCarte;
	private String pdfName;
    //@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@ManyToOne(fetch = FetchType.LAZY)
	private Provider provider;
	

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public String getRib() {
		return rib;
	}

	public void setRib(String rib) {
		this.rib = rib;
	}

	public String getNomBanque() {
		return nomBanque;
	}

	public void setNomBanque(String nomBanque) {
		this.nomBanque = nomBanque;
	}

	public String getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}

	public Facture_Provider() {

	}

	public Facture_Provider(String num, LocalDate date_facture, LocalDate date_echeance, double totTtc
			 , Status status,boolean actv,boolean vu, float totTva, float totHt, String pdfName,Provider provider) {
		super(num, date_facture, date_echeance, totTtc,status,actv,vu);
		this.totTva = totTva;
		this.totHt = totHt;
		this.pdfName= pdfName;
		this.provider=provider;
		
	}

	public Facture_Provider(String num, LocalDate date_facture, LocalDate date_echeance, double totTtc,
			Status status,boolean actv,boolean vu) {
		super(num, date_facture, date_echeance, totTtc,status,actv,vu);

	}

	public Facture_Provider(String num_fact, LocalDate date_facture, LocalDate date_echeance, float totTtc,
			  Status status,boolean actv,boolean vu, float totTva, float totHt, List<ProductItem> productItem, String rib,
			 String numCarte, String nomBanque,String pdfName,Provider provider){
		super(num_fact, date_facture, date_echeance, totTtc,status,actv,vu);
		this.totTva = totTva;
		this.totHt = totHt;
		this.rib= rib;
		this.nomBanque= nomBanque;
		this.numCarte=numCarte;
		this.productItem = productItem;
		this.pdfName=pdfName;
		this.provider=provider;
		
	}

	public float getTotTva() {
		return totTva;
	}

	public void setTotTva(float totTva) {
		this.totTva = totTva;
	}

	public float getTotHt() {
		return totHt;
	}

	public void setTotHt(float totHt) {
		this.totHt = totHt;
	}

	public List<ProductItem> getProductItem() {
		return productItem;
	}

	public void setProductItem(List<ProductItem> productItem) {
		this.productItem = productItem;
	}

	

	
 
}
