package com.kh.finalproject.repository;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UserQuestionResultDto;

@Repository
public class UploadQuestionDaoImple implements UploadQuestionDao{
	@Autowired
	private SqlSession sqlSession;
//	시퀀스 미리뽑기
	public int questionSequece() {
		return sqlSession.selectOne("question.sequence"); 
	}
	public int userSequence() {
		return sqlSession.selectOne("question.userSequence");
		
	}	
	@Override
	public int questionResultSequece() {
		return sqlSession.selectOne("question.questionSequence");
	}
	
	//DB에 업로드
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
	//문제 수정
	@Override
	public void updateQustion(UploadQuestionDto uploadQuestionDto) {
		sqlSession.update("question.updateCustom",uploadQuestionDto);
		sqlSession.update("question.updateQuestion",uploadQuestionDto);
	}
	public void updateFile(UploadQuestionFileDto uploadQuestionFileDto) {		
		sqlSession.update("question.updateFile",uploadQuestionFileDto);
	}
	//리스트 조회를 위한 것
	@Override
	public List<UploadQuestionDto> getList() {
		return sqlSession.selectList("question.getList");
	}
	//파일 삭제를 위한 검색
	@Override
	public UploadQuestionFileDto getFile(int question_no) {
		return sqlSession.selectOne("question.getFile",question_no);		
	}
	//파일 삭제(빈테이블의 경우 처리해줘야함. 조회 후 null값일 경우 무시해야함.)
	public void fileDelete2(int question_no,int user_custom_question_no, int question_file_no) {	
		sqlSession.delete("question.deleteFile", question_file_no);
		sqlSession.delete("question.deleteUser",user_custom_question_no);
		sqlSession.delete("question.deleteQuestion",question_no);
		sqlSession.delete("question.deleteResult",question_no);		
	}
	
	public String timeCheck() {
		SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm");
		Date time = new Date();		
		String time1 = format.format(time);
		return time1;
	}
	@Override
	public int question_true(int question_no) {		
		return sqlSession.selectOne("question.userResultTrue",question_no);
	}
	@Override
	public int question_false(int question_no) {		
		return sqlSession.selectOne("question.userResultFalse",question_no);
	}
	@Override
	public UploadQuestionDto question_all(int question_no) {		
		return sqlSession.selectOne("question.getTotal", question_no);
	}
	@Override
	public void insert_result(UserQuestionResultDto userQuestionResultDto) {
		sqlSession.insert("question.insert_result",userQuestionResultDto);		
	}
	@Override
	public int userPriority(int question_no, int result_no) {
		UserQuestionResultDto userQuestionResultDto = UserQuestionResultDto.builder()
				.question_no(question_no)
				.result_no(result_no)
				.build();
		return sqlSession.selectOne("question.userPriority", userQuestionResultDto);
	}
	@Override
	public UploadQuestionDto isCorrect(int question_no) {
		return sqlSession.selectOne("question.getOne", question_no);
	}
}
