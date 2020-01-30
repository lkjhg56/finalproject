package com.kh.finalproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SolveQuestionController {
	
	@GetMapping("question/choose")
	public String choose() {
		return "question/questions";
	}
	@GetMapping("question/plural")
	public String plural(){
		return "question/plural";
	}
}
