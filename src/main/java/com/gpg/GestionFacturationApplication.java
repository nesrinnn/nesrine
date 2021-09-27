package com.gpg;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.gpg.controller.FactureNQController;
import com.gpg.repository.UserRepo;
import com.gpg.repository.jwtRepository;
import com.gpg.service.FactureNQService;
import com.zaxxer.hikari.HikariConfig;

@SpringBootApplication
public class GestionFacturationApplication {

	public static void main(String[] args) {
		new File(FactureNQService.DIRECTORY).mkdir();
		SpringApplication.run(GestionFacturationApplication.class, args);
	}

	@Bean

	public BCryptPasswordEncoder getBCPE() {

	

		return new BCryptPasswordEncoder();
	}

  


}
