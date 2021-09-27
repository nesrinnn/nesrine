package com.gpg.entities;

import java.util.List;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;



@Entity

public class Company {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true)
	private String name;
	private boolean active;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Adress> address;
	@Column(name = "picByte", nullable = false)
	private byte[] picByte;
	
	public byte[] getPicByte() {
		return picByte;
	}

	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	private String type;
	public Company() {
	
	}
	
public Company(Long id, String name, List<Adress> address, String type,byte[] picByte) {
	
		this.id = id;
		this.name = name;
		this.address = address;
		this.type = type;
		this.picByte= picByte;
	}

public Company(String name, List<Adress> address, String type, byte[] picByte, boolean active) {
		
		this.name = name;
		this.address = address;
		this.type = type;
		this.picByte= picByte;
		this.active=active;
	}
public Company(String name, String type, byte[] picByte, boolean active) {
	
	this.name = name;
	
	this.type = type;
	this.picByte= picByte;
	this.active=true;
}
	

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Adress> getAddress() {
		return address;
	}

	public void setAddress(List<Adress> address) {
		this.address = address;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	

}
