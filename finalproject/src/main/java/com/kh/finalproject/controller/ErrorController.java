package com.kh.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class ErrorController {
	@GetMapping
	public String defaultError() {
		return "error/default";
	}
	@GetMapping("/400")
	public String nopage() {
		return "error/400";
	}
	
	@GetMapping("/403")
	public String noResource3() {
		return "error/403";
	}

	@GetMapping("/404")
	public String noResource() {
		return "error/404";
	}
	
	@GetMapping("/405")
	public String noResource2() {
		return "error/405";
	}

	@GetMapping("/500")
	public String serverError() {
		return "error/500";
	}
	
}
