package com.gpg.entities;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
@Entity
public class Requete {
	  @Id @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	  private String route;
	  private LocalDateTime systemDate;
	  private String description;
	  private boolean visible;
	  private boolean visible1;
@ManyToOne
@JsonBackReference
JwtInfo jwtInfo;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public LocalDateTime getSystemDate() {
		return systemDate;
	}
	public void setSystemDate(LocalDateTime systemDate) {
		this.systemDate = systemDate;
	}
	

	public Requete() {
		
	}
	public Requete(String route, LocalDateTime systemDate, String description) {

		this.route = route;
		this.systemDate = systemDate;
		this.description = description;
	
	}
	public JwtInfo getJwtInfo() {
		return jwtInfo;
	}
	public void setJwtInfo(JwtInfo jwtInfo) {
		this.jwtInfo = jwtInfo;
	}
	
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public Requete(String route, LocalDateTime systemDate, String description, JwtInfo jwtInfo) {
		super();
		this.route = route;
		this.systemDate = systemDate;
		this.description = description;
		this.jwtInfo = jwtInfo;
	}
	public boolean isVisible1() {
		return visible1;
	}
	public void setVisible1(boolean visible1) {
		this.visible1 = visible1;
	}
	
	
	
	

	
	
		  

}
