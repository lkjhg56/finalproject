package com.kh.finalproject.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.SolutionDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadTestQuestionFileDto;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;

@Repository
public class NormalUploadQuestionDaoImpl implements NormalUploadQuestionDao{

	@Autowired
private SqlSession sqlSession;
	
//	시퀀스 미리뽑기
	public int testquestionSequece() {
		return sqlSession.selectOne("question.testquestionSequence"); 
	}
	public int categorySequence() {
		return sqlSession.selectOne("question.categorySequence");
		
	}	

	public int testSequece() {
		return sqlSession.selectOne("question.testSequence");
	}
	
	
	@Override
	public int categoryExist(NormalUpdateQuestionVO normalUpdateQuestionVO ) {
	
		return sqlSession.selectOne("question.categoryE",normalUpdateQuestionVO);
	}

	//db에업로드
	
	//카테고리 없을때
	@Override
	public void upload(TestQuestionDto testQuestionDto, CategoryDto categoryDto, TestDto testDto,SolutionDto solutionDto) {
		sqlSession.insert("question.uploadtest", testDto);// 테스트등록
		
		sqlSession.insert("question.uploadcategory", categoryDto);// 카테고리 등록
		
		
		sqlSession.insert("question.uploadtestquestion", testQuestionDto);//test_question 등록
		sqlSession.insert("question.uploadsolution",solutionDto);
		
		
	}
	
	//카테고리에 값이 있을때
	@Override
	public void upload2(TestQuestionDto testQuestionDto, SolutionDto solutionDto) {
		sqlSession.insert("question.uploadtestquestion", testQuestionDto);//test_question 등록
		sqlSession.insert("question.uploadsolution",solutionDto);
		
		
	}
	
	
	@Override
	public void fileUpload(UploadTestQuestionFileDto uploadTestQuestionFileDto) {
		sqlSession.insert("question.normalupload_file", uploadTestQuestionFileDto);	
		
	}
	
	
}
