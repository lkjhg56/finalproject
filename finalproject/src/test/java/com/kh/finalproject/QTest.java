package com.kh.finalproject;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.finalproject.entity.UploadQuestionDto;

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
	
	// db�� ���ӵǴ��� Ȯ��
	@Test
	public void upload() {
		UploadQuestionDto dto = UploadQuestionDto.builder()
				.question_title("test1")
				.user_custom_question_no(1)
				.question_content("testest")
				.question_answer("������ �̷���")
				.question_solution("�̷��� Ʋ����")
				.build();
		sqlSession.insert("question.upload",dto);
	}

	@Test
	public void update() {
//				if("������") {
//					UploadQuestionDto dto = UploadQuestionDto.builder()
//							.question_title("test2")
//							.user_custom_question_no(1)
//							.question_content("testtesttest")
//							.question_answer("������ ������")
//							.question_solution("�ؼ��� ������")
//							.question_premium(0)
//							.build();
//					
//				}else {
//				}
				
					UploadQuestionDto dto = UploadQuestionDto.builder()
							.question_no(6)
							.question_title("test2")
							.user_custom_question_no(1)
							.question_content("testtesttest")
							.question_answer("������ ������")
							.question_solution("�ؼ��� ������")
							.build();					
		sqlSession.update("question.update",dto);
	}
	
}
