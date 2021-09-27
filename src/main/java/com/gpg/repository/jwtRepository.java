package com.gpg.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.AppUser;
import com.gpg.entities.Caissier;
import com.gpg.entities.JwtInfo;

public interface jwtRepository  extends JpaRepository<JwtInfo, Long>{
   public Optional<JwtInfo>findByJwt(String jwtInfo);
   public Optional<JwtInfo> findByUser(Caissier caissier);
}
