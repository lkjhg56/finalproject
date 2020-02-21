package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.GradePointDto;

public interface GradePointDao {
	
	//포인트 내역 조회
	List<GradePointDto> get_pointList(Map<String, String> total);
	//문제 업로드 포인트 부여
	void giveQuestionUploadPoint(int user_no);
	//문제 풀기 포인트 부여
	void giveQuestionSolvePoint(int user_no);
	void deleteQuestionPoint(int user_no);
}
