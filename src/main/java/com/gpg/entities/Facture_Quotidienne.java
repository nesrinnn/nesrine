package com.gpg.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Facture_Quotidienne extends Facture{
	//nombre de mois de facture
	private double nbMonth;
	
	@ManyToOne (fetch = FetchType.EAGER)
	@JsonBackReference
	  private Abonnement abonnement;
	@ManyToOne(fetch = FetchType.LAZY)
	private Company company;
	
	
	

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Facture_Quotidienne() {
		super();
	
	}

	public Abonnement getAbonnement() {
		return abonnement;
	}

	public void setAbonnement(Abonnement abonnement) {
		this.abonnement = abonnement;
	}


	




	public Facture_Quotidienne(String num, LocalDate date_facture, LocalDate date_echeance, double totTtc,
			Status status,boolean actv,boolean vu,double sommeResteImpaye, List<Payement> payements,double nbMonth,Abonnement abonnement,Company company) {
		super(num, date_facture, date_echeance, totTtc, payements, status, actv,vu,sommeResteImpaye);
		this.nbMonth=nbMonth;
		this.abonnement=abonnement;
		this.company=company;
	
	}

	public double getNbMonth() {
		return nbMonth;
	}

	public void setNbMonth(double nbMonth) {
		this.nbMonth = nbMonth;
	}
	
	
	

}
