package com.gpg.dto;

import java.util.ArrayList;
import java.util.List;

import com.gpg.entities.Caissier;
import com.gpg.entities.Role;

public class CaissierDto {
	private String firstName;
	private String lastName;
	private String email;
	private String mobile1;
	private String mobile2;
	private String password;
	private String poste;
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile1() {
		return mobile1;
	}
	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}
	public String getMobile2() {
		return mobile2;
	}
	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPoste() {
		return poste;
	}
	public void setPoste(String poste) {
		this.poste = poste;
	}
	
	public Caissier CaissierDtoToCaissier(CaissierDto caissierDto) {
		Caissier caissier ;
		caissier = new Caissier(this.getFirstName(),this.getLastName(),this.getEmail(),this.getMobile1(),this.getMobile2(),this.getPassword(),this.getPoste());
		
		caissier.setActive(true);
		Role role=new Role("caissier");
		List<Role> list= new ArrayList<>();
		list.add(role);
		caissier.setRoles(list);
	
		return caissier;
	}

}
