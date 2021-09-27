package com.gpg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Facture;
import com.gpg.entities.Facture_Provider;

public interface FactureNQRepository extends JpaRepository<Facture_Provider, Long>{
	public Optional<Facture_Provider > findBynum(String s);

}
