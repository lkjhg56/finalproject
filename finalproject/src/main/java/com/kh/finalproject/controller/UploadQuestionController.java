package com.kh.finalproject.controller;

import java.io.File;
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
import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UserQuestionMultiResultDto;
import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.repository.UsersDao;
import com.kh.finalproject.service.NormalUploadQuestionService;
import com.kh.finalproject.service.UploadQuestionService;
import com.kh.finalproject.vo.ExamResultVO;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;
import com.kh.finalproject.vo.SearchQuestionVO;
import com.kh.finalproject.vo.UpdateQuestionVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/question")
public class UploadQuestionController {
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private UploadQuestionService uploadQuestionService;	
	@Autowired
	private NormalUploadQuestionService normalUploadQuestionService;
	@Autowired
	private UsersDao usersDao;
	
	@Autowired
	private NormalUploadQuestionDao NormalUploadQuestionDao;
	//문제 풀기(한문제)
	@GetMapping("/solve")
	public String solve(@RequestParam int question_no, Model model) {
		model.addAttribute("questionDto",uploadQuestionDao.question_all(question_no));
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		return "question/solve";
	}
	@PostMapping("/solve")
	public String solve2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) {
		model.addAttribute("result", uploadQuestionService.questionSolve(updateQuestionVO));
		return "question/solve_result";
	}
	//문제 풀기(여러문제 한페이지당 최대 4문제)
	@GetMapping("/multi")
	public String multi(@RequestParam int wantQuestion, Model model) {
		List<UploadQuestionDto> list = uploadQuestionService.multiQuestion(wantQuestion);
		model.addAttribute("count",wantQuestion);
		model.addAttribute("list", list);
		return "question/multi";
	}
	@PostMapping("/multi")
	public String multi2(@ModelAttribute ExamResultVO examResultVO,
			@ModelAttribute UserQuestionResultDto dto, Model model) {	
		//문제를 푼 시간
		model.addAttribute("time",dto);
		//각 문제에 대한 번호, 결과값을 비교하는 서비스
		List<UserQuestionResultDto> list = uploadQuestionService.checkMulti(examResultVO);		
		model.addAttribute("list", list);
		UserQuestionMultiResultDto multi_dto = uploadQuestionService.insert_multi(list,dto);
		model.addAttribute("multi", multi_dto);
		return "question/multi_result";
	}
	//문제 업로드
	@GetMapping("/upload")
	public String upload() {
		return "question/upload";
	}
	@PostMapping("/upload")
	public String upload2(@ModelAttribute UpdateQuestionVO updateQuestionVO, HttpSession session, Model model) throws Exception {
		updateQuestionVO.setUser_no(sqlSession.selectOne("question.getNo", (String)session.getAttribute("id")));
		uploadQuestionService.questionUpload(updateQuestionVO);
		return "redirect:list";
	}	
	//파일 다운로드(미리보기)
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_file_no) throws Exception{
		return uploadQuestionService.downloadImg(question_file_no);
	}
	//문제 정보 호출
	@GetMapping("/content")
	public String content(@RequestParam int question_no, Model model) {
		//이미지 리스트로 불러옴
		model.addAttribute("questionDto", uploadQuestionDao.question_all(question_no));
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		return "question/content";
	}
	//문제 수정
	@GetMapping("/update")
	public String update(@RequestParam int question_no, Model model) {
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		model.addAttribute("questionDto",uploadQuestionDao.question_all(question_no));
		return "question/update";
	}
	@PostMapping("/update")
	public String update2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) throws Exception {		
		uploadQuestionService.questionUpdate(updateQuestionVO);
		return "redirect:content?question_no="+updateQuestionVO.getQuestion_no();
	}	
	//문제 삭제
	@GetMapping("/delete")
	public String delete(@RequestParam int question_no, @RequestParam int user_custom_question_no, Model model) {
		uploadQuestionService.questionDelete(question_no, user_custom_question_no);
		return "redirect:list";
	}
	//이미지 파일 삭제
	@PostMapping("/deleteFile")
	public void deleteFile(int question_no) {
		List<UploadQuestionFileDto> delete = uploadQuestionDao.getFile2(question_no);
		for(int j = 0;j<delete.size();j++) {
			String filepath = "D:/upload/kh2b/"+delete.get(j).getFile_save_name();		
			File file = new File(filepath);
			//실제 파일 삭제
			file.delete();
			//DB 내용 삭제
			uploadQuestionDao.deleteFile(delete.get(j).getQuestion_file_no());
			}
	}
	//문제 전체 리스트 호출(Users 테이블과 조인됨)
	@GetMapping("/list")
	public String list(Model model, HttpServletRequest request) {
		//정답률 계산하여 출력해줘야함.
//		model.addAttribute("list",uploadQuestionDao.question_user_all());
		//네비게이터
		//페이지 크기
		int pageSize = 15;
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
		//데이터 행이 150개가 있다면?
		int finish = pageNumber * pageSize;
		int start = finish - (pageSize-1);
		
		/*****************************************************/
		//	하단 네비게이터 계산하기
		/*****************************************************/
		//전체 등록되어 있는 문제 개수를 구함.
		int count = uploadQuestionDao.questionCount();
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
		model.addAttribute("list", uploadQuestionDao.mapList(param));			

		request.setAttribute("pno", pageNumber);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("navsize", navSize);
		return "question/list";
	}
	@PostMapping("/list")
	public String list2(Model model, HttpServletRequest request, @ModelAttribute SearchQuestionVO searchQuestionVO) {
		//네비게이터
		//페이지 크기
		int pageSize = 15;
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
		//데이터 행이 150개가 있다면?
		int finish = pageNumber * pageSize;
		int start = finish - (pageSize-1);
		
		/*****************************************************/
		//	하단 네비게이터 계산하기
		/*****************************************************/
		Map<String, String> param = new HashMap<>();
		param.put("start", String.valueOf(start));
		param.put("finish",String.valueOf(finish));
		param.put("option",searchQuestionVO.getOption());
		param.put("keyword",searchQuestionVO.getKeyword());
		model.addAttribute("list", uploadQuestionDao.mapSearchList(param));
		//전체 등록되어 있는 문제 개수를 구함.
		int count = uploadQuestionDao.mapSearchListCount(param);
		//전체 페이지 수
		int pageCount=(count+pageSize)/pageSize;
		int startBlock = (pageNumber-1) / navSize * navSize+1;
		int finishBlock = startBlock +(navSize-1);
		if(finishBlock>pageCount) {
			finishBlock=pageCount;
		}
		
		request.setAttribute("pno", pageNumber);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("navsize", navSize);
		request.setAttribute("option",searchQuestionVO.getOption());
		request.setAttribute("keyword",searchQuestionVO.getKeyword());
		return "question/list";
	}
	//사용자가 업로드한 문제 리스트
	@GetMapping("/my_upload_list")
	public String my_upload_list(Model model, HttpSession session, HttpServletRequest request) {
		String id = (String) session.getAttribute("id");
		//네비게이터
		//페이지 크기
		int pageSize = 15;
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
		int start = finish - (pageSize-1);

		/*****************************************************/
		//	하단 네비게이터 계산하기
		/*****************************************************/
		//전체 등록되어 있는 문제 개수를 구함.
		int count = uploadQuestionDao.questionCountId(id);
		
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
		param.put("user_no", usersDao.getUserNo(id));
		//사용자 ID를 세션에서 가져와 리스트를 뽑아준다.
		model.addAttribute("list", uploadQuestionDao.mapListId(param));
		
		request.setAttribute("pno", pageNumber);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("navsize", navSize);
		return "question/my_upload_list";
	}
	//일반문제만들기
	@GetMapping("/normalupload")
	public String normalupload() {
		return "question/normalupload";
	}
	
	
	@PostMapping("/normalupload")
	public String normalupload2(@ModelAttribute NormalUpdateQuestionVO normalUpdateQuestionVO, HttpSession session,Model model,HttpServletRequest request) throws Exception {
		
//		uploadQuestionService.questionUpload(updateQuestionVO);
		
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
		
//		model.addAttribute("list", NormalUploadQuestionDao.mapList2(param));
		
		
		request.setAttribute("pno", pageNumber);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("navsize", navSize);
		

		return "redirect: ../admin/admin_normal_question";
		
		
		
		
		
		
		
	}
	
	
	//일반문제 목록
	@GetMapping("/normallist")
	public String normallist(@RequestParam  String csname,String category_no,  Model model,HttpServletRequest request) {

		TestQuestionDto dto=TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();
		List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal3",dto);
	
		
		
		/* model.addAttribute("list",list2); */
		
		
		

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
		param.put("csname", csname);
		param.put("category_no", category_no);
		
		model.addAttribute("list", NormalUploadQuestionDao.mapList2(param));
		
		
		request.setAttribute("pno", pageNumber);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pageSize);
		request.setAttribute("navsize", navSize);
		

		return "question/normallist2";
	}
	
	
	
	//일반문제 전체목록
	
	@GetMapping("/normallist2")
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
			
			
			
			return "question/normallist";
		
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
//		TestQuestionDto testQuestionDto= sqlSession.selectOne("question.getContent", no);
		log.info("aaano={}",no);
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
	public String delete2(@RequestParam int no, String csname,String category_no,  Model model) {
		
		normalUploadQuestionService.questionDelete(no,csname);
		TestQuestionDto dto=TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();
		List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal3",dto);
		model.addAttribute("list",list2);

		return "question/normallist";
	}


}
