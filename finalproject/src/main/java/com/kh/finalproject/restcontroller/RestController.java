package com.kh.finalproject.restcontroller;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.RcorrectDto;
import com.kh.finalproject.entity.ResultDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.repository.TestDao;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/question2")
public class RestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private TestDao testDao;
	
	@PostMapping("insert")
	public String insert(@RequestParam int test_no, int correct, int result_no, int iscorrect) {
		RcorrectDto rcorrectDto = RcorrectDto.builder()
																	.test_no(test_no)
																	.correct(correct)
																	.result_no(result_no)
																	.iscorrect(iscorrect)
																	.build();
		sqlSession.insert("correct", rcorrectDto);
		
		return null;
	}
	
	@PostMapping("delete")
	public String delete(@RequestParam int test_no, int result_no) {
		RcorrectDto rcorrectDto = RcorrectDto.builder()
																	.test_no(test_no)
																	.result_no(result_no)
																	.build();
		sqlSession.delete("deleteAns", rcorrectDto);
		
		return null;
	}
	
	@PostMapping("resultin")
	public int resultin(@RequestParam String csname, int tno, String id) {
		
		int rno = sqlSession.selectOne("resultno");
		
		ResultDto resultDto = ResultDto.builder()
											.user_id(id)
											.test_no(tno)
											.cs_no(csname)
											.rno(rno)
											.build();
		sqlSession.insert("resultinn", resultDto);
		
		return rno;
	}
	
	@PostMapping("callcategory")
	public List<TestQuestionDto> callcategory(@RequestParam String csname) {
		List<TestQuestionDto> quesList = sqlSession.selectList("callcategory", csname);
		return quesList;
	}
	
}
