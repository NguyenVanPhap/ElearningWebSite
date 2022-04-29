package com.elearning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.elearning.entities.NguoiDung;
import com.elearning.service.NguoiDungService;

@SpringBootApplication
@ComponentScan(basePackages = { "com.elearning.controller", "com.elearning" })
@ControllerAdvice
public class ELearningWebsiteApplication {

	@Autowired
	private NguoiDungService nguoiDungService;

	public static void main(String[] args) {
		SpringApplication.run(ELearningWebsiteApplication.class, args);
		System.out.println("hhhh");
	}

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return nguoiDungService.findByEmail(auth.getName());
	}

}
