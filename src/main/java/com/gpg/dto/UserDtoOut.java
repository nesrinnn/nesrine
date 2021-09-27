package com.gpg.dto;


import java.util.Collection;


import com.gpg.entities.AppUser;
import com.gpg.entities.Role;


public class UserDtoOut {
	private String firstName;
	private String lastName;
	private String email;
	private String mobile1;
	private String mobile2;
	private String password;
	private Collection<Role> roles;
	private long id;
	private boolean active;
	
	public static UserDtoOut mapToUserDtoOut(AppUser user) {
		
		return new UserDtoOut(user.getFirstName(),user.getLastName(),user.getEmail(),user.getMobile1(),user.getMobile2(),user.getPassword(),user.getRoles(),user.getId(),user.isActive());
	}
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

	
	public Collection<Role> getRoles() {
		return roles;
	}
	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}
	public long  getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public UserDtoOut() {
	
	}
	public UserDtoOut(String firstName, String lastName, String email, String mobile1, String mobile2, String password,
			Collection<Role> roles, long id, boolean active) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.mobile1 = mobile1;
		this.mobile2 = mobile2;
		this.password = password;
		this.roles = roles;
		this.id = id;
		this.active = active;
	}

	
	
	

}
