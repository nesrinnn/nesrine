package com.gpg.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.gpg.entities.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
	public Optional<Company> findByName(String name);
}
