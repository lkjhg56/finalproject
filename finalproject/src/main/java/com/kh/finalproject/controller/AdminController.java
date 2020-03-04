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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardReplyDto;
import com.kh.finalproject.entity.BoardReportDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.BoardDao;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.repository.UsersDao;
import com.kh.finalproject.service.BoardFileService;
import com.kh.finalproject.service.EmailService;
import com.kh.finalproject.service.NormalUploadQuestionService;
import com.kh.finalproject.service.UploadQuestionService;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/admin")
@Slf4j
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
	private BoardDao boardDao;
	
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private BoardFileService boardfileService;
	
	@Autowired
	private EmailService emailService;
	
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
		
		
//	관리자 게시글 신고 목록,검색 조회		
	@GetMapping("/blacklist")
	public String blacklist(Model model, HttpServletRequest request,
											@RequestParam(required = false, defaultValue = "0") String type,
											@RequestParam(required = false, defaultValue = "0") String keyword) {
		//페이지 크기
		int pagesize = 10;
		//네비게이터 크기
		int navsize = 10;
		
		//페이징 추가
		int pno;
		try{
			pno = Integer.parseInt(request.getParameter("pno"));
			if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
		}
		catch(Exception e){
			pno = 1;
		}
			
		int finish = pno * pagesize;
		int start = finish - (pagesize - 1);
	//**************************************************************************************
	//			 		하단 네비게이터 계산하기
	//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
	//**************************************************************************************
		int count = boardDao.boardReportCount(); //전체글 개수를 구하는 메소드 		
		int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
		int startBlock = (pno -1) / navsize * navsize + 1;
		int finishBlock = startBlock + (navsize -1);
		
		//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
		if(finishBlock > pagecount){
			finishBlock = pagecount;
		}
		
		if(keyword != null) {
			Map<String, String> param = new HashMap<>();
			param.put("start", String.valueOf(start));
			param.put("finish", String.valueOf(finish));
			param.put("type", type);
			param.put("keyword", keyword);
			
			List<BoardReportDto> dto = boardDao.reportSearch(param);
			
			for(int i = 0; i < dto.size(); i ++) {
				int board_no = dto.get(i).getReport_board_no();
				int addCount = boardDao.reportCountAdd(dto.get(i).getBoard_no());
				dto.get(i).setReport_count(addCount);

				if(board_no !=0) {
					model.addAttribute("list", dto);	
				}			
			}						
			request.setAttribute("pno", pno);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pagesize);
			request.setAttribute("navsize", navsize);
			request.setAttribute("type", type);
			request.setAttribute("keyword", keyword);
			
			return "admin/blacklist";	
		}
		else {
			Map<String, String> param = new HashMap<>();
			param.put("start", String.valueOf(start));
			param.put("finish", String.valueOf(finish));
			
			List<BoardReportDto> dto = boardDao.getReportList(param);
			
			for(int i = 0; i < dto.size(); i ++) {
				int board_no = dto.get(i).getReport_board_no();
				int addCount = boardDao.reportCountAdd(dto.get(i).getBoard_no());
				dto.get(i).setReport_count(addCount);

				if(board_no !=0) {
					model.addAttribute("list", dto);	
				}			
			}			
			
			request.setAttribute("pno", pno);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pagesize);
			request.setAttribute("navsize", navsize);
			
			return "admin/blacklist";
		}
				
	}
	
//	관리자 게시글 신고 카테고리별 목록,검색 조회		
	@PostMapping("/blacklist")
	public String blacklist(@RequestParam(defaultValue = "전체") String board_category, 
											@RequestParam(required = false, defaultValue = "0") String type,
											@RequestParam(required = false, defaultValue = "0") String keyword,
											Model model, 
											HttpServletRequest request) {
		//페이지 크기
			int pagesize = 10;
			//네비게이터 크기
			int navsize = 10;
			
			//페이징 추가
			int pno;
			try{
				pno = Integer.parseInt(request.getParameter("pno"));
				if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
			}
			catch(Exception e){
				pno = 1;
			}
				
			int finish = pno * pagesize;
			int start = finish - (pagesize - 1);			
		//**************************************************************************************
		//			 		하단 네비게이터 계산하기
		//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
		//**************************************************************************************
			int count = boardDao.boardCategoryCount(board_category); //전체글 개수를 구하는 메소드 		
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수		
			int startBlock = (pno -1) / navsize * navsize + 1;
			int finishBlock = startBlock + (navsize -1);
			
			//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
			if(finishBlock > pagecount){
				finishBlock = pagecount;
			}
			
			if(board_category != null && keyword != null){
				System.out.println("@@board_category="+board_category);
				Map<String, String> param = new HashMap<>();
				param.put("start", String.valueOf(start));
				param.put("finish", String.valueOf(finish));
				param.put("type", type);
				param.put("keyword", keyword);
				param.put("board_category", board_category);
			
				request.setAttribute("pno", pno);
				request.setAttribute("count", count);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("navsize", navsize);
				request.setAttribute("type", type);
				request.setAttribute("keyword", keyword);
				request.setAttribute("board_category", board_category);
			
				List<BoardReportDto> dto = boardDao.reportCategorySearch(param);
				System.out.println("@@검색결과="+dto);

				for(int i = 0; i < dto.size(); i ++) {
					int board_no = dto.get(i).getReport_board_no();
					int addCount = boardDao.reportCountAdd(dto.get(i).getBoard_no());
					dto.get(i).setReport_count(addCount);
					
					if(board_no !=0) {
						model.addAttribute("list", dto);
					}			
				}			
			return "admin/blacklist";
			}
			
			else {
				System.out.println("@@@@board_category="+board_category);
				Map<String, String> param = new HashMap<>();
				param.put("start", String.valueOf(start));
				param.put("finish", String.valueOf(finish));
				param.put("board_category", board_category);
			
				request.setAttribute("pno", pno);
				request.setAttribute("count", count);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("navsize", navsize);
				request.setAttribute("board_category", board_category);
			
				List<BoardReportDto> dto = boardDao.getReportCGList(param);

				for(int i = 0; i < dto.size(); i ++) {
					int board_no = dto.get(i).getReport_board_no();
					int addCount = boardDao.reportCountAdd(dto.get(i).getBoard_no());
					dto.get(i).setReport_count(addCount);
					
					if(board_no !=0) {
						model.addAttribute("list", dto);
					}			
				}			
				return "admin/blacklist";
			}
	}
	
//////////////////////////////////////////////////////////////////////////////////////////	
	@GetMapping("/replyblacklist")
	public String Rblacklist(Model model, HttpServletRequest request,
											@RequestParam(required = false, defaultValue = "0") String type,
											@RequestParam(required = false, defaultValue = "0") String keyword) {
		//페이지 크기
			int pagesize = 10;
			//네비게이터 크기
			int navsize = 10;
			
			//페이징 추가
			int pno;
			try{
				pno = Integer.parseInt(request.getParameter("pno"));
				if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
			}
			catch(Exception e){
				pno = 1;
			}
				
			int finish = pno * pagesize;
			int start = finish - (pagesize - 1);			
		//**************************************************************************************
		//			 		하단 네비게이터 계산하기
		//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
		//**************************************************************************************
			int count = boardDao.reportRPCount(); //신고댓글 개수를 구하는 메소드 			
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
			int startBlock = (pno -1) / navsize * navsize + 1;
			int finishBlock = startBlock + (navsize -1);
			
			//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
			if(finishBlock > pagecount){
				finishBlock = pagecount;
			}
			
			if(keyword != null) {
				Map<String, String> param = new HashMap<>();
				param.put("start", String.valueOf(start));
				param.put("finish", String.valueOf(finish));
				param.put("type", type);
				param.put("keyword", keyword);
				
				List<BoardReportDto> dto = boardDao.reportRPSearch(param);
				
				for(int i = 0; i < dto.size(); i ++) {
					int board_reply_no = dto.get(i).getReport_reply_no();
					int addCount = boardDao.reportCountAdd2(dto.get(i).getReport_reply_no());
					dto.get(i).setReport_count(addCount);

					if(board_reply_no !=0) {
						model.addAttribute("list", dto);
					}			
				}			
				
				request.setAttribute("pno", pno);
				request.setAttribute("count", count);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("navsize", navsize);
				request.setAttribute("type", type);
				request.setAttribute("keyword", keyword);
		
			return  "admin/replyblacklist";	
			}
			
			else {
				Map<String, String> param = new HashMap<>();
				param.put("start", String.valueOf(start));
				param.put("finish", String.valueOf(finish));
				
				List<BoardReportDto> dto = boardDao.getReportRPList(param);
				
				for(int i = 0; i < dto.size(); i ++) {
					int board_reply_no = dto.get(i).getReport_reply_no();
					int addCount = boardDao.reportCountAdd2(dto.get(i).getReport_reply_no());
					dto.get(i).setReport_count(addCount);

					if(board_reply_no !=0) {
						model.addAttribute("list", dto);
					}			
				}			
				
				request.setAttribute("pno", pno);
				request.setAttribute("count", count);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("navsize", navsize);
		
			return  "admin/replyblacklist";	
			}
	}
	
	//게시글 삭제
	@GetMapping("/delete2")
		public String delete(@RequestParam List<Integer> board_no) {
		
		for(int i = 0; i < board_no.size(); i ++) {
//		실제파일 삭제
			boardfileService.deleteRealfile(board_no.get(i));
//		DB에서 게시글 삭제
			boardDao.delete(board_no.get(i)); 			
		}		
		return "redirect:blacklist";
	}
	
	
	//댓글 삭제
	@PostMapping("/delete")
	public String deletereply(@RequestParam List<Integer> board_reply_no,
													@RequestParam List<Integer> board_reply_origin) {
		for(int i = 0; i < board_reply_no.size(); i ++) {
			sqlSession.delete("board.deleteReply", board_reply_no.get(i));
			boardDao.replyCount(board_reply_origin.get(i));			
		}
		
		return "redirect:replyblacklist";
	}
	
	//이메일 전송
		@GetMapping("/send")
		@ResponseBody
		private String send(@RequestParam(required = false) List<Integer> board_no,
											@RequestParam(required = false) List<Integer> board_reply_no) {	
			if(board_reply_no==null) {
				for(int i = 0; i < board_no.size(); i ++) {
					BoardDto dto = boardDao.get(board_no.get(i));
					String board_writer = dto.getBoard_writer();
			
					UsersDto udto = usersDao.getInfo(board_writer);
					String email = udto.getEmail();
					emailService.sendEmail(email);
				}
				return "success";
			}
			else {
				for(int i = 0; i < board_reply_no.size(); i ++) {
					BoardReplyDto dto = boardDao.getReply(board_reply_no.get(i));
					String board_reply_writer = dto.getBoard_reply_writer();
			
					UsersDto udto = usersDao.getInfo(board_reply_writer);
					String email = udto.getEmail();
					emailService.sendEmail(email);
				}
				return "success";
			}

	}
}		
