package com.gpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Payement;

public interface PayementRepository extends JpaRepository<Payement, Long> {

}
