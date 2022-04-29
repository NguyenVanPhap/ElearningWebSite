package com.elearning.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.elearning.entities.Vocabulary;
import com.elearning.dto.ResponseObject;
import com.elearning.entities.Course;
import com.elearning.entities.NguoiDung;
import com.elearning.entities.Role;
import com.elearning.service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.elearning.dto.*;
import com.elearning.config.*;


@Controller
@RequestMapping("/admin")
@SessionAttributes("loggedInUser")
public class AdminController {

	@Autowired
	private CourseService courseService;

	@Autowired
	NguoiDungService nguoiDungService;

	@Autowired
	VocabularyService baitaptuvungService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public NguoiDung getSessionUser(HttpServletRequest request) {
		return (NguoiDung) request.getSession().getAttribute("loggedInUser");

	}

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return nguoiDungService.findByEmail(auth.getName());
	}

	@GetMapping()
	public String loginPage(Model model) {
		return "admin/homepage";
	}

	@GetMapping("/grammar")
	public String quanLyGrammar() {
		return "admin/grammar";
	}

	@GetMapping("/blog")
	public String quanLyBlog() {
		return "admin/post";
	}

	@GetMapping("/courses")
	public String cources() {
		return "admin/course";
	}

	@RequestMapping(value = "/coursedetail")
	public String CourseDetail(@RequestParam("courseId") int id, Model model) {
		Course course = courseService.getCourse(id).get(0);
		model.addAttribute("currentCourse", course);
		return "admin/lesson";
	}

	@GetMapping("/vocab")
	public String quanLyVocab(Model model) {
		model.addAttribute("listVocab", baitaptuvungService.findAll());
		model.addAttribute("vocabbulary", new Vocabulary());
		return "admin/quanLyVocab";

	}

	@GetMapping("/account")
	public String quanLyTaiKhoan(Model model) {
		model.addAttribute("listRole", Role.values());
		return "admin/account";
	}

	@GetMapping("/readingexercise")
	public String quanLyBaiDocPage() {
		return "admin/readingexercise";
	}
	
	@GetMapping("/listeningexercise")
	public String ListeningExercise() {
		return "admin/listeningexercise";
	}

	@GetMapping("/profileAdmin")
	public String profileAdmin(Model model, HttpServletRequest request, @AuthenticationPrincipal OAuth2User oauth2User){
		model.addAttribute("user", getSessionUser(request));
		return "admin/profileAdmin";
	}

	@PostMapping(value="/updateInfo")
	@ResponseBody
	public ResponseObject comitChange(HttpServletRequest request, @RequestBody NguoiDung user){
		NguoiDung currentUser = getSessionUser(request);
		currentUser.setHoTen(user.getHoTen());
		currentUser.setSoDienThoai(user.getSoDienThoai());
		currentUser.setDiaChi(user.getDiaChi());
		nguoiDungService.updateUser(currentUser);
		return new ResponseObject();

	}
	@PostMapping("/updatePassword")
	@ResponseBody
	public ResponseObject passwordChange(HttpServletRequest res,@RequestBody PasswordDTO dto) {
		NguoiDung currentUser = getSessionUser(res);
		if (!passwordEncoder.matches( dto.getOldPassword(), currentUser.getPassword())) {
			ResponseObject re = new ResponseObject();
			re.setStatus("old");
			return re;
		}
		nguoiDungService.changePass(currentUser, dto.getNewPassword());
		return new ResponseObject();
	}

	

}
