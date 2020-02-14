package com.kh.finalproject.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.ResultDao;
import com.kh.finalproject.repository.UsersDao;
import com.kh.finalproject.service.GradePointService;


@Controller

public class UsersController {
	
//	@Autowired
//	private UsersDao usersDao;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private ResultDao resultDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private GradePointService pointService;
	
	//회원 가입
	@GetMapping("users/join")
	public String join() {
		return "users/join";
	}
	@PostMapping("users/join")
	public String join(@ModelAttribute UsersDto usersDto, @ModelAttribute GradePointDto pointDto) {
		usersDto.setPw(encoder.encode(usersDto.getPw()));
//		usersDao.join(usersDto);
		sqlSession.insert("users.join", usersDto);
		pointService.giveJoinPoint(pointDto,usersDto);
		return "redirect:/";
	}
	
	//로그인&로그아웃
	@GetMapping("users/login")
	public String login(){
		return "users/login";
	}
	@PostMapping("users/login")
	public String login(@ModelAttribute UsersDto usersDto,HttpSession session){
		
		UsersDto find = sqlSession.selectOne("users.login", usersDto);
		
		if(find == null) {
			return "redirect:login?error";
		}
		else {
			
			boolean correct = encoder.matches(usersDto.getPw(), find.getPw());
			
			if(correct == true) {
				session.setAttribute("id", find.getId());
				session.setAttribute("grade", find.getGrade());
				return "redirect:/";
			}
			else {			
				return "redirect:login?error";
			}
		}
	}
	
	@GetMapping("users/logout")
	public String logout(HttpSession session) {
		session.removeAttribute("id");
		session.removeAttribute("grade");
		return "redirect:/";
	}
	
	// 아이디 중복 검사
	@GetMapping("users/id_check")
	@ResponseBody
	public String id_check(@RequestParam String id) {
		//검사를 수행
		int count = sqlSession.selectOne("users.id_check", id);
		if(count > 0) return "Y";
		else return "N";
	}

	// 회원 정보
	@GetMapping("users/info")
	public String info(HttpSession session, Model model) {
		String id = (String) session.getAttribute("id");
		model.addAttribute("users", sqlSession.selectOne("users.info", id));
		return "users/info";
	}
	
	// 회원 탈퇴
	@GetMapping("users/bye")
	public String bye(HttpSession session) {
		String id = (String) session.getAttribute("id");
		sqlSession.delete("users.bye", id);
		session.removeAttribute("id");
		session.removeAttribute("grade");
		return "redirect:/";
	}
	
//	 //회원 정보 수정
	 @GetMapping("users/change")
	 public String change(HttpSession session, Model model) {
		 String id = (String) session.getAttribute("id");
		 model.addAttribute("users", sqlSession.selectOne("users.info", id));
		return "users/change";
	 }
	 @PostMapping("users/change")
	 public String change(HttpSession session, @ModelAttribute UsersDto usersDto) {
		 String id = (String) session.getAttribute("id");
		 usersDto.setId(id);
		 sqlSession.update("users.change", usersDto );
		 return "redirect:info";
	 }
	 
	 //내가 본 시험 내역 조회
	 @GetMapping("users/test_result")
	 public String test_result(HttpServletRequest req,Model model,HttpSession session) {
		
		int pagesize = 4;
		int navsize = 2;
		
		int pno;
		try{
			pno = Integer.parseInt(req.getParameter("pno"));
			if(pno <= 0) throw new Exception();
		}
		catch(Exception e){
			pno = 1;
		}
		
		int finish = pno * pagesize;
		int start = finish - (pagesize - 1);
		
		String users_id = (String) session.getAttribute("id");
		
		int count = sqlSession.selectOne("resultDto.getCount", users_id);
		
		int pagecount = (count + pagesize) / pagesize;
		
		int startBlock = (pno - 1) / navsize * navsize + 1;
		int finishBlock = startBlock + (navsize - 1);
		
		//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
		if(finishBlock > pagecount){
			finishBlock = pagecount;
		}
		
		Map<String, String> total = new HashMap<>();
		total.put("users_id", users_id);
		total.put("start", String.valueOf(start));
		total.put("finish", String.valueOf(finish));
		
		model.addAttribute("test_result", resultDao.getList(total));
		
		//뷰에서 필요한 데이터를 첨부(4개)
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/test_result";
	 }
	 // - 검색 조회
	 @GetMapping("users/test_result_search")
	 public String test_result_search(@RequestParam String keyword,HttpServletRequest req,HttpSession session,Model model){
		
		int pagesize = 5;
		int navsize = 3;
		int pno;
		try{
			pno = Integer.parseInt(req.getParameter("pno"));
			if(pno <= 0) throw new Exception();
		}
		catch(Exception e){
			pno = 1;
		}
		
		int finish = pno * pagesize;
		int start = finish - (pagesize - 1);
		
		String users_id = (String) session.getAttribute("id");
		Map<String, String> ready = new HashMap<>();
		ready.put("users_id", users_id);
		ready.put("keyword", keyword);
		int count = sqlSession.selectOne("resultDto.search_getCount", ready);
		
		int pagecount = (count + pagesize) / pagesize;
		
		int startBlock = (pno - 1) / navsize * navsize + 1;
		int finishBlock = startBlock + (navsize - 1);
		
		//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
		if(finishBlock > pagecount){
			finishBlock = pagecount;
		}
		 
		Map<String, String> total = new HashMap<>();
		total.put("users_id", users_id);
		total.put("keyword", keyword);
		total.put("start", String.valueOf(start));
		total.put("finish", String.valueOf(finish));
		
		model.addAttribute("search_result", resultDao.searchList(total));
		 
		//뷰에서 필요한 데이터를 첨부(4개)
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/test_result_search";
	 } 
	 
	 // 포인트 랭킹 조회
	 @GetMapping("users/grade_point_rank")
	 public String point_rank(Model model, HttpServletRequest req) {
		int pagesize = 10;
		int navsize = 3;
		int pno;
		try{
			pno = Integer.parseInt(req.getParameter("pno"));
			if(pno <= 0) throw new Exception();
		}
		catch(Exception e){
			pno = 1;
		}
		
		int finish = pno * pagesize;
		int start = finish - (pagesize - 1);
		
		int count = sqlSession.selectOne("users.getCount");
		
		Map<String, Integer> total = new HashMap<>();
		total.put("start", start);
		total.put("finish", finish);
		 
		model.addAttribute("grade_point_rank", usersDao.getRank(total));
		 
		//뷰에서 필요한 데이터를 첨부(4개)
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/grade_point_rank";
	 }
	 
	 // 포인트 주는 기능 
	 @GetMapping("users/test_point")
	 public String givePoint() {
		 return "users/test_point";
	 }
	 @PostMapping("users/test_point")
	 public String givePoint(@ModelAttribute UsersDto usersDto,@ModelAttribute GradePointDto pointDto) {
//		 pointService.giveCheckPoint(pointDto,usersDto);
//		 pointService.giveQuestionUploadPoint(pointDto,usersDto);
//		 pointService.giveQuestionSolvePoint(pointDto,usersDto);
		 pointService.giveAnswerPoint(pointDto,usersDto);
		 return "redirect:info";
	 }
	 
	 //나의 포인트 내역 조회
//	 @GetMapping("users/my_grade_point")
//	 public String my_grade_point() {
//		 return "users/my_grade_point";
//	 }
	 @GetMapping("users/my_grade_point")
	 public String my_grade_point(Model model,HttpSession session) {
		 String id = (String) session.getAttribute("id");
		 model.addAttribute("grade_point", sqlSession.selectList("grade_point.my_grade_point", id));
		 return "users/my_grade_point";
	 }
}
