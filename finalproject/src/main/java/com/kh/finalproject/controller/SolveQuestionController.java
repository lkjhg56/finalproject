package com.kh.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.repository.TestDao;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SolveQuestionController {
	
	@Autowired
	private TestDao testDao;
	
	@GetMapping("question/choose")
	public String choose(Model model) {
		
		model.addAttribute("list", testDao.getList());
		return "question/questions";
	}
	
	@GetMapping("question/onechoose")
	public String choose2(Model model) {
		
		model.addAttribute("list", testDao.getList());
		return "question/onequestions";
	}
	
	@GetMapping("question/questype")
	public String category(@RequestParam int tno, Model model) {
		model.addAttribute("list", testDao.getDetailList(tno));
		return "question/typechoice";
	}
	
	
	@GetMapping("question/questype2")
	public String category2(@RequestParam int tno, Model model) {
		model.addAttribute("list", testDao.getDetailList(tno));
		return "question/onetypechoice";
	}
	
	
	
	@GetMapping("question/questcategory")
	public String category(@RequestParam String categoryname, Model model) {
		model.addAttribute("clist", testDao.getQuestionList(categoryname));
		return "question/plural";
		
	}

	
	
	
	@GetMapping("question/questcategory2")
	public String category2(@RequestParam String categoryname, Model model) {
		model.addAttribute("clist2", testDao.getQuestionList2(categoryname));
		return "question/one";
		
	}
	
	@GetMapping("question/numberchoice")
	public String number(@RequestParam String categoryname, Model model) {
		model.addAttribute("clist", testDao.getQuestionList(categoryname));
		return "question/numberchoice";
	}
}
