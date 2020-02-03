package com.kh.finalproject.restcontroller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.RcorrectDto;
import com.kh.finalproject.repository.TestDao;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/question2")
public class RestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private TestDao testDao;
	
	@PostMapping("insert")
	public String insert(@RequestParam int test_no, int correct, int result_no) {
		RcorrectDto rcorrectDto = RcorrectDto.builder()
																	.test_no(test_no)
																	.correct(correct)
																	.result_no(result_no)
																	.build();
		sqlSession.insert("correct", rcorrectDto);
		
		return null;
	}
	
}
