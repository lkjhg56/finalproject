package com.kh.finalproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.service.UploadQuestionService;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private UploadQuestionService uploadQuestionService;
	
	//관리자 메인 페이지
	@GetMapping("/main")
	public String adminMain() {
		return "admin/adminMain";
	}
	//관리자 유저 문제 관리 페이지
	@GetMapping("/admin_user_question")
	public String adminUserQuestion(Model model, HttpServletRequest request) {
		model.addAttribute("list",uploadQuestionDao.question_user_all());
		//네비게이터
		int pageSize = 15;
		int navSize = 10;
		int pageNumber;
		try {
			pageNumber=Integer.parseInt(request.getParameter("pageNumber"));
			if(pageNumber<=0) {
				throw new Exception();
			}
		}catch(Exception e) {
			pageNumber=1;
		}
		int finish = pageNumber * pageSize;
		int start = finish - (pageSize-1);

		int count = uploadQuestionDao.questionCount();
		int pageCount=(count+pageSize)/pageSize;
		
		int startBlock = (pageNumber-1) / navSize * navSize+1;
		int finishBlock = startBlock +(navSize-1);
		
		if(finishBlock>pageCount) {
			finishBlock=pageCount;
		}
		Map<String, Integer> param = new HashMap<>();
		param.put("start", start);
		param.put("finish",finish);
		model.addAttribute("nav", uploadQuestionDao.mapList(param));
		request.setAttribute("pno", pageNumber);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("navsize", navSize);		
		return "admin/admin_user_question";
	}
	//관리자 유저 문제 확인
	@GetMapping("/content")
	public String adminUserQuestionCheck(@RequestParam int question_no, Model model) {
		//이미지 리스트로 불러옴
		model.addAttribute("questionDto", uploadQuestionDao.question_all(question_no));
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		return "question/content";
	}
	//관리자 유저 문제 이미지 불러오기
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_file_no) throws Exception{
		return uploadQuestionService.downloadImg(question_file_no);
	}
}
