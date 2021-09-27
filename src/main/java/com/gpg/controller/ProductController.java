package com.gpg.controller;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.gpg.entities.Product;
import com.gpg.repository.ProductRepo;

@RestController
@CrossOrigin(origins = "*")
public class ProductController {
	@Autowired
	ProductRepo productRepo;
	@PreAuthorize("hasAuthority('provider')")
	@GetMapping(value = "/listProduct")

	public List<Product> getAllProduct() {
		return productRepo.findAll();
	}

	@PreAuthorize("hasAuthority('provider')")
	@GetMapping(value = "/price/")

	public float getprice(@RequestParam("reference") String reference, @RequestParam("name") String name) {

		Optional<Product> product = this.getProduct().stream().filter(p -> ((p.getName().equals(name)) &(p.getReference().equals(reference)))).findAny();

		return product.get().getPrice();
	}

	public List<Product> getProduct() {
		return productRepo.findAll();
	}
}
