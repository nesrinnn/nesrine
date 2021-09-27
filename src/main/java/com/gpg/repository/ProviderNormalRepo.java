package com.gpg.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Provider;


public interface ProviderNormalRepo extends JpaRepository<Provider, Long> {
	public Optional<Provider> findByEmail(String email);
	public Optional<Provider> findByFirstName(String firstName);

}
