package com.gpg.dto;

import java.time.LocalDate;




import com.gpg.entities.Abonnement;
import com.gpg.entities.Company;
import com.gpg.entities.Provider;

public class AbonnementDto {
	private long cle;
	private double price;
	private double duration;
	private double nbMonth;
	private String email;
	private LocalDate dateAbonnement;
	private String type;
	private LocalDate date;
	
	//nombre de mois du dernier facture
	private double nb;
	//date du dernier facture
	private LocalDate datefinal;
	//reste du duration 
	private double resteDuration;

	
	
	
	
	public long getCle() {
		return cle;
	}
	public void setCle(long cle) {
		this.cle = cle;
	}
	
	
	
	
	
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
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
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
	public AbonnementDto() {
	
	}
	public Abonnement convert(AbonnementDto abonnementDto, Company provider) {

		Abonnement abonnement= new Abonnement(abonnementDto.cle,abonnementDto.price,true,abonnementDto.duration,abonnementDto.getNbMonth(),provider,abonnementDto.date,abonnementDto.dateAbonnement, this.type,this.nb,this.datefinal,this.resteDuration);
		return abonnement;
		
	}
	 

}
