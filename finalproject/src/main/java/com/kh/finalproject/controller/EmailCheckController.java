package com.kh.finalproject.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.finalproject.service.EmailService;
import com.kh.finalproject.service.RandomService;

@Controller
public class EmailCheckController {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private RandomService randomService;
	
	@GetMapping("users/check")
	public String check() {
		
		return "check";
	}
	
	@GetMapping("users/send")
	@ResponseBody	// 내가 반환하는 내용이 곧 결과무리
	public String send(@RequestParam String email, HttpSession session) {
		// 인증번호를 세션이든 DB든 어디에 저장
//		String cert = "123456";
		String cert = randomService.generateCertificationNumber(6);
		session.setAttribute("cert", cert);
		return emailService.sendCertMessage(email, cert);
	}
	
	@GetMapping("users/validate")
	@ResponseBody
	public String validate(HttpSession session, @RequestParam String cert) {
		String value = (String)session.getAttribute("cert");
		if(value.equals(cert)) {
			return "success";
		}
		else {
			
			return "fail";
		}
	}
}
