package com.gpg.security;

import java.util.ArrayList;


import java.util.Collection;

import com.gpg.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserDetailsServiceImpl userDetailsServiceImpl;

	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// faire appel à la méthode loadUserByUsername pour récupérer de bd ls infos de
		// l'utilisateur qui a le username de la réquéte
		// comparer le mot de passe de l'objet authenticate avec le mot de passe
		// récupéré de la bd
		auth.userDetailsService(userDetailsServiceImpl);


	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// désactivé le tocker synchronisé et l'enregisté dans la session
		http.csrf().disable();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	
	    http.authorizeRequests().antMatchers(HttpMethod.POST,"/createAdmin/**").permitAll(); 
	   http.authorizeRequests().antMatchers(HttpMethod.POST,"/#/login/**").permitAll();
	   http.authorizeRequests().antMatchers(HttpMethod.POST,"/login/**").permitAll();
	    http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/resetPassword/**").permitAll();
	    http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/resetPassword2/**").permitAll();
	    http.authorizeRequests().antMatchers(HttpMethod.GET,"/user/updatePassword/**").permitAll();

	   
		
	    //http.headers().frameOptions().disable();

	//	http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JWTAuthentificationFilter(authenticationManager()));
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

}
