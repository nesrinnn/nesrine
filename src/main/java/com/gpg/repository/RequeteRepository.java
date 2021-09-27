package com.gpg.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Requete;

public interface RequeteRepository extends JpaRepository<Requete, Long>{
 public Optional<Requete> findBySystemDate(LocalDateTime date);
 public Optional<Requete> findByRoute(String route);
}
