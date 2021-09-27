package com.gpg.repository;

import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Admin;
import com.gpg.entities.AppUser;
public interface AdminRepository extends JpaRepository<Admin,Long> {
	public Optional<Admin> findByEmail(String email);
}
