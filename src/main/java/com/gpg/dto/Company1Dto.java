package com.gpg.dto;

import java.util.Arrays;
import java.util.List;

import javax.persistence.OneToOne;



import com.gpg.entities.Adress;

public class Company1Dto {
	private String name;

	private String type;
	
	private Adress[] address;
	

	private byte[] picByte;


	@Override
	public String toString() {
		return "Company1Dto [name=" + name + ", type=" + type + ", address=" + Arrays.toString(address) + ", picByte="
				+ Arrays.toString(picByte) + "]";
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


	public Adress[] getAdresse() {
		return this.address;
	}


	public void setAdresse(Adress[] adresse) {
		this.address = adresse;
	}


	public byte[] getPicByte() {
		return picByte;
	}


	public void setPicByte(byte[] picByte) {
		this.picByte = picByte;
	}

	
}
