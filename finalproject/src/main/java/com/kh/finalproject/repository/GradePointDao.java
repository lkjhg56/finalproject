package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;

public interface GradePointDao {
	
	//포인트 내역 조회
	List<GradePointDto> get_pointList(Map<String, String> total);
	
	//회원가입 포인트 부여
	void giveJoinPoint(int user_no);
	
	//출석체크 포인트 부여
	void giveCheckPoint(int user_no);
	
	//문제 업로드 포인트 부여
	void giveQuestionUploadPoint(int user_no);
	
	//문제 풀기 포인트 부여
	void giveQuestionSolvePoint(int user_no);
	
	//문제 삭제 포인트 차감
	void deleteQuestionPoint(int user_no);
	
	//답변 채택 포인트 부여
	void giveAnswerPoint(int user_no);
}
