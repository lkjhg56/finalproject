package com.kh.finalproject.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.UploadQuestionDto;

@Repository
public class UploadQuestionDaoImple implements UploadQuestionDao{
	@Autowired
	private SqlSession sqlSession;
	
//	���� ���
	public void upload(UploadQuestionDto uploadQuestionDto) {
		UploadQuestionDto dto = UploadQuestionDto.builder()
				.question_title(uploadQuestionDto.getQuestion_title())
				.user_custom_question_no(uploadQuestionDto.getUser_custom_question_no())
				.question_content(uploadQuestionDto.getQuestion_content())
				.question_answer(uploadQuestionDto.getQuestion_answer())
				.question_solution(uploadQuestionDto.getQuestion_solution())
				.user_no(uploadQuestionDto.getUser_no())
				.category_name(uploadQuestionDto.getCategory_name())
				.answer1(uploadQuestionDto.getAnswer1())
				.answer2(uploadQuestionDto.getAnswer2())
				.answer3(uploadQuestionDto.getAnswer3())
				.answer4(uploadQuestionDto.getAnswer4())
				.answer5(uploadQuestionDto.getAnswer5())
				.build();
		sqlSession.insert("question.upload_sub", dto);
		sqlSession.insert("question.upload", dto);
	}
//	���� ����
//	�������� ��� ���Ṯ�� ���� ���� ����
	public void update(UploadQuestionDto uploadQuestionDto, String admin) {

		boolean isAdmin = admin.equals("������");
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
	
	
//	���� ��ȸ
	@Override
	public UploadQuestionDto getOne() {
		
		return null;
	}
//	��ü ��ȸ
	@Override
	public List<UploadQuestionDto> getList() {
		return sqlSession.selectList("question.getList");
	}

}
