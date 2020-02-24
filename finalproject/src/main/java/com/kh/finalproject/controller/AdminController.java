package com.kh.finalproject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.service.NormalUploadQuestionService;
import com.kh.finalproject.service.UploadQuestionService;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;

@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private UploadQuestionService uploadQuestionService;
	
	@Autowired
	private NormalUploadQuestionService normalUploadQuestionService;
	
	@Autowired
	private NormalUploadQuestionDao NormalUploadQuestionDao;
	
	@Autowired
	private SqlSession sqlSession;
	
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
	//유저 문제 이미지 불러오기
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_file_no) throws Exception{
		return uploadQuestionService.downloadImg(question_file_no);
	}
	
	
	

	//일반문제 전체목록
	
	@GetMapping("/admin_normal_question")
		public String normallist2( Model model,HttpServletRequest request) {

			
			List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal2");
			model.addAttribute("list2",list2);

			//네비게이터
			//페이지 크기
			int pageSize = 10;
			//네비게이터 크기
			int navSize = 10;
			//페이지별 번호
			int pageNumber;
			//받아온 페이지 번호가 음수일 경우 예외를 발생시켜 catch에서 처리해준다.
			try {
				pageNumber=Integer.parseInt(request.getParameter("pno"));
				if(pageNumber<=0) {
					//pageNumber가 0보다 작을 경우 catch로 처리한다.
					throw new Exception();
				}
			}catch(Exception e) {
				pageNumber=1;
			}
		
			int finish = pageNumber * pageSize;
			int start = finish - (pageSize - 1);
			/*****************************************************/
			//	하단 네비게이터 계산하기
			/*****************************************************/
			//전체 등록되어 있는 문제 개수를 구함.
			int count = NormalUploadQuestionDao.questionCount();
			//전체 페이지 수
			int pageCount=(count+pageSize)/pageSize;
			
			int startBlock = (pageNumber-1) / navSize * navSize+1;
			int finishBlock = startBlock +(navSize-1);
			
			if(finishBlock>pageCount) {
				finishBlock=pageCount;
			}
			Map<String, Integer> param = new HashMap<>();
			param.put("start", start);
			param.put("finish",finish);
			model.addAttribute("list", NormalUploadQuestionDao.mapList(param));
			request.setAttribute("pno", pageNumber);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pageSize);
			request.setAttribute("navsize", navSize);
			
			
			
			return "admin/admin_normal_question";
		
	}
	
	
	
	
	//일반문제 상세보기
		@GetMapping("/normalcontent")
		public String normalcontent(@RequestParam int no, Model model) {
		
			
		NormalUpdateQuestionVO normalUpdateQuestionVO=sqlSession.selectOne("question.getContent2", no);
		model.addAttribute("questionDto",normalUpdateQuestionVO);
			
			
			/*
			 * TestQuestionDto testQuestionDto =sqlSession.selectOne("question.getContent",
			 * no); model.addAttribute("questionDto", testQuestionDto);
			 */
			
			
			return "question/normalcontent";
		}	
		
		
		
		//일반문제 파일 미리보기
		@GetMapping("/qimage")
		public ResponseEntity<ByteArrayResource> previewImg2(@RequestParam int no) throws Exception{
		
			return normalUploadQuestionService.downloadImg(no);
			

		}
		
	
		@GetMapping("/normalupdate")
		public String normalupdate(@RequestParam int no, Model model) {
//			TestQuestionDto testQuestionDto= sqlSession.selectOne("question.getContent", no);
		
			NormalUpdateQuestionVO normalUpdateQuestionVO=sqlSession.selectOne("question.getContent2", no);
	//log.info("avab={}", normalUpdateQuestionVO.getNo());
			model.addAttribute("questionDto",normalUpdateQuestionVO);
			return "question/normalupdate";
		}
		
		
		
		@PostMapping("/normalupdate")
		public String normalupdate2(@ModelAttribute NormalUpdateQuestionVO normalUpdateQuestionVO, Model model) throws Exception {		
			
			normalUploadQuestionService.normalquestionUpdate(normalUpdateQuestionVO);
			
			model.addAttribute("questionDto",normalUpdateQuestionVO);
			return "question/normalcontent";
		}
		
		//일반 문제 삭제
		@GetMapping("/normaldelete")
		public String delete2(@RequestParam int no, String csname, Model model,HttpServletRequest request) {
			
			normalUploadQuestionService.questionDelete(no,csname);
			
			
//			
//			TestQuestionDto dto=TestQuestionDto.builder()
//					.csname(csname)
//					.category_no(category_no)
//					.build();
//			List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal3",dto);
//			model.addAttribute("list",list2);

		

			//네비게이터
			//페이지 크기
			int pageSize = 10;
			//네비게이터 크기
			int navSize = 10;
			//페이지별 번호
			int pageNumber;
			//받아온 페이지 번호가 음수일 경우 예외를 발생시켜 catch에서 처리해준다.
			try {
				pageNumber=Integer.parseInt(request.getParameter("pno"));
				if(pageNumber<=0) {
					//pageNumber가 0보다 작을 경우 catch로 처리한다.
					throw new Exception();
				}
			}catch(Exception e) {
				pageNumber=1;
			}
		
			int finish = pageNumber * pageSize;
			int start = finish - (pageSize - 1);
			/*****************************************************/
			//	하단 네비게이터 계산하기
			/*****************************************************/
			//전체 등록되어 있는 문제 개수를 구함.
			int count = NormalUploadQuestionDao.questionCount();
			//전체 페이지 수
			int pageCount=(count+pageSize)/pageSize;
			
			int startBlock = (pageNumber-1) / navSize * navSize+1;
			int finishBlock = startBlock +(navSize-1);
			
			if(finishBlock>pageCount) {
				finishBlock=pageCount;
			}
			Map<String, Integer> param = new HashMap<>();
			param.put("start", start);
			param.put("finish",finish);
			model.addAttribute("list", NormalUploadQuestionDao.mapList(param));
			
			
			request.setAttribute("pno", pageNumber);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pageSize);
			request.setAttribute("navsize", navSize);
			
			
			
			return "redirect:admin_normal_question";
		}
		
		
		//일반문제 업로드
		
		@GetMapping("/admin_normal_upload")
		public String normalupload() {
			return "question/normalupload";
		}
		
		
		@PostMapping("/normalupload")
		public String normalupload2(@ModelAttribute NormalUpdateQuestionVO normalUpdateQuestionVO, HttpSession session,Model model,HttpServletRequest request) throws Exception {
			
//			uploadQuestionService.questionUpload(updateQuestionVO);
			
			normalUploadQuestionService.normalquestionUpload(normalUpdateQuestionVO);
			List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal",normalUpdateQuestionVO);

			
			 model.addAttribute("list",list2);
		
			

			//네비게이터
			//페이지 크기
			int pageSize = 10;
			//네비게이터 크기
			int navSize = 10;
			//페이지별 번호
			int pageNumber;
			//받아온 페이지 번호가 음수일 경우 예외를 발생시켜 catch에서 처리해준다.
			try {
				pageNumber=Integer.parseInt(request.getParameter("pno"));
				if(pageNumber<=0) {
					//pageNumber가 0보다 작을 경우 catch로 처리한다.
					throw new Exception();
				}
			}catch(Exception e) {
				pageNumber=1;
			}
		
			int finish = pageNumber * pageSize;
			int start = finish - (pageSize - 1);
			/*****************************************************/
			//	하단 네비게이터 계산하기
			/*****************************************************/
			//전체 등록되어 있는 문제 개수를 구함.
			TestQuestionDto dto=TestQuestionDto.builder()
					.csname(normalUpdateQuestionVO.getCsname())
					.category_no(normalUpdateQuestionVO.getCategory_no())
					.build();
			
			int count=sqlSession.selectOne("question.getCountNormal",dto);
			
			
			//전체 페이지 수
			int pageCount=(count+pageSize)/pageSize;
			
			int startBlock = (pageNumber-1) / navSize * navSize+1;
			int finishBlock = startBlock +(navSize-1);
			
			if(finishBlock>pageCount) {
				finishBlock=pageCount;
			}
			Map<String, String> param = new HashMap<>();
			param.put("start", String.valueOf(start));
			param.put("finish", String.valueOf(finish));
			param.put("csname", normalUpdateQuestionVO.getCsname());
			param.put("category_no",normalUpdateQuestionVO.getCategory_no());
			
//			model.addAttribute("list", NormalUploadQuestionDao.mapList2(param));
			
			
			request.setAttribute("pno", pageNumber);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pageSize);
			request.setAttribute("navsize", navSize);
			

			return "redirect:admin_normal_question";
			
			
			
			
			
		}
		
	
}
