package com.gpg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Caissier;

public interface CaissierRepository extends JpaRepository<Caissier, Long> {
	public Optional<Caissier> findByEmail(String email);
	

}
