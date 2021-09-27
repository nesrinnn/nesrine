package com.gpg.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gpg.entities.AppUser;
import com.gpg.entities.Caissier;
import com.gpg.entities.JwtInfo;
import com.gpg.entities.Requete;
import com.gpg.repository.CaissierRepository;
import com.gpg.repository.RequeteRepository;
import com.gpg.repository.UserRepo;
import com.gpg.repository.jwtRepository;
import com.gpg.security.JWTAuthentificationFilter;
import com.gpg.security.JwtAuthorizationFilter;
import com.gpg.security.SecurityUtilis;

@Service
public class RequeteService {
	@Autowired
	RequeteRepository requeteRepo;
	@Autowired
	jwtRepository jwtRepository;
	@Autowired
	UserRepo userRepo;
	@Autowired
	private SecurityUtilis sec=new SecurityUtilis();
	@Autowired
	CaissierRepository caissierRepository;
	

	public void saveLoginInfo() {
		AppUser appUser=sec.getloggedUser();
		String aa= JWTAuthentificationFilter.aa; 
		if(!(jwtRepository.findByJwt(aa).isPresent())) {
		 LocalDateTime currentUtilDate  = LocalDateTime.now();
		   List<Requete>list=new ArrayList<>();
		   Requete req = new Requete("/login",currentUtilDate,"Authentification");
		   list.add(req);
		
		   JwtInfo jwtInfo=new JwtInfo(aa,currentUtilDate,appUser,list);
			jwtRepository.save(jwtInfo);
		   req.setJwtInfo(jwtInfo);
		   requeteRepo.save(req);}
	}
	public void saveConsultationNotif() {
		 LocalDateTime currentUtilDate  = LocalDateTime.now();
		 JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwt not found"));
			Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"Consultation des notifications",jwtInfo);
			requeteRepo.save(requete);
		 
	}
	public void saveInfologout() {
		 LocalDateTime currentUtilDate  = LocalDateTime.now();
		 JwtInfo jwtInfo= jwtRepository.findByJwt(JwtAuthorizationFilter.url1Requete).orElseThrow(() -> new IllegalArgumentException("jwtnot found"));
			Requete requete =new Requete(JwtAuthorizationFilter.urlRequete,currentUtilDate,"Déconnexion",jwtInfo);
			requeteRepo.save(requete);
		 
	}
	public List<List<Requete>> getAllRequetteByUser(Long userId){
		 List<List<Requete>> listRequettefinal = new ArrayList<>();
		Caissier caissier =caissierRepository.findById(userId).orElseThrow(()-> new IllegalArgumentException("id not  found"));
		List<JwtInfo> listJwt= jwtRepository.findAll().stream().filter(c->c.getUser()==caissier).collect(Collectors.toList());
		for(JwtInfo jwtInfo:listJwt) {
			List<Requete> listRequette= requeteRepo.findAll().stream().filter(c-> c.getJwtInfo()==jwtInfo).collect(Collectors.toList());
			listRequettefinal.add(listRequette);
		}
		return listRequettefinal;
	}
	

}
