package com.gpg.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Adress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private long id;
	@Column(nullable= true)
	private String street;
	@Column(nullable= true)
	private int postalCode;
	@Column(nullable= true)
	private String region;
	
	
    public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
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


	public Adress() {

	}


	public Adress(String street, int postalCode, String region) {
		
		this.street = street;
		this.postalCode = postalCode;
		this.region = region;
	}
	
	
}
