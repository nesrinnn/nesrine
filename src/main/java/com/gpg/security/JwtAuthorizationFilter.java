package com.gpg.security;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.boot.model.source.internal.hbm.RootEntitySourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.gpg.entities.JwtInfo;
import com.gpg.entities.Requete;
import com.gpg.entities.Role;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.jwtRepository;
import com.gpg.service.UserDetailsServiceImpl;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
	@Autowired
	private UserDetailsServiceImpl userD;
	public static String urlRequete;
	public static String url1Requete;

	@Autowired
   jwtRepository jwtRepo;
	@Autowired
	RequeteRepository requeteRepository;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		response.addHeader("Access-Control-Allow-Origin", "*");

		response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type,"
				+ " Access-Control-Request-Method, " + "Access-Control-Request-Headers, " + "Authorization");
		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials, Authorization");
		response.setHeader("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
		// System.out.println("nes");
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);

		} else {

			String authorizationTocken = request.getHeader(JWTUtil.AUTH_HEADER);

			if (!(request.getRequestURI().equals("/factureQ/getNonVuFacture"))
					& !(request.getRequestURI().equals("/abonnement/listeFactures"))
					& !(request.getRequestURI().equals("/allFacture/updateLate"))
					& !(request.getRequestURI().equals("/allFacture/updateAll")))
				System.out.println(request.getRequestURI());
			/*urlRequete = request.getRequestURI();
			url1Requete=authorizationTocken.substring(JWTUtil.PREFIX.length());*/
			
		
			if (authorizationTocken != null && authorizationTocken.startsWith(JWTUtil.PREFIX)) {
				urlRequete = request.getRequestURI();
				
				String jwt = authorizationTocken.substring(JWTUtil.PREFIX.length());
				url1Requete=jwt;
				Algorithm algorithm = Algorithm.HMAC256(JWTUtil.SECRET);
				JWTVerifier jWTVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jWTVerifier.verify(jwt);
				String username = decodedJWT.getSubject();

				String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
				Collection<GrantedAuthority> authorities = new ArrayList<>();

				for (String r : roles) {
					authorities.add(new SimpleGrantedAuthority(r));
				}
				UsernamePasswordAuthenticationToken authentificationTocken = new UsernamePasswordAuthenticationToken(
						username, null, authorities);
				SecurityContextHolder.getContext().setAuthentication(authentificationTocken);
				filterChain.doFilter(request, response);

			} else {
				filterChain.doFilter(request, response);
			}
		}

	}

}
