package com.kh.finalproject.controller;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.service.UploadQuestionService;
import com.kh.finalproject.vo.UpdateQuestionVO;

@Controller
@RequestMapping("/question")
public class UploadQuestionController {
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private UploadQuestionService uploadQuestionService;
	
	@GetMapping("/upload")
	public String upload() {
		return "question/upload";
	}
	@PostMapping("/upload")
	public String upload2(@ModelAttribute UpdateQuestionVO updateQuestionVO, HttpSession session) throws Exception {
		String id = (String)session.getAttribute("id");
		int sq = sqlSession.selectOne("question.getNo", id);
		updateQuestionVO.setUser_no(sq);
		uploadQuestionService.questionUpload(updateQuestionVO);
		return "question/questions";
	}
	
	@GetMapping("/update")
	public String update() {
		return "question/update";
	}
	@PostMapping("/update")
	public String update2(@ModelAttribute UploadQuestionDto uploadQuestionDto, HttpSession session, Model model) {
		String admin = (String) session.getAttribute("grade");
		uploadQuestionDao.update(uploadQuestionDto, admin);
		model.addAttribute("dto", uploadQuestionDto);
		return "question/questions";
	}
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", uploadQuestionDao.getList());
		return "question/list";
	}
	
}
