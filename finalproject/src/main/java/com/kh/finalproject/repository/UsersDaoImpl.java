package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.UsersDto;

public class UsersDaoImpl implements UsersDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<UsersDto> getRank(Map<String, Integer> total) {
		return sqlSession.selectList("users.grade_point_rank", total);
	}
	//회원 정보를 읽어옴.
	@Override
	public UsersDto getInfo(String id) {
		return sqlSession.selectOne("users.info", id);
	}
	
	

}
