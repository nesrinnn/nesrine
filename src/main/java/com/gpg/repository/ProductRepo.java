package com.gpg.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.Product;


public interface ProductRepo extends JpaRepository<Product, Long> {
	public Optional<Product> findByName(String name);
	public Optional<Product> findByPrice(float f);
	public Optional<Product> findByReference(String f);

}
