package com.kh.finalproject.service;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;

public interface GradePointService {
	
	//회원가입 포인트 부여
	void giveJoinPoint(GradePointDto pointDto,UsersDto usersDto);

	//출석체크 포인트 부여
	void giveCheckPoint(GradePointDto pointDto,UsersDto usersDto);
	
	//문제업로드 포인트 부여
	void giveQuestionUploadPoint(GradePointDto pointDto,UsersDto usersDto);
	
	//문제 풀기 포인트 부여
	void giveQuestionSolvePoint(GradePointDto pointDto,UsersDto usersDto);
	
	//답변 채택 포인트 부여
	void giveAnswerPoint(GradePointDto pointDto,UsersDto usersDto);

	//해설 작성 포인트 부여

}
