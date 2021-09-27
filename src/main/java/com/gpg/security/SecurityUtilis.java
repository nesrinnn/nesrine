package com.gpg.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



import com.gpg.entities.AppUser;
import com.gpg.repository.UserRepo;
@Component
public class SecurityUtilis {
	@Autowired
	UserRepo userRepository;
	

	public SecurityUtilis(UserRepo userRepository) {
		this.userRepository = userRepository;
	}






	public SecurityUtilis() {
		// TODO Auto-generated constructor stub
	}






	public AppUser getloggedUser() {
		
		
		String login= this.getLoginUser();
		
			 return userRepository.findByEmail(login)
					 .orElseThrow(() -> new UsernameNotFoundException("Not found: "+login));} 
		
		
		
		
	
	
	public String getLoginUser() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
		  String login = ((UserDetails)principal).getUsername();
		  return login;
		} else {
		  String login= principal.toString();
		  return login;
		}
	}

}
