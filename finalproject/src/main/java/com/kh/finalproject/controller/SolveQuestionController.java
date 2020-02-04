package com.kh.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.TestQuestionDto;
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
		model.addAttribute("tno", tno);
		return "question/typechoice";
	}
	
	
	@GetMapping("question/questype2")
	public String category2(@RequestParam int tno, Model model) {
		model.addAttribute("list", testDao.getDetailList(tno));
		return "question/onetypechoice";
	}
	
	
	
	@GetMapping("question/questcategory")
	public String category(@RequestParam String categoryname, String session, Model model) {
		List<TestQuestionDto> questionDto = testDao.getQuestionList(categoryname);
		List<TestQuestionDto> question = new ArrayList<>();		
		for(TestQuestionDto qlist : questionDto) {
			if(qlist.getCategory_no().equals(session)) {
				TestQuestionDto dto = new TestQuestionDto();
				dto.setAnswer(qlist.getAnswer());
				dto.setCategory_no(qlist.getCategory_no());
				dto.setDis1(qlist.getDis1());
				dto.setDis2(qlist.getDis2());
				dto.setDis3(qlist.getDis3());
				dto.setDis4(qlist.getDis4());
				dto.setDis5(qlist.getDis5());
				dto.setQuestion(qlist.getQuestion());
				question.add(dto);
			}
		}
		model.addAttribute("clist", question);
		model.addAttribute("session", session);
		log.info("session={}", session);
		return "question/plural";
		
	}


	
	
	@GetMapping("question/questcategory2")
	public String category2(@RequestParam String categoryname, Model model) {
		model.addAttribute("clist2", testDao.getQuestionList2(categoryname));
		return "question/one";
		
	}
	
}
