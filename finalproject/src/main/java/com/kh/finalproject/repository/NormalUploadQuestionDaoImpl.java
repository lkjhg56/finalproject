package com.kh.finalproject.repository;

import java.net.URLEncoder;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.SolutionDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
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
	
	//파일검색
	@Override
	public UploadTestQuestionFileDto getFile(int no) {
		return sqlSession.selectOne("question.getNormalFile",no);		
	}
	@Override
	public String makeDispositionString(UploadTestQuestionFileDto uploadTestQuestionFileDto) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attachment;");
		buffer.append("filename=");
		buffer.append("\"");
		buffer.append(URLEncoder.encode(uploadTestQuestionFileDto.getFile_upload_name(), "UTF-8"));
		buffer.append("\"");
		return buffer.toString();
	}
	@Override
	public void updateQustion(NormalUpdateQuestionVO normalUpdateQuestionVO) {
		sqlSession.update("question.updateTestQuestion",normalUpdateQuestionVO);
		sqlSession.update("question.updateSolution",normalUpdateQuestionVO);
		
	}
	@Override
	public void updateFile(UploadTestQuestionFileDto uploadTestQuestionFileDto) {
		sqlSession.update("updateTestFile",uploadTestQuestionFileDto);
		
	}
	@Override
	public void fileDelete2(int no) {
		sqlSession.delete("question.deleteTestQuestion",no);
		
	}
	
	
}
