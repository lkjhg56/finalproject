package com.kh.finalproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.UploadQuestionDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})
@WebAppConfiguration

public class QTest {
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	
	@Test
	public void questionSequece() {
		int seq= uploadQuestionDao.questionSequece();
		
		UploadQuestionDto uploadQuestionDto = UploadQuestionDto.builder()
				.question_no(seq)
				.question_title("���� ���ε�")
				.question_content("������ ����")
				.question_answer("������ ����")
				.question_solution("�ؼ��� ����")
				.user_custom_question_no(10)
				.user_no(24)
				.category_name("�����׽�Ʈ")
				.answer1("���Ͼ��ε�1")
				.answer2("���Ͼ��ε�2")
				.answer3("���Ͼ��ε�3")
				.answer4("���Ͼ��ε�4")
				.answer5("���Ͼ��ε�5")			
				.build();

		uploadQuestionDao.upload(uploadQuestionDto);
		
		//2. ���� ���ε�
		

		
		
		
		//DB�� �Է�
		UploadQuestionFileDto uploadQuestionFileDto = UploadQuestionFileDto.builder()
				.question_no(seq)
				.file_upload_name("���ε� �̸�")
				.file_save_name("���� ����� �̸�")
				.file_type("jepg")
				.file_size(4000)
				.build();
		uploadQuestionDao.fileUpload(uploadQuestionFileDto);
	}
	
}
