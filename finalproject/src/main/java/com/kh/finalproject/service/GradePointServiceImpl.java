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
	
	@Override
	public void givePoint(GradePointDto pointDto,UsersDto usersDto) {
		

		String id = (String) req.getSession().getAttribute("id");
		usersDto.setId(id);
		 
		int users_no = sqlSession.selectOne("users.get_users_no", id);
		pointDto.setUsers_no(users_no);
		
		sqlSession.insert("grade_point.givePoint", pointDto);
		sqlSession.update("users.change_point", usersDto);
	}
}
