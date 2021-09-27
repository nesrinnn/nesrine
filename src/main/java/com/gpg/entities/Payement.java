package com.gpg.entities;

import java.time.LocalDate;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



@Entity
public class Payement {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	
	private String refpayement;
    @Enumerated(EnumType.STRING)
    private PayementMethod methodPayment;
	private LocalDate date_payement;
	private LocalDate date_echeance;
	private double montant;
	private String banque;
	private String refMethod;
	
	private String numCarte;
	@Enumerated(EnumType.STRING)
	private Status status;
	//pour tester sur la date d'échéance
	private boolean test;
	

	
	
	public boolean isTest() {
		return test;
	}


	public void setTest(boolean test) {
		this.test = test;
	}


	public Payement() {
	
	}
	

	public Payement(String refpayement, PayementMethod methodPayment, LocalDate date_payement, LocalDate date_echeance,
			double montant, String banque, String refMethod, String numCarte,Status status) {
		super();
		this.refpayement = refpayement;
		this.methodPayment = methodPayment;
		this.date_payement = date_payement;
		this.date_echeance = date_echeance;
		this.montant = montant;
		this.banque = banque;
		this.refMethod = refMethod;
		this.numCarte = numCarte;
		this.status=status;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRefpayement() {
		return refpayement;
	}

	public void setRefpayement(String refpayement) {
		this.refpayement = refpayement;
	}

	public PayementMethod getMethodPayment() {
		return methodPayment;
	}

	public void setMethodPayment(PayementMethod methodPayment) {
		this.methodPayment = methodPayment;
	}

	public LocalDate getDate_payement() {
		return date_payement;
	}

	public void setDate_payement(LocalDate date_payement) {
		this.date_payement = date_payement;
	}

	public LocalDate getDate_echeance() {
		return date_echeance;
	}

	public void setDate_echeance(LocalDate date_echeance) {
		this.date_echeance = date_echeance;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getBanque() {
		return banque;
	}

	public void setBanque(String banque) {
		this.banque = banque;
	}

	public String getRefMethod() {
		return refMethod;
	}

	public void setRefMethod(String refMethod) {
		this.refMethod = refMethod;
	}

	public String getNumCarte() {
		return numCarte;
	}

	public void setNumCarte(String numCarte) {
		this.numCarte = numCarte;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	
	
	
	
	

}
