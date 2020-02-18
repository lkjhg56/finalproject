package com.kh.finalproject.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;

@Service
public class GradePointServiceImpl implements GradePointService{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private HttpServletRequest req;
	

	//회원가입 포인트 부여
	@Override
	public void giveJoinPoint(GradePointDto pointDto, UsersDto usersDto) {
		
		String id = req.getParameter("id");
		usersDto.setId(id);
		 
		int users_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(users_no);
		
		sqlSession.insert("grade_point.giveJoinPoint", pointDto);
		sqlSession.update("users.change_5point", usersDto);
	}

	//출석체크 포인트 부여
	@Override
	public void giveCheckPoint(GradePointDto pointDto, UsersDto usersDto) {
		
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int users_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(users_no);
		
		sqlSession.insert("grade_point.giveCheckPoint", pointDto);
		sqlSession.update("users.change_1point", usersDto);
	}

	//문제업로드 포인트 부여
	@Override
	public void giveQuestionUploadPoint(GradePointDto pointDto,UsersDto usersDto) {		
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		
		int users_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(users_no);
		
		sqlSession.insert("grade_point.giveQuestionUploadPoint", pointDto);
		sqlSession.update("users.change_5point", usersDto);
	}
	
	//문제 풀기 포인트 부여
	@Override
	public void giveQuestionSolvePoint(GradePointDto pointDto, UsersDto usersDto) {
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int users_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(users_no);
		
		sqlSession.insert("grade_point.giveQuestionSolvePoint", pointDto);//users_no만 필요함.
		sqlSession.update("users.change_3point", usersDto);
	}

	//답변 채택 포인트 부여
	@Override
	public void giveAnswerPoint(GradePointDto pointDto, UsersDto usersDto) {
		
		String id = req.getParameter("board_reply_writer");
		usersDto.setId(id);
		 
		int users_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(users_no);
		
		sqlSession.insert("grade_point.giveAnswerPoint", pointDto);
		sqlSession.update("users.change_3point", usersDto);
		
	}
	
	//해설 작성 포인트 부여
}
