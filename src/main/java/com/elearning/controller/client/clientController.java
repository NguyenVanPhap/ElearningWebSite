package com.elearning.controller.client;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;


import com.elearning.entities.NguoiDung;
import com.elearning.service.NguoiDungService;
import com.elearning.entities.ResponseObject;
import com.elearning.dto.*;

@Controller
@SessionAttributes("loggedInUser")
public class clientController {
	
	@Autowired
	private NguoiDungService nguoiDungService;
	
	@Autowired
    private BCryptPasswordEncoder passwordEncoder;

	@ModelAttribute("loggedInUser")
	public NguoiDung loggedInUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		NguoiDung nguoiDung = new NguoiDung();
		if (auth.getClass().isAssignableFrom(OAuth2AuthenticationToken.class)) {
			String principal = auth.getPrincipal().toString();
			String[] part = principal.split(",");
			String name = part[2].split("=")[1];
			nguoiDung.setHoTen(name);
			nguoiDung.setLoginOauth2(true);
			return nguoiDung;
		} else {
			return nguoiDungService.findByEmail(auth.getName());
		}
	}
	
	public NguoiDung getSessionUser(HttpServletRequest request) {
		NguoiDung nguoiDung = (NguoiDung) request.getSession().getAttribute("loggedInUser");
		return nguoiDung;
	}

	@GetMapping(value = { "/home", "/" })
	public String home(Model model, @AuthenticationPrincipal OAuth2User oauth2User, HttpServletRequest request) {
		//model.addAttribute("listslidebanner", slideBannerService.findAll());
		return "client/home";
	}
	@GetMapping(value = "/profile")
	public String profile(Model model, HttpServletRequest request, @AuthenticationPrincipal OAuth2User oauth2User) {
		model.addAttribute("user", getSessionUser(request));
		return "client/profile";
	}
	@GetMapping(value = "/profile/update")
	public String updateProfile(Model model, @AuthenticationPrincipal OAuth2User oauth2User, HttpServletRequest request) {
		model.addAttribute("user", getSessionUser(request));
		return "client/updateProfile";
	}
	
	@PostMapping("/profile/update")
    public String updateNguoiDung(@ModelAttribute NguoiDung user, HttpServletRequest request) {
        NguoiDung currentUser = getSessionUser(request);
        currentUser.setDiaChi(user.getDiaChi());
        currentUser.setHoTen(user.getHoTen());
        currentUser.setSoDienThoai(user.getSoDienThoai());
        nguoiDungService.updateUser(currentUser);
        return "redirect:/profile";
//        return "redirect:/client/updateProfile";
    }
	@GetMapping("/changePassword")
    public String clientChangePasswordPage() {
        return "client/passwordChange";
    }
	@PostMapping("/updatePassword")
    @ResponseBody
    public ResponseObject passwordChange(HttpServletRequest res, @RequestBody PasswordDTO dto) {
        NguoiDung currentUser = getSessionUser(res);
        if (!passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
            ResponseObject re = new ResponseObject();
            re.setStatus("old");
            return re;
        }
        nguoiDungService.changePass(currentUser, dto.getNewPassword());
        return new ResponseObject();
    }
	@GetMapping(value = "/logout")
	public String logoutPage(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
}
