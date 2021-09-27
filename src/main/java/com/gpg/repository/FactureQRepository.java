package com.gpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Facture_Quotidienne;

public interface FactureQRepository extends JpaRepository<Facture_Quotidienne, Long>{
	

}
