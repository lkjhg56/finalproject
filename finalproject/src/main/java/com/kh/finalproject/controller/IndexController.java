package com.kh.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.kh.finalproject.entity.UploadQuestionDto;

@Controller
public class IndexController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("grade_point_rank", sqlSession.selectList("users.grade_point_rank7"));
		model.addAttribute("newQ", sqlSession.selectList("test.nList"));
		model.addAttribute("recentC", sqlSession.selectList("board.recent_content"));
		
		return "index";
	}
}
