package com.kh.finalproject;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.finalproject.entity.QuestionDto;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})
@WebAppConfiguration
@Slf4j
public class QTest {
	@Autowired
	private SqlSession sqlSession;
	
	// db와 접속되는지 확인
	@Test
	public void test() {
		QuestionDto dto = QuestionDto.builder()
				.question_title("test1")
				.user_custom_question_no(1)
				.question_content("testest")
				.question_answer("정답은 이래요")
				.question_solution("이래서 틀려요")
				.question_premium(0)
				.build();
		sqlSession.insert("question.upload",dto);
		log.info("");
	}
}
