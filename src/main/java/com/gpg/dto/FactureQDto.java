package com.gpg.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.gpg.entities.Abonnement;
import com.gpg.entities.Company;
import com.gpg.entities.Facture_Quotidienne;
import com.gpg.entities.Payement;
import com.gpg.entities.Provider;
import com.gpg.entities.Status;


public class FactureQDto {
	private long cle;
    private String num;
	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date_facture;

	@JsonDeserialize(using = LocalDateDeserializer.class)
	private LocalDate date_echeance;
	private double totTtc;
	private double nbMonth;
	private List<Payement> payements;
	private String name;
	private String status;
	private double sommeResteImpaye;
	
	
	public double getSommeResteImpaye() {
		return sommeResteImpaye;
	}
	public void setSommeResteImpaye(double sommeResteImpaye) {
		this.sommeResteImpaye = sommeResteImpaye;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getCle() {
		return cle;
	}
	public void setCle(long cle) {
		this.cle = cle;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
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
	public List<Payement> getPayements() {
		return payements;
	}
	public void setPayements(List<Payement> payements) {
		this.payements = payements;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	public Facture_Quotidienne convert(FactureQDto factureQDto, Company provider,Abonnement abonnement) {
		Facture_Quotidienne factureQ=null;
	if(factureQDto.getStatus().equals("Payee")) {
		 factureQ = new Facture_Quotidienne(this.num, factureQDto.getDate_facture(), 
				factureQDto.getDate_echeance(), factureQDto.getTotTtc(),Status.Payee,true,true, factureQDto.sommeResteImpaye,factureQDto.getPayements(), factureQDto.getNbMonth(), abonnement,abonnement.getProvider());
	}
	else if(factureQDto.getStatus().equals("ENCOURS")){
		
		 factureQ = new Facture_Quotidienne(this.num, factureQDto.getDate_facture(), 
					factureQDto.getDate_echeance(), factureQDto.getTotTtc(),Status.ENCOURS,true,true,factureQDto.sommeResteImpaye, factureQDto.getPayements(), factureQDto.getNbMonth(), abonnement,abonnement.getProvider());
		}
	else if(factureQDto.getStatus().equals("Non_Payee")){
		
		 factureQ = new Facture_Quotidienne(this.num, factureQDto.getDate_facture(), 
					factureQDto.getDate_echeance(), factureQDto.getTotTtc(),Status.Non_Payee,true,true,factureQDto.sommeResteImpaye, factureQDto.getPayements(), factureQDto.getNbMonth(), abonnement,abonnement.getProvider());
		}
	
		return factureQ;
	}
	

}
