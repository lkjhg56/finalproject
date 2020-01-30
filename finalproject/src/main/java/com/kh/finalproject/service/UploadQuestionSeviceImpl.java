package com.kh.finalproject.service;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.finalproject.entity.UploadQuestionDto;

@Service
public class UploadQuestionSeviceImpl implements UploadQuestionService {

	@Autowired
	private SqlSession sqlSession;

	public void upload(UploadQuestionDto uploadQuestionDto) {
		UploadQuestionDto dto = UploadQuestionDto.builder()
				.question_title(uploadQuestionDto.getQuestion_title())
				.user_custom_question_no(uploadQuestionDto.getUser_custom_question_no())
				.question_content(uploadQuestionDto.getQuestion_content())
				.question_answer(uploadQuestionDto.getQuestion_answer())
				.question_solution(uploadQuestionDto.getQuestion_solution())
				.build();
		sqlSession.insert("question.upload", dto);
	}
//	관리자일 경우 premium 변경 가능하도록 해야함.

	@Override
	public void update(UploadQuestionDto uploadQuestionDto, String admin) {

		boolean isAdmin = admin.equals("관리자");
		UploadQuestionDto dto = new UploadQuestionDto();
		if (isAdmin) {
			dto = UploadQuestionDto.builder()
					.question_no(uploadQuestionDto.getQuestion_no())
					.question_title(uploadQuestionDto.getQuestion_title())
					.question_content(uploadQuestionDto.getQuestion_content())
					.question_answer(uploadQuestionDto.getQuestion_answer())
					.question_solution(uploadQuestionDto.getQuestion_solution())
					.question_premium(uploadQuestionDto.getQuestion_premium())
					.build();

		} else {
			dto = UploadQuestionDto.builder()
					.question_no(uploadQuestionDto.getQuestion_no())
					.question_title(uploadQuestionDto.getQuestion_title())
					.question_content(uploadQuestionDto.getQuestion_content())
					.question_answer(uploadQuestionDto.getQuestion_answer())
					.question_solution(uploadQuestionDto.getQuestion_solution())
					.build();
		}
		sqlSession.update("question.update", dto);
	}
}
