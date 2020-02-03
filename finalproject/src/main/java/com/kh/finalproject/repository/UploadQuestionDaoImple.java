package com.kh.finalproject.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;

@Repository
public class UploadQuestionDaoImple implements UploadQuestionDao{
	@Autowired
	private SqlSession sqlSession;
	

	public int questionSequece() {
		return sqlSession.selectOne("question.sequence"); 
	}
	
//	���� ���
	public void upload(UploadQuestionDto uploadQuestionDto) {
		sqlSession.insert("question.upload_sub", uploadQuestionDto);
		sqlSession.insert("question.upload", uploadQuestionDto);
	}

	@Override
	public void fileUpload(UploadQuestionFileDto uploadQuestionFileDto) {
		//���� ���� ����
		sqlSession.insert("question.upload_file", uploadQuestionFileDto);
		
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
	
	

	@Override
	public UploadQuestionDto getOne() {
		
		return null;
	}

	@Override
	public List<UploadQuestionDto> getList() {
		return sqlSession.selectList("question.getList");
	}


}
