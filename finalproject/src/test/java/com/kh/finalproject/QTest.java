package com.kh.finalproject;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.finalproject.entity.UploadQuestionDto;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})
@WebAppConfiguration

public class QTest {
	@Autowired
	private SqlSession sqlSession;
	
	@Test
	public void upload() {
		UploadQuestionDto dto = UploadQuestionDto.builder()
				.question_title("문제 제목")
				.user_custom_question_no(4)
				.question_content("문제")
				.question_answer("정답")
				.question_solution("해설지")
				.user_no(24)
				.category_name("test")
				.answer1("선택지1")
				.answer2("선택지2")
				.answer3("선택지3")
				.answer4("선택지4")
				.answer5("선택지5")
				.build();
		sqlSession.insert("question.upload_sub", dto);
		sqlSession.insert("question.upload", dto);
	}
}
