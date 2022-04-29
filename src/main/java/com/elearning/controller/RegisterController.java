package com.elearning.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.elearning.entities.NguoiDung;
import com.elearning.entities.Role;
import com.elearning.service.NguoiDungService;
import com.elearning.service.SecurityService;
import com.elearning.validator.NguoiDungValidator;

@Controller
public class RegisterController {

	@Autowired
	private NguoiDungService nguoiDungService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private NguoiDungValidator nguoiDungValidator;

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("newUser", new NguoiDung());
		return "register";
	}

	@PostMapping("/register")
	public String registerProcess(@ModelAttribute("newUser") @Valid NguoiDung nguoiDung, BindingResult bindingResult,
			Model model) {

		nguoiDungValidator.validate(nguoiDung, bindingResult);

		if (bindingResult.hasErrors()) {
			System.out.println("ok1");
			System.out.println(bindingResult.toString());
			return "register";
		}
		System.out.println("ok2");
		nguoiDung.setVaiTro(Role.ROLE_MEMBER);
		nguoiDungService.saveUser(nguoiDung);
		//securityService.autologin(nguoiDung.getEmail(), nguoiDung.getConfirmPassword());
		System.out.println("ok3");
		return "redirect:/";
	}
}
