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
				.question_title("파일 업로드")
				.question_content("문제의 파일")
				.question_answer("정답의 파일")
				.question_solution("해설의 파일")
				.user_custom_question_no(10)
				.user_no(24)
				.category_name("파일테스트")
				.answer1("파일업로드1")
				.answer2("파일업로드2")
				.answer3("파일업로드3")
				.answer4("파일업로드4")
				.answer5("파일업로드5")			
				.build();

		uploadQuestionDao.upload(uploadQuestionDto);
		
		//2. 파일 업로드
		

		
		
		
		//DB에 입력
		UploadQuestionFileDto uploadQuestionFileDto = UploadQuestionFileDto.builder()
				.question_no(seq)
				.file_upload_name("업로드 이름")
				.file_save_name("실제 저장될 이름")
				.file_type("jepg")
				.file_size(4000)
				.build();
		uploadQuestionDao.fileUpload(uploadQuestionFileDto);
	}
	
}
