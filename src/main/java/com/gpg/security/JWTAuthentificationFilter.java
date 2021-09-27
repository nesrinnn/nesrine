package com.gpg.security;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.criteria.Join;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gpg.controller.UserController;
import com.gpg.entities.AppUser;
import com.gpg.entities.JwtInfo;
import com.gpg.entities.Requete;
import com.gpg.repository.UserRepo;
import com.gpg.repository.jwtRepository;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
	@Autowired
	jwtRepository jwtRepository;
	@Autowired
	UserRepo userRepo;
	@Autowired
	UserController userController;
	private SecurityUtilis sec=new SecurityUtilis();
public static String aa;
	private AuthenticationManager authenticationManager;

	public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {

		this.authenticationManager = authenticationManager;
	}

	public JWTAuthentificationFilter() {
		
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		AppUser ooUser;
		try {
			ooUser = new ObjectMapper().readValue(request.getInputStream(), com.gpg.entities.AppUser.class);

		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
		
		UsernamePasswordAuthenticationToken authenticationTocken = new UsernamePasswordAuthenticationToken(
				ooUser.getEmail(),ooUser.getPassword());

	
		
		return authenticationManager.authenticate(authenticationTocken);

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		User user = (User) authResult.getPrincipal();
		//System.out.println(user.getUsername());
	    List<String> roles=  user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList());
		Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
		String jwtAccessTocken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.EXPIRE_ACCESS_TOCKEN))
				.withIssuer(request.getRequestURI().toString())
				.withClaim("roles",
						 user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
				.sign(algorithm);
		
		String jwtRefreshTocken = JWT.create().withSubject(user.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis() + JWTUtil.REFRESH_ACCESS_TOCKEN))
				.withIssuer(request.getRequestURI().toString()).sign(algorithm);
		Map<String, String> idTocken = new HashMap<>();
		idTocken.put("jwt", jwtAccessTocken);
		  for(String r:roles) {
			  idTocken.put("roles", r);
		  }
		
		idTocken.put("refreshTocken", jwtRefreshTocken);
		response.setContentType("application/json");
		new ObjectMapper().writeValue(response.getOutputStream(), idTocken);
		
	
		
 aa=jwtAccessTocken;
	}
	
}
