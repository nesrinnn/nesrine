package com.gpg.service;

import java.util.ArrayList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import com.gpg.entities.AppUser;
import com.gpg.entities.Role;

import com.gpg.repository.UserRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UserRepo userRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepo userRepository) {

		this.userRepository = userRepository;

	}

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
	
		if (UserUtilss.isEmail(login)) {
	
			AppUser user = userRepository.findByEmail(login)
					.orElseThrow(() -> new UsernameNotFoundException("Not found: " + login));
			
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			user.getRoles().forEach(role -> {
				
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			});
		
           if(user.isActive()) {
        	  
			return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
					.password(user.getPassword()).authorities(authorities).disabled(false).build();}
           else 
        	   return null;

		} 

		else {
			
			System.out.println("null");
			return null;
		}

	}

}
