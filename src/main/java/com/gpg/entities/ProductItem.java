package com.gpg.entities;


import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class ProductItem {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  private int qte;
	  private double totttc;
	  private double tottva;
	  private double totht;
	  
	@ManyToOne (fetch = FetchType.EAGER)
	@JsonBackReference
	  private Facture_Provider facture;
	  
	  @ManyToOne (fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	  private Product product;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public Facture_Provider getFacture() {
		return facture;
	}
	public void setFacture(Facture_Provider facture) {
		this.facture = facture;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	
	
	public ProductItem() {
	
	}
	public ProductItem(double tottva,double totttc,double totht,int qte,  Product product) {
		
		this.qte = qte;
		
		this.product = product;
		this.totht=totht;
		this.totttc=totttc;
		this.tottva=tottva;
	}
	public ProductItem(double tottva,double totttc,double totht,int qte, Facture_Provider facture, Product product) {
		
		this.qte = qte;
		this.facture = facture;
		this.product = product;
		this.totht=totht;
		this.totttc=totttc;
		this.tottva=tottva;
	}
	public double getTotttc() {
		return totttc;
	}
	public void setTotttc(double totttc) {
		this.totttc = totttc;
	}
	public double getTottva() {
		return tottva;
	}
	public void setTottva(double tottva) {
		this.tottva = tottva;
	}
	public double getTotht() {
		return totht;
	}
	public void setTotht(double totht) {
		this.totht = totht;
	}
	
	  
}
