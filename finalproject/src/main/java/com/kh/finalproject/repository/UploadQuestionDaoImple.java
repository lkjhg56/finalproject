package com.kh.finalproject.repository;

import java.net.URLEncoder;
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
	public int userSequence() {
		return sqlSession.selectOne("question.userSequence");
	}	
//	DB에 업로드
	public void upload(UploadQuestionDto uploadQuestionDto) {
		sqlSession.insert("question.upload_sub", uploadQuestionDto);//user_costom_question 등록
		sqlSession.insert("question.upload", uploadQuestionDto);// question 등록
	}
	//파일 업로드
	@Override
	public void fileUpload(UploadQuestionFileDto uploadQuestionFileDto) {
		sqlSession.insert("question.upload_file", uploadQuestionFileDto);	
	}

	//ResponseEntity 헤더에 추가할 내용
	@Override
	public String makeDispositionString(UploadQuestionFileDto uploadQuestionFileDto) throws Exception {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attachment;");
		buffer.append("filename=");
		buffer.append("\"");
		buffer.append(URLEncoder.encode(uploadQuestionFileDto.getFile_upload_name(), "UTF-8"));
		buffer.append("\"");
		return buffer.toString();
	}
	@Override
	public void updateQustion(UploadQuestionDto uploadQuestionDto) {
		sqlSession.update("question.updateCustom",uploadQuestionDto);
		sqlSession.update("question.updateQuestion",uploadQuestionDto);
	}
	public void updateFile(UploadQuestionFileDto uploadQuestionFileDto) {
		
		sqlSession.update("question.updateFile",uploadQuestionFileDto);
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
