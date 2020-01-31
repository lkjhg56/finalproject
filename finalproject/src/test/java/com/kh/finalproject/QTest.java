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
				.question_title("���� ����")
				.user_custom_question_no(4)
				.question_content("����")
				.question_answer("����")
				.question_solution("�ؼ���")
				.user_no(24)
				.category_name("test")
				.answer1("������1")
				.answer2("������2")
				.answer3("������3")
				.answer4("������4")
				.answer5("������5")
				.build();
		sqlSession.insert("question.upload_sub", dto);
		sqlSession.insert("question.upload", dto);
	}
}
