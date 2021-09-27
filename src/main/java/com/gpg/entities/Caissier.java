package com.gpg.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class Caissier extends AppUser {
	private String poste;
	


public String getPoste() {
		return poste;
	}

	public void setPoste(String poste) {
		this.poste = poste;
	}

public Caissier() {
	super();
	this.setActive(true);
	Role role=new Role("caissier");
	List<Role> list= new ArrayList<>();
	list.add(role);
	this.setRoles(list);
}

public Caissier(String firstName, String lastName, String email, String mobile1, String mobile2, String password, String poste) {
	super(firstName, lastName, email, mobile1, mobile2, password);
	
	this.poste=poste;
}





}
