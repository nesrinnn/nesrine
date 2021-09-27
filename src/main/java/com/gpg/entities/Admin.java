package com.gpg.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Admin extends AppUser {

	public Admin() {
		super();
	}

	public Admin(String firstName, String lastName, String email, String mobile1, String mobile2, String password) {
		super(firstName, lastName, email, mobile1, mobile2, password);
		this.setActive(true);
		Role role=new Role("admin");
		List<Role> list= new ArrayList<>();
		list.add(role);
		this.setRoles(list);
	}
	
	

}
