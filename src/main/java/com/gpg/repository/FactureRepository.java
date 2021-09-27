package com.gpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Facture;

public interface FactureRepository extends JpaRepository<Facture, Long>{

}
