package com.kh.finalproject.restcontroller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.finalproject.entity.RcorrectDto;
import com.kh.finalproject.entity.ResultDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadTestQuestionFileDto;
import com.kh.finalproject.payvo.RateVO;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.repository.TestDao;
import com.kh.finalproject.vo.SetScoreVO;

import lombok.extern.slf4j.Slf4j;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/question2")
@Slf4j
public class RestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private TestDao testDao;
	
	@Autowired
	private NormalUploadQuestionDao  NormalUploadQuestionDao;
	
	@PostMapping("insert")
	public String insert(@RequestParam int test_no, int correct, int answer, int iscorrect, HttpSession session) {
		int result_no = (int) session.getAttribute("rno");

		RcorrectDto rcorrectDto = RcorrectDto.builder()
																	.test_no(test_no)
																	.correct(correct)
																	.result_no(result_no)
																	.iscorrect(iscorrect)
																	.answer(answer)
																	.build();
	int count=	sqlSession.selectOne("rcorrectCount", rcorrectDto);
	
	
	 if(count>=0) {
		 
		 sqlSession.delete("deleteAns", rcorrectDto); 
		 
	 }
		 
		 sqlSession.update("correct2", rcorrectDto);
		 
		 int totalNum = sqlSession.selectOne("totalNum", test_no); 
		 int correctNum = sqlSession.selectOne("correctNum", test_no);
		 int sum = correctNum*100/totalNum;
		 log.info("total={}", totalNum);
		 log.info("correctsum={}", correctNum);
		 log.info("sumsum={}", sum);
		 
		 RateVO vo = RateVO.builder()
				 .sum(sum)
				 .test_no(test_no)
				 .build();
		 
		 sqlSession.update("rate", vo);
		
		
		return null;
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam int test_no, HttpSession session) {
		
		
		
		int result_no = (int) session.getAttribute("rno");
		RcorrectDto rcorrectDto = RcorrectDto.builder()
																	.test_no(test_no)
																	.result_no(result_no)
																	.build();
		sqlSession.delete("deleteAns", rcorrectDto);
//		session.removeAttribute("rno");
		return null;
	}
	
	
	
	@PostMapping("delete2")
	public int delete2(@RequestParam int test_no, HttpSession session) {
		
	
		
		
		
		int result_no = (int) session.getAttribute("rno");
	
		RcorrectDto rcorrectDto = RcorrectDto.builder()
																	.test_no(test_no)
																	.result_no(result_no)
																	.build();
	
		
			int rqno=	NormalUploadQuestionDao.rqno(rcorrectDto);

		int correct;
		if(rqno!=0) {
			
			


			if(sqlSession.selectOne("selectCorrect", rcorrectDto)!=null) {
				correct=sqlSession.selectOne("test.selectCorrect", rcorrectDto);
			}
			else {
				correct=0;
			}


			
		
			return correct;
			
			
		}
		
		
		
		else {
		  return 0;
		}
		
		
	
		
	}
	
	
	
	

	
	
	@PostMapping("resultin")
	public int resultin(@RequestParam String csname, int tno, String id, HttpSession session) {
		int rno = sqlSession.selectOne("resultno");
		
		ResultDto resultDto = ResultDto.builder()
											.user_id(id)
											.test_no(tno)
											.cs_no(csname)
											.rno(rno)
											.build();
		sqlSession.insert("resultinn", resultDto);
		session.setAttribute("rno", rno);
	
		return rno;
	}
	
	@PostMapping("callcategory")
	public List<TestQuestionDto> callcategory(@RequestParam String csname) {
		List<TestQuestionDto> quesList = sqlSession.selectList("callcategory", csname);
		return quesList;
	}
	
	
	@PostMapping("newwindow")
	public String newwindow(HttpSession session) {

			session.setAttribute("no", 0);

		return null;

	}
	@GetMapping("queup")
	public String queup(@RequestParam  String session_ques, String csname, HttpSession session) {
		
		log.info("checkcheckasdasdasdasd={}", csname);
		int rno = (int) session.getAttribute("rno");
		
		
		SetScoreVO result = SetScoreVO.builder()
													.rno(rno)
													.session_ques(session_ques)
													.csname(csname)
													.build();
		List<TestQuestionDto> TQDto = sqlSession.selectList("readyForAns", result);
		
		for(TestQuestionDto insertQues : TQDto) {
		log.info("asdasd={}", insertQues.getAnswer());
		log.info("asdasdasd={}", insertQues.getNo());
			RcorrectDto dto = RcorrectDto.builder()
															.result_no(rno)
															.test_no(insertQues.getNo())
															.correct(0)
															.iscorrect(0)
															.answer(insertQues.getAnswer())
															.build();
			sqlSession.insert("correct", dto);
		}
		
		
		sqlSession.update("quesup", result);
		return null;
	}
	
	@PostMapping("deletefile")
	public String deletefile(@RequestParam int no) {
		UploadTestQuestionFileDto	 delete = 
				NormalUploadQuestionDao.getFile(no);
		
		if(delete!=null) {
		String filepath = "D:/upload/normalquestion_image/"+delete.getFile_save_name();
		File file = new File(filepath);
		file.delete();
		}
		NormalUploadQuestionDao.onlyfileDelete(no);
		return null;
		
		/* "redirect:question/normalupdate"+no; */
	}
	
	
	@PostMapping("deletesession")
	public String deletesession(HttpSession session) {
		
		int no=(int)session.getAttribute("no");
		
		session.setAttribute("no", no-2);
		return null;
		
	}
	
	
}