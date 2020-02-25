package com.kh.finalproject.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.GradePointDao;

@Service
public class GradePointServiceImpl implements GradePointService{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private GradePointDao gradePointDao;
	
	@Autowired
	private UsersDto usersDto;
	
	@Autowired
	private GradePointDto pointDto;
	
	//회원가입 포인트 부여
	@Override
	public void giveJoinPoint(UsersDto usersDto, GradePointDto pointDto) {
		
		String id = req.getParameter("id");
		usersDto.setId(id);
		 
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(user_no);
		
		gradePointDao.giveJoinPoint(user_no);
	}

	//출석체크 포인트 부여
	@Override
	public void giveCheckPoint(UsersDto usersDto, GradePointDto pointDto) {
		
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(user_no);
		
		gradePointDao.giveCheckPoint(user_no);
	}
	
	//문제업로드 포인트 부여
	@Override
	public void giveQuestionUploadPoint(UsersDto usersDto, GradePointDto pointDto) {
		
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(user_no);
		
		gradePointDao.giveQuestionUploadPoint(user_no);
	}
	
	//문제풀기 포인트 부여
	@Override
	public void giveQuestionSolvePoint(UsersDto usersDto, GradePointDto pointDto) {
		
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(user_no);
		
		gradePointDao.giveQuestionSolvePoint(user_no);
	}
	
	//문제삭제 포인트 차감
	@Override
	public void deleteQuestionPoint(UsersDto usersDto, GradePointDto pointDto) {
		
		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(user_no);
		
		gradePointDao.deleteQuestionPoint(user_no);
		
	}
	
	//답변 채택 포인트 부여
	@Override
	public void giveAnswerPoint(UsersDto usersDto, GradePointDto pointDto) {
		
		String id = req.getParameter("board_reply_writer");
		usersDto.setId(id);
		 
		int user_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(user_no);
		
		gradePointDao.giveAnswerPoint(user_no);
	}
}
