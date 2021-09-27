package com.gpg.entities;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
@Entity
public class JwtInfo {
	@Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String jwt;
	private LocalDateTime SystemDate;
	@ManyToOne(fetch = FetchType.LAZY)
	private AppUser user;
	@OneToMany( fetch = FetchType.EAGER, mappedBy = "jwtInfo", cascade = CascadeType.ALL)
	private List<Requete> requete;
	
	
	public JwtInfo() {
	
	}
	
	


	public JwtInfo(String jwt, LocalDateTime systemDate, AppUser user, List<Requete> requete) {
		super();
		this.jwt = jwt;
		SystemDate = systemDate;
		this.user = user;
		this.requete = requete;
	}




	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getJwt() {
		return jwt;
	}


	public void setJwt(String jwt) {
		this.jwt = jwt;
	}


	public LocalDateTime getSystemDate() {
		return SystemDate;
	}


	public void setSystemDate(LocalDateTime systemDate) {
		SystemDate = systemDate;
	}


	public AppUser getUser() {
		return user;
	}


	public void setUser(AppUser user) {
		this.user = user;
	}


	public List<Requete> getRequete() {
		return requete;
	}


	public void setRequete(List<Requete> requete) {
		this.requete = requete;
	}
	
	

}
