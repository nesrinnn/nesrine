package com.gpg.dto;

import com.gpg.entities.Product;

import com.gpg.entities.ProductItem;

public class ProductItemDto {

	private String name;
	private float price;
	private String reference;
	private int qte;
	double tottva;
	double totttc;
	double totht;
	
	
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public ProductItemDto(String name, float price, int qte) {
		super();
		this.name = name;
		this.price = price;
		this.qte = qte;
	}
	
	public ProductItemDto(String name, float price, String reference, int qte) {
		super();
		this.name = name;
		this.price = price;
		this.reference = reference;
		this.qte = qte;
	}
	public ProductItemDto() {
		
	}
	public ProductItem convert(ProductItemDto pp) {
		ProductItem productItem;
		Product product= new Product(pp.getReference(),pp.getName(),pp.getPrice());
		
		productItem = new ProductItem(pp.getTottva(),pp.getTotttc(),pp.getTotht(),pp.getQte(),product);
		return productItem;
	}
	public double getTottva() {
		return tottva;
	}
	public void setTottva(double tottva) {
		this.tottva = tottva;
	}
	public double getTotttc() {
		return totttc;
	}
	public void setTotttc(double totttc) {
		this.totttc = totttc;
	}
	public double getTotht() {
		return totht;
	}
	public void setTotht(double totht) {
		this.totht = totht;
	}
	
	
}
