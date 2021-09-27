package com.gpg.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;

@Entity

public class Abonnement {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(unique = true)
	private long cle;
	//price cyclique
	private double price;
	private boolean active;
	//duration du contrat
	private double duration;
	//nbr month cyclique
	private double nbMonth;
	@ManyToOne(fetch = FetchType.EAGER)
	private Company provider;
	//date du sys
	private LocalDate date;
	//date d'abonnement
	private LocalDate dateAbonnement;
	//type d'abonnement
	private String type;
	//nombre de mois du dernier facture
	private double nb;
	//date du dernier facture
	private LocalDate datefinal;
	//reste du duration 
	private double resteDuration;
	
	
	
	
	
	
	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}





	public LocalDate getDateAbonnement() {
		return dateAbonnement;
	}





	public void setDateAbonnement(LocalDate dateAbonnement) {
		this.dateAbonnement = dateAbonnement;
	}





	public Abonnement() {

	}
	
	



	public long getCle() {
		return cle;
	}





	public void setCle(long cle) {
		this.cle = cle;
	}





	public long getId() {
		return cle;
	}
	public void setId(long id) {
		this.cle = id;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public double getNbMonth() {
		return nbMonth;
	}
	public void setNbMonth(double nbMonth) {
		this.nbMonth = nbMonth;
	}





	public Company getProvider() {
		return provider;
	}





	public void setProvider(Company provider) {
		this.provider = provider;
	}





	public LocalDate getDate() {
		return date;
	}





	public void setDate(LocalDate date) {
		this.date = date;
	}


	public Abonnement(long cle,double price, boolean active, double duration, double nbMonth, Company provider,
			LocalDate date, LocalDate dateAbonnement, String type) {
		this.cle=cle;
		this.price = price;
		this.active = active;
		this.duration = duration;
		this.nbMonth = nbMonth;
		this.provider = provider;
		this.date = date;
		this.dateAbonnement=dateAbonnement;
		this.type=type;
	}





	public double getNb() {
		return nb;
	}





	public void setNb(double nb) {
		this.nb = nb;
	}





	public LocalDate getDatefinal() {
		return datefinal;
	}





	public void setDatefinal(LocalDate datefinal) {
		this.datefinal = datefinal;
	}





	public double getResteDuration() {
		return resteDuration;
	}





	public void setResteDuration(double resteDuration) {
		this.resteDuration = resteDuration;
	}





	public Abonnement(long cle, double price, boolean active, double duration, double nbMonth, Company provider,
			LocalDate date, LocalDate dateAbonnement, String type, double nb, LocalDate datefinal,
			double resteDuration) {

		this.cle = cle;
		this.price = price;
		this.active = active;
		this.duration = duration;
		this.nbMonth = nbMonth;
		this.provider = provider;
		this.date = date;
		this.dateAbonnement = dateAbonnement;
		this.type = type;
		this.nb = nb;
		this.datefinal = datefinal;
		this.resteDuration = resteDuration;
	}
	
	
	
	

}
