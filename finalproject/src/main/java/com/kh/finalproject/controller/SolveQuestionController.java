package com.kh.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.RcorrectDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.repository.TestDao;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SolveQuestionController {
	
	@Autowired
	SqlSession sqlSession;
	
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

public String category2(@RequestParam String categoryname, String session, String hour, String min, String method, HttpSession httpSession,Model model) {

		if(method.equals("한번에풀기")) {
			List<TestQuestionDto> questionDto = testDao.getQuestionList(categoryname);
			List<TestQuestionDto> question = new ArrayList<>();		
			for(TestQuestionDto qlist : questionDto) {
				if(qlist.getCategory_no().equals(session)) {
					TestQuestionDto dto = new TestQuestionDto();
					dto.setNo(qlist.getNo());
					log.info("testno 확인 {}=", qlist.getNo());
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

			model.addAttribute("method", method);
			model.addAttribute("csname", categoryname);
			model.addAttribute("hour", hour);
			model.addAttribute("min", min);

			log.info("session={}", session);
			
			return "question/plural";
		}

		else {
			
			if(httpSession.getAttribute("no")==null ) {
				httpSession.setAttribute("no", 0);
			}
		
			int sessioncount=(int)httpSession.getAttribute("no");
			int dtocount=testDao.getDtocount(categoryname, session);
			
			if(dtocount<=sessioncount) {
				httpSession.removeAttribute("no");
				
				int rno = (int) httpSession.getAttribute("rno");
				TestQuestionDto testQuestionDto = TestQuestionDto.builder()
						.csname(categoryname)
						.category_no(session)
						.build();


				List<TestQuestionDto> answerList = sqlSession.selectList("getQuesNum", testQuestionDto);
				List<RcorrectDto> rCorrectDto = sqlSession.selectList("getCorrectList", rno);

				model.addAttribute("rCorrectDto", rCorrectDto);
				model.addAttribute("answerList", answerList);
				model.addAttribute("score",  testDao.getScore(rno, session, categoryname));
			
								
			
				
				model.addAttribute("category_no", session);
				model.addAttribute("csname", categoryname);
				model.addAttribute("method", method);
				
				
				return "question/result";
			}
			
			
			TestQuestionDto tdto= testDao.getDto(categoryname,httpSession);
			
			
			TestQuestionDto dto=new TestQuestionDto();
			
			
				if(tdto.getCategory_no().equals(session)) {
					dto.setNo(tdto.getNo());
					dto.setAnswer(tdto.getAnswer());
					dto.setCategory_no(tdto.getCategory_no());
					dto.setDis1(tdto.getDis1());
					dto.setDis2(tdto.getDis2());
					dto.setDis3(tdto.getDis3());
					dto.setDis4(tdto.getDis4());
					dto.setDis5(tdto.getDis5());
					dto.setQuestion(tdto.getQuestion());
				}
			
			model.addAttribute("clist", dto);
			model.addAttribute("session", session);
			model.addAttribute("method", method);
			model.addAttribute("csname", categoryname);
	
			return "question/one";
			
			
		
		}

	}

	
@PostMapping("question/questcategory")
	public String category(@RequestParam String categoryname, String session, String hour, String min, String method, HttpSession httpSession,Model model) {

		
		if(method.equals("한번에풀기")) {
			List<TestQuestionDto> questionDto = testDao.getQuestionList(categoryname);
			List<TestQuestionDto> question = new ArrayList<>();		
			for(TestQuestionDto qlist : questionDto) {
				if(qlist.getCategory_no().equals(session)) {
					TestQuestionDto dto = new TestQuestionDto();
					dto.setNo(qlist.getNo());
					log.info("testno 확인 {}=", qlist.getNo());
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

			model.addAttribute("method", method);
			model.addAttribute("csname", categoryname);
			model.addAttribute("hour", hour);
			model.addAttribute("min", min);

			log.info("session={}", session);
			
			return "question/plural";
		}

		else {
			
			if(httpSession.getAttribute("no")==null ) {
				httpSession.setAttribute("no", 0);
			}
		
			int sessioncount=(int)httpSession.getAttribute("no");
			int dtocount=testDao.getDtocount(categoryname, session);
			
			if(dtocount<=sessioncount) {
				httpSession.removeAttribute("no");
				
				int rno = (int) httpSession.getAttribute("rno");
				TestQuestionDto testQuestionDto = TestQuestionDto.builder()
						.csname(categoryname)
						.category_no(session)
						.build();


				List<TestQuestionDto> answerList = sqlSession.selectList("getQuesNum", testQuestionDto);
				List<RcorrectDto> rCorrectDto = sqlSession.selectList("getCorrectList", rno);

				model.addAttribute("rCorrectDto", rCorrectDto);
				model.addAttribute("answerList", answerList);
				model.addAttribute("score",  testDao.getScore(rno, session, categoryname));
			
								
			
				
				model.addAttribute("category_no", session);
				model.addAttribute("csname", categoryname);
				model.addAttribute("method", method);
				
				
				return "question/result";
			}
			
			
			TestQuestionDto tdto= testDao.getDto(categoryname,httpSession);
			
			
			TestQuestionDto dto=new TestQuestionDto();
			
			
				if(tdto.getCategory_no().equals(session)) {
					dto.setNo(tdto.getNo());
					dto.setAnswer(tdto.getAnswer());
					dto.setCategory_no(tdto.getCategory_no());
					dto.setDis1(tdto.getDis1());
					dto.setDis2(tdto.getDis2());
					dto.setDis3(tdto.getDis3());
					dto.setDis4(tdto.getDis4());
					dto.setDis5(tdto.getDis5());
					dto.setQuestion(tdto.getQuestion());
				}
			
			model.addAttribute("clist", dto);
			model.addAttribute("session", session);
			model.addAttribute("method", method);
			model.addAttribute("csname", categoryname);
	
			return "question/one";
			
			
		
		}

	}


	

	@GetMapping("question/result")
	public String result(@RequestParam String category_no, String csname, HttpSession session, Model model) {
		int rno = (int) session.getAttribute("rno");
		TestQuestionDto testQuestionDto = TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();


		List<TestQuestionDto> answerList = sqlSession.selectList("getQuesNum", testQuestionDto);
		List<RcorrectDto> rCorrectDto = sqlSession.selectList("getCorrectList", rno);

		model.addAttribute("rCorrectDto", rCorrectDto);
		model.addAttribute("answerList", answerList);
		model.addAttribute("score",  testDao.getScore(rno, category_no, csname));
		model.addAttribute("csname", csname);
		return "question/result";
	}
	
	@GetMapping("question/oneresult")
	public String result2(@RequestParam String category_no, String csname, String method,HttpSession session, Model model) {
		int rno = (int) session.getAttribute("rno");
	
		TestQuestionDto testQuestionDto = TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();


						
	
		
		model.addAttribute("category_no", category_no);
		model.addAttribute("csname", csname);
		model.addAttribute("method", method);
		return "question/oneresult";
	}
	
}
