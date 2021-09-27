package com.gpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Adress;

public interface AdressRepo extends JpaRepository<Adress, Long> {

}
