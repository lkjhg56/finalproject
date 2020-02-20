
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
import com.kh.finalproject.entity.ResultDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.repository.TestDao;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SolveQuestionController {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	private TestDao testDao;
	
	@Autowired
	private NormalUploadQuestionDao NormalUploadQuestionDao;
	
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
	public String category(@RequestParam int tno, Model model,HttpSession session) {
		model.addAttribute("tno", tno);
		model.addAttribute("list", testDao.getDetailList(tno));
//		session.removeAttribute("no");
		return "question/typechoice";
	}
	
	
	@GetMapping("question/questype2")
	public String category2(@RequestParam int tno, Model model) {
		model.addAttribute("list", testDao.getDetailList(tno));
		return "question/onetypechoice";
	}
	
	
	
	@GetMapping("question/questcategory")
public String category2(@RequestParam String categoryname, int tno,String session, String hour, String min, String method, String category_no,HttpSession httpSession,Model model) {

		if(method.equals("한번에풀기")) {
			List<TestQuestionDto> questionDto = testDao.getQuestionList(categoryname);
			
			List<TestQuestionDto> question = new ArrayList<>();		
	
//			 UploadTestQuestionFileDto uploadTestQuestionFileDto;
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
					dto.setRate(qlist.getRate());
					dto.setLim_min(2);
					dto.setLim_hour(0);
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
				
				int rno = (int) httpSession.getAttribute("rno");//
				TestQuestionDto testQuestionDto = TestQuestionDto.builder()
																	.csname(categoryname)//
																	.category_no(category_no)
																	.build();
				log.info("checking={}", categoryname+category_no);//
				List<ResultDto> sum= sqlSession.selectList("getSum", testQuestionDto);
				int total=0;
				int num=0;
				for(ResultDto dto : sum) {
					total += dto.getSumscore();
					num +=1;
				}
				log.info("total={}", total);
				log.info("num={}", num);
				
				int average = total/num;
				int high10 = num/10;
				int high25 = num/4;
				int high50 =  num/2;
				int high75 = num*3/4;
				int high10average = 0;
				int high25average = 0;
				int high50average = 0;
				int high75average = 0;
				
				testQuestionDto = TestQuestionDto.builder()
						.csname(categoryname)//
						.category_no(category_no)
						.end(high10)
						.rno(rno)
						.build();
				total = 0;
				if(high10<1) {
					high10average=average;
				}else {
					List<ResultDto> sum2 = sqlSession.selectList("getSum2", testQuestionDto);
					for(ResultDto dto : sum2) {
						total+= dto.getSumscore();
					}
					high10average=total/high10;
				}
				total = 0;
				if(high25<1) {
					high25average = average;
				}else {
					testQuestionDto = TestQuestionDto.builder()
							.csname(categoryname)//
							.category_no(category_no)
							.end(high25)
							.rno(rno)
							.build();
					List<ResultDto> sum3 = sqlSession.selectList("getSum2", testQuestionDto);
					for(ResultDto dto : sum3) {
						total+= dto.getSumscore();
					}
					high25average = total/high25;
				
				}
				total = 0;
				if(high50<1) {
					high50average = average;
				}else {
					testQuestionDto = TestQuestionDto.builder()
							.csname(categoryname)
							.category_no(category_no)
							.end(high50)
							.rno(rno)
							.build();
					List<ResultDto> sum3 = sqlSession.selectList("getSum2", testQuestionDto);
					for(ResultDto dto : sum3) {
						total+= dto.getSumscore();
					}
					high50average = total/high50;
				
				}
				total = 0;
				if(high75<1) {
					high75average = average;
				}else {
					testQuestionDto = TestQuestionDto.builder()
							.csname(categoryname)
							.category_no(category_no)
							.end(high75)
							.rno(rno)
							.build();
					List<ResultDto> sum3 = sqlSession.selectList("getSum2", testQuestionDto);
					for(ResultDto dto : sum3) {
						total+= dto.getSumscore();
					}
					high75average = total/high75;
				
				}


				
				
				List<TestQuestionDto> answerList = sqlSession.selectList("getQuesNum", testQuestionDto);
			
				List<RcorrectDto> rCorrectDto = sqlSession.selectList("getCorrectList", rno);

				
				int rank = sqlSession.selectOne("getRank", testQuestionDto);
				
				int percentile = rank*100/num;
				
				
				model.addAttribute("rCorrectDto", rCorrectDto);
				model.addAttribute("answerList", answerList);
				model.addAttribute("score",  testDao.getScore(rno, category_no,categoryname));//
				model.addAttribute("csname", categoryname);//
				model.addAttribute("solved", num);
				model.addAttribute("average", average);
				model.addAttribute("high10average", high10average);
				model.addAttribute("high25average", high25average);
				model.addAttribute("high50average", high50average);
				model.addAttribute("high75average", high75average);
				model.addAttribute("rank", rank);
				model.addAttribute("percentile", percentile);
				
				return "question/result";
			}
			
			int no= (int)httpSession.getAttribute("no");
			TestQuestionDto tdto= testDao.getDto(categoryname,no,httpSession);
			
			
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
					
					dto.setLim_min(1);
					dto.setLim_hour(0);
				}
			
			model.addAttribute("clist", dto);
			model.addAttribute("session", session);
			model.addAttribute("method", method);

			
			model.addAttribute("categoryname", categoryname);
			
			model.addAttribute("category_no", session);//
			model.addAttribute("hour", hour);
			model.addAttribute("min", min);
			model.addAttribute("tno", tno);
			model.addAttribute("no", no);
			return "question/one";
			
			
		
		}

	}

	
@PostMapping("question/questcategory")
	public String category(@RequestParam String categoryname,String category_no, String session, String hour, String min, String method, HttpSession httpSession,Model model) {

		
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
					dto.setLim_min(2);
					dto.setLim_hour(0);
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
			int dtocount=testDao.getDtocount(categoryname, category_no);
			
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
			
			
			int no= (int)httpSession.getAttribute("no");
			TestQuestionDto tdto= testDao.getDto(categoryname,no,httpSession);
			
			
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
					dto.setLim_min(2);
					dto.setLim_hour(0);
				}
			
			model.addAttribute("clist", dto);
			model.addAttribute("session", session);
			model.addAttribute("method", method);
			model.addAttribute("categoryname", categoryname);

			model.addAttribute("hour", hour);
			model.addAttribute("min", min);
			
		
			
			model.addAttribute("category_no", session);//
	
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
		log.info("checking={}", csname+category_no);
		List<ResultDto> sum= sqlSession.selectList("getSum", testQuestionDto);
		int total=0;
		int num=0;
		for(ResultDto dto : sum) {
			total += dto.getSumscore();
			num +=1;
		}
		log.info("total={}", total);
		log.info("num={}", num);
		
		int average = total/num;
		int high10 = num/10;
		int high25 = num/4;
		int high50 =  num/2;
		int high75 = num*3/4;
		int high10average = 0;
		int high25average = 0;
		int high50average = 0;
		int high75average = 0;
		
		testQuestionDto = TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.end(high10)
				.rno(rno)
				.build();
		total = 0;
		if(high10<1) {
			high10average=average;
		}else {
			List<ResultDto> sum2 = sqlSession.selectList("getSum2", testQuestionDto);
			for(ResultDto dto : sum2) {
				total+= dto.getSumscore();
			}
			high10average=total/high10;
		}
		total = 0;
		if(high25<1) {
			high25average = average;
		}else {
			testQuestionDto = TestQuestionDto.builder()
					.csname(csname)
					.category_no(category_no)
					.end(high25)
					.rno(rno)
					.build();
			List<ResultDto> sum3 = sqlSession.selectList("getSum2", testQuestionDto);
			for(ResultDto dto : sum3) {
				total+= dto.getSumscore();
			}
			high25average = total/high25;
		
		}
		total = 0;
		if(high50<1) {
			high50average = average;
		}else {
			testQuestionDto = TestQuestionDto.builder()
					.csname(csname)
					.category_no(category_no)
					.end(high50)
					.rno(rno)
					.build();
			List<ResultDto> sum3 = sqlSession.selectList("getSum2", testQuestionDto);
			for(ResultDto dto : sum3) {
				total+= dto.getSumscore();
			}
			high50average = total/high50;
		
		}
		total = 0;
		if(high75<1) {
			high75average = average;
		}else {
			testQuestionDto = TestQuestionDto.builder()
					.csname(csname)
					.category_no(category_no)
					.end(high75)
					.rno(rno)
					.build();
			List<ResultDto> sum3 = sqlSession.selectList("getSum2", testQuestionDto);
			for(ResultDto dto : sum3) {
				total+= dto.getSumscore();
			}
			high75average = total/high75;
		
		}


		
		
		List<TestQuestionDto> answerList = sqlSession.selectList("getQuesNum", testQuestionDto);
	
		List<RcorrectDto> rCorrectDto = sqlSession.selectList("getCorrectList", rno);

		
		int rank = sqlSession.selectOne("getRank", testQuestionDto);
		
		int percentile = rank*100/num;
		
		
		model.addAttribute("rCorrectDto", rCorrectDto);
		model.addAttribute("answerList", answerList);
		model.addAttribute("score",  testDao.getScore(rno, category_no, csname));
		model.addAttribute("csname", csname);
		model.addAttribute("solved", num);
		model.addAttribute("average", average);
		model.addAttribute("high10average", high10average);
		model.addAttribute("high25average", high25average);
		model.addAttribute("high50average", high50average);
		model.addAttribute("high75average", high75average);
		model.addAttribute("rank", rank);
		model.addAttribute("percentile", percentile);
		return "question/result";
	}
	
	@GetMapping("question/oneresult")
	public String result2(@RequestParam String category_no, String csname, String method,HttpSession session, Model model) {
	
	
		TestQuestionDto testQuestionDto = TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();


					
		
		model.addAttribute("category_no", category_no);
		model.addAttribute("csname", csname);
		model.addAttribute("method", method);
		
		return "question/oneresult";
	}
	
	
	@PostMapping("question/solving")
	public String solving(@RequestParam int tno,Model model) {
		model.addAttribute("tno", tno);
		return "question/solving";
	}
	
}
