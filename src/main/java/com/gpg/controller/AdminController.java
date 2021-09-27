package com.gpg.controller;

import java.io.UnsupportedEncodingException;


import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gpg.entities.Admin;
import com.gpg.repository.AdminRepository;
import com.gpg.service.AdminService;

@RestController
@CrossOrigin(origins = "*")
public class AdminController {
	private final AdminService adminService;
	@Autowired
	public AdminController(AdminService adminService) {
		this.adminService = adminService;}
      @PostMapping("/createAdmin")
	public void add(@RequestBody Admin admin, HttpServletRequest request) throws UnsupportedEncodingException, MessagingException {
    	  adminService.add_admin(admin, request);
	}
	
	
}
