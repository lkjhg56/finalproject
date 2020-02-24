package com.kh.finalproject.service;

import org.springframework.web.bind.annotation.ModelAttribute;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;

public interface GradePointService {
	
	//회원가입 포인트 부여
	void giveJoinPoint(UsersDto usersDto, GradePointDto pointDto);

	//출석체크 포인트 부여
	void giveCheckPoint(UsersDto usersDto, GradePointDto pointDto);
	
	//문제업로드 포인트 부여
	void giveQuestionUploadPoint(UsersDto usersDto, GradePointDto pointDto);
	
	//문제풀기 포인트 부여
	void giveQuestionSolvePoint(UsersDto usersDto, GradePointDto pointDto);
	
	//문제삭제 포인트 차감
	void deleteQuestionPoint(UsersDto usersDto, GradePointDto pointDto);
	
	//답변 채택 포인트 부여
	void giveAnswerPoint(UsersDto usersDto, GradePointDto pointDto);



}
