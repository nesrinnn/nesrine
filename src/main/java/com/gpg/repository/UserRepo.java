package com.gpg.repository;


import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;


import com.gpg.entities.AppUser;


public interface UserRepo extends JpaRepository<AppUser, Long> {
	
	public Optional<AppUser> findByEmail(String email);
	public Optional<AppUser> findByMobile1(String mobile1);
	public Optional<AppUser> findByMobile2(String mobile2);
	
}
