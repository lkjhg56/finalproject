package com.kh.finalproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/question")
public class UploadQuestionSolveController {
	@GetMapping("/solve")
	public String solve() {
		return "question/solve";
	}
	@PostMapping("/solve")
	public String solve2() {
		
		return "question/solve_result";
	}
	
}
