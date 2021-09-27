package com.gpg.dto;

public class ChartFactureDto {
	
	private String name;
	private double value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public ChartFactureDto(String name, int value) {
		
		this.name = name;
		this.value = value;
	}
	 

}
