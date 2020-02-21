package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.GradePointDto;

public class GradePointDaoImpl implements GradePointDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<GradePointDto> get_pointList(Map<String, String> total) {
		return sqlSession.selectList("grade_point.my_grade_point", total);
	}
	//각 서비스 기능에서 user_no를 조회하여 해당 DAO에 값을 넘겨줘야함.
	//문제업로드 포인트 부여
	@Override
	public void giveQuestionUploadPoint(int user_no) {
		//users 테이블
		sqlSession.update("users.change_5point", user_no);
		//grade_point 테이블
		sqlSession.insert("grade_point.giveQuestionUploadPoint", user_no);		
	}
	@Override
	public void giveQuestionSolvePoint(int user_no) {
		//users 테이블
		sqlSession.update("users.change_3point", user_no);
		//grade_point 테이블
		sqlSession.insert("grade_point.giveQuestionSolvePoint", user_no);	
	}
	@Override
	public void deleteQuestionPoint(int user_no) {
		//users 테이블
		sqlSession.update("users.decreasePoint",user_no);
		//grade_point 테이블
		sqlSession.insert("grade_point.deleteMinus",user_no);
	}

}
