package com.kh.finalproject.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UserFileDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.GradePointDao;
import com.kh.finalproject.repository.ResultDao;
import com.kh.finalproject.repository.UserFileDao;
import com.kh.finalproject.repository.UsersDao;
import com.kh.finalproject.service.GradePointService;
import com.kh.finalproject.service.UserFileService;

import lombok.extern.slf4j.Slf4j;


@Controller
public class UsersController {
	
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
	
	@Autowired
	private GradePointDao gradePointDao;
	
	@Autowired
	private UserFileService userFileService;
	
	@Autowired
	private UserFileDto userFileDto;
	
	@Autowired
	private UserFileDao userFileDao;
	
	@Autowired
	private UsersDto usersDto;
	
	@Autowired
	private GradePointDto pointDto;
	
	//회원 가입
	@GetMapping("users/join")
	public String join() {
		return "users/join";
	}
	@PostMapping("users/join")
	public String join(@ModelAttribute UsersDto usersDto, List<MultipartFile> user_file,@ModelAttribute GradePointDto pointDto) throws IllegalStateException, IOException {
		userFileService.JoinWithFile(usersDto, user_file);
		return "redirect:/";
	}
	
	//로그인&로그아웃
	@GetMapping("users/login")
	public String login(){
		return "users/login";
	}
	@PostMapping("users/login")
	public String login(@ModelAttribute UsersDto usersDto,@ModelAttribute GradePointDto pointDto,HttpSession session){
		
		UsersDto find = sqlSession.selectOne("users.login", usersDto);
		
		if(find == null) {
			return "redirect:login?error";
		}
		else {
			
			boolean correct = encoder.matches(usersDto.getPw(), find.getPw());
			
			if(correct == true) {
				session.setAttribute("id", find.getId());
				session.setAttribute("grade", find.getGrade());
				session.setAttribute("isPremium", find.getIs_premium());
		
				
				String id = (String) session.getAttribute("id");
				
				//user_no 뽑기
				int user_no = sqlSession.selectOne("users.get_users_no", id);
				pointDto.setUsers_no(user_no);
				
				//오늘 날짜와 마지막 로그인 체크 날짜와 비교
				//오늘 날짜
				String today = sqlSession.selectOne("users.today");
				//로그인 체크 날짜
				String login_check = sqlSession.selectOne("users.login_day", id);
				//오늘 날짜와 마지막 로그인 날짜가 다르면 포인트 주고 업데이트
				if(!today.equals(login_check)) {
					gradePointDao.giveCheckPoint(user_no);
				}
				//로그인 체크 업데이트
				sqlSession.update("users.login_check", id);
				
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
		session.removeAttribute("isPremium");
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
		
		//회원정보 보여주기
		String id = (String) session.getAttribute("id");
		UsersDto dto = sqlSession.selectOne("users.info", id);
		model.addAttribute("users", dto);
		
		//사진 보여주기
		userFileDto = sqlSession.selectOne("users.getFileOne", dto.getUser_no());
		model.addAttribute("userFileDto", userFileDto);
		
		return "users/info";
	}
	// 관리자가 회원 정보
	@PostMapping("users/info")
	public String info(HttpSession session, Model model, @RequestParam String id) {
		
		//회원정보 보여주기
		UsersDto dto = sqlSession.selectOne("users.info", id);
		model.addAttribute("users", dto);
		
		//사진 보여주기
		userFileDto = sqlSession.selectOne("users.getFileOne", dto.getUser_no());
		model.addAttribute("userFileDto", userFileDto);
		
		return "users/info";
	}
	
	////////////////////////////이미지 1개 보여주기//////////////////////////////
	@GetMapping("users/userimg")
	public ResponseEntity<ByteArrayResource> getImg(int user_no) throws Exception{
		return userFileService.getImg(user_no);
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
	//관리자가 회원 강제 탈퇴
	@PostMapping("users/bye")
	public String bye(HttpSession session,@RequestParam String id) {
		sqlSession.delete("users.bye", id);
		return "redirect:user_list";
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
	 
	 //프로필 수정
	 @PostMapping("users/profile_edit")
	 public String profile_edit(List<MultipartFile> user_file) throws IllegalStateException, IOException {
		 userFileService.ProfileEdit(user_file);
		 return "redirect:info";
	 }
	 
	 //프로필 수정
	 @PostMapping("users/profile_delete")
	 public String profile_delete(HttpSession session) throws IllegalStateException, IOException {
		 String id = (String) session.getAttribute("id");
		 int user_no = sqlSession.selectOne("users.get_users_no", id);
		 userFileService.ProfileDelete(user_no);
		 return "redirect:info";
	 }
	 
	 //내가 본 시험 내역 조회
	 @GetMapping("users/test_result")
	 public String test_result(HttpServletRequest req,Model model,HttpSession session) {
		
		int pagesize = 15;
		int navsize = 10;
		
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
		
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/test_result";
	 }
	 // - 검색 조회
	 @GetMapping("users/test_result_search")
	 public String test_result_search(@RequestParam String keyword,HttpServletRequest req,HttpSession session,Model model){
		
		int pagesize = 15;
		int navsize = 10;
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
		 
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/test_result_search";
	 } 
	 
	 // 포인트 랭킹 조회
	 @GetMapping("users/grade_point_rank")
	 public String point_rank(Model model, HttpServletRequest req) {
		int pagesize = 15;
		int navsize = 10;
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
		 
		 //출석체크 포인트
//		 pointService.giveCheckPoint(usersDto,pointDto);
		 //문제 업로드 포인트
//		 pointService.giveQuestionUploadPoint(usersDto,pointDto);
		 //문제 풀기 포인트
//		 pointService.giveQuestionSolvePoint(usersDto,pointDto);
		 //문제 삭제 포인트
		 pointService.deleteQuestionPoint(usersDto, pointDto);
		 //답변 채택 포인트
//		 pointService.giveAnswerPoint(usersDto,pointDto);
		 return "redirect:info";
	 }
	 
	 //나의 포인트 내역 조회
	 @GetMapping("users/my_grade_point")
	 public String my_grade_point(Model model,HttpSession session,HttpServletRequest req) {
		 
		int pagesize = 15;
		int navsize = 10;
			
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
		
		String id = (String) session.getAttribute("id");
		
		int count = sqlSession.selectOne("grade_point.getCount", id);
		
		Map<String, String> total = new HashMap<>();
		total.put("id", id);
		total.put("start", String.valueOf(start));
		total.put("finish", String.valueOf(finish));
		
		model.addAttribute("my_grade_point", gradePointDao.get_pointList(total));
		
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/my_grade_point";
		 
	 }
	 
	 //회원 목록
	 @GetMapping("users/user_list")
	 public String user_list(Model model, HttpServletRequest req) {
		 int pagesize = 15;
		int navsize = 10;
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
		
		int count = sqlSession.selectOne("users.getUserCount");
		
		Map<String, Integer> total = new HashMap<>();
		total.put("start", start);
		total.put("finish", finish);
		 
		model.addAttribute("user_list", usersDao.getUserList(total));
		 
		req.setAttribute("pno", pno);
		req.setAttribute("count", count);
		req.setAttribute("pagesize", pagesize);
		req.setAttribute("navsize", navsize);
		
		return "users/user_list";
	 }
}
