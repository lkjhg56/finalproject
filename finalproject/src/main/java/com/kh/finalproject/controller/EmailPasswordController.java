package com.kh.finalproject.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.CertDao;
import com.kh.finalproject.service.EmailService;
import com.kh.finalproject.service.RandomService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("users/pw")
@Slf4j
public class EmailPasswordController {

	@Autowired
	private CertDao certDao;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/input")
	public String input() {
		return "users/pw/input";
	}
	
	@PostMapping("/input")
	public String input(@RequestParam String email) throws Exception {
		emailService.sendChangePasswordMail(email);
		return "redirect:result";
	}
	
	@GetMapping("/result")
	public String result() {
		return "users/pw/result";
	}
	
	@GetMapping("/change")
	public String change(@RequestParam String cert,@RequestParam String email,HttpServletResponse response) {
		
		boolean enter = certDao.check(email, cert);
		log.info("enter = {}",enter);
		certDao.delete(email);
		if(!enter) {
			response.setStatus(403);
		}
		return "users/pw/change";
	}
	
	@PostMapping("/change")
	public String change(HttpSession session,@RequestParam String pw,@ModelAttribute UsersDto usersDto) {
		usersDto.setPw(encoder.encode(pw));
		String id = (String) session.getAttribute("id");
		usersDto.setId(id);
		sqlSession.update("users.change_pw", usersDto);
		return "redirect:/";
	}
	
}
