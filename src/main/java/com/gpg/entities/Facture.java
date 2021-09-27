package com.gpg.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Facture {
	  @Id @GeneratedValue(strategy = GenerationType.TABLE)
	  private long id;
	  //num du facture
	private String num;
	//date de facture
	private LocalDate date_facture;
	//retard de la facture
	private boolean late;
	
	private double sommeResteImpaye;
	
	private LocalDate date_echeance;
	//prix du facture
	private double totTtc;
	private boolean actv;
	private boolean vu;
    @Enumerated(EnumType.STRING)
	private Status status;
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	
	

	
	@OneToMany(cascade = CascadeType.ALL)
	private List<Payement> payements;
	
	
	public List<Payement> getPayements() {
		return payements;
	}


	public void setPayements(List<Payement> payements) {
		this.payements = payements;
	}
	


	public boolean isLate() {
		return late;
	}


	public void setLate(boolean late) {
		this.late = late;
	}


	public Facture(String num, LocalDate date_facture, LocalDate date_echeance, double totTtc,
			 List<Payement> payements,Status status,boolean actv,boolean vu, double sommeResteImpaye) {
	
		this.num = num;
		this.date_facture = date_facture;
		this.date_echeance = date_echeance;
		this.totTtc = totTtc;
		
	
		this.payements=payements;
		this.status=status;
		this.actv=actv;
		this.vu=vu;
		this.sommeResteImpaye=sommeResteImpaye;
	}
	public Facture(String num, LocalDate date_facture, LocalDate date_echeance, double totTtc,
			 List<Payement> payements,boolean actv,boolean vu) {
	
		this.num = num;
		this.date_facture = date_facture;
		this.date_echeance = date_echeance;
		this.totTtc = totTtc;
		
	
		this.payements=payements;
		
		this.actv=actv;
		this.vu=vu;
	}

	public String getNum() {
		return num;
	}


	public void setNum(String num) {
		this.num = num;
	}


	

	public Facture() {
		
	}
	



	

	public double getSommeResteImpaye() {
		return sommeResteImpaye;
	}


	public void setSommeResteImpaye(double sommeResteImpaye) {
		this.sommeResteImpaye = sommeResteImpaye;
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




	public boolean isVu() {
		return vu;
	}


	public void setVu(boolean vu) {
		this.vu = vu;
	}


	public Facture(String num, LocalDate date_facture, LocalDate date_echeance, double totTtc
			,Status status,boolean actv,boolean vu) {
		
		this.num = num;
		this.date_facture = date_facture;
		this.date_echeance = date_echeance;
		this.totTtc = totTtc;
		

		this.status=status;
		this.actv=actv;
		this.vu=vu;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public boolean isActv() {
		return actv;
	}


	public void setActv(boolean actv) {
		this.actv = actv;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}
	



}
