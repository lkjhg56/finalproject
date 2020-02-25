package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;

public class GradePointDaoImpl implements GradePointDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<GradePointDto> get_pointList(Map<String, String> total) {
		return sqlSession.selectList("grade_point.my_grade_point", total);
	}
	//각 서비스 기능에서 user_no를 조회하여 해당 DAO에 값을 넘겨줘야함.
	//회원가입 포인트 부여
	@Override
	public void giveJoinPoint(int user_no) {
		sqlSession.insert("grade_point.giveJoinPoint", user_no);
		sqlSession.update("users.change_5point", user_no);
	}
	//출석체크 포인트 부여
	@Override
	public void giveCheckPoint(int user_no) {
		sqlSession.insert("grade_point.giveCheckPoint", user_no);
		sqlSession.update("users.change_1point", user_no);
	}
	//문제업로드 포인트 부여
	@Override
	public void giveQuestionUploadPoint(int user_no) {
		sqlSession.insert("grade_point.giveQuestionUploadPoint", user_no);		
		sqlSession.update("users.change_5point", user_no);
	}
	//문제풀기 포인트 부여
	@Override
	public void giveQuestionSolvePoint(int user_no) {
		sqlSession.insert("grade_point.giveQuestionSolvePoint", user_no);	
		sqlSession.update("users.change_3point", user_no);
	}
	//문제삭제 포인트 차감
	@Override
	public void deleteQuestionPoint(int user_no) {
		sqlSession.insert("grade_point.deleteMinus",user_no);
		sqlSession.update("users.decreasePoint",user_no);
	}
	//답변채택 포인트 부여
	@Override
	public void giveAnswerPoint(int user_no) {
		sqlSession.insert("grade_point.giveAnswerPoint", user_no);
		sqlSession.update("users.change_3point", user_no);
	}

}
