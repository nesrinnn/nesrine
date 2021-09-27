package com.gpg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gpg.entities.Abonnement;
import com.gpg.entities.Admin;

public interface AbonnementRepository extends JpaRepository<Abonnement,Long>{
	public Optional<Abonnement> findByCle(long cle);

}
