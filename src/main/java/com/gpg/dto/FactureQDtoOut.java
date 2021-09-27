package com.gpg.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.gpg.entities.Facture_Quotidienne;


public class FactureQDtoOut {
	private long cle;
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date_facture;
	@JsonSerialize(using = LocalDateSerializer.class)
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date_echeance;
	private double totTtc;
	private double nbMonth;

	private String name;

	public long getCle() {
		return cle;
	}

	public void setCle(long cle) {
		this.cle = cle;
	}

	public LocalDate getDate_facture() {
		return date_facture;
	}

	public void setDate_facture(LocalDate date_facture) {
		this.date_facture = date_facture;
	}

	public LocalDate getDate_echeance() {
		return date_echeance;
	}

	public void setDate_echeance(LocalDate date_echeance) {
		this.date_echeance = date_echeance;
	}

	public double getTotTtc() {
		return totTtc;
	}

	public void setTotTtc(double totTtc) {
		this.totTtc = totTtc;
	}

	public double getNbMonth() {
		return nbMonth;
	}

	public void setNbMonth(double nbMonth) {
		this.nbMonth = nbMonth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	public FactureQDtoOut(long cle, LocalDate date_facture, LocalDate date_echeance, double totTtc, double nbMonth,
			String name) {
		
		this.cle = cle;
		this.date_facture = date_facture;
		this.date_echeance = date_echeance;
		this.totTtc = totTtc;
		this.nbMonth = nbMonth;
		this.name = name;
	}

	public FactureQDtoOut() {
	
	}

	public FactureQDtoOut convert(Facture_Quotidienne facture) {
		long cle = facture.getAbonnement().getCle();
		String name= facture.getCompany().getName();
		FactureQDtoOut facture1 = new FactureQDtoOut(cle,facture.getDate_facture(),facture.getDate_echeance(),facture.getTotTtc(),facture.getNbMonth(),name);
		return facture1;
	}
}
