package com.gpg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gpg.entities.ProductItem;


public interface ProductItemRepo extends JpaRepository<ProductItem, Long> {

}
