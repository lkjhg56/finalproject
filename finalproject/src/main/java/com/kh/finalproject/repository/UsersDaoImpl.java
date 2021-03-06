package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.UsersDto;

public class UsersDaoImpl implements UsersDao{

	@Autowired
	private SqlSession sqlSession;
	
//	@Override
//	public int getsequence() {
//		return 0;
//	}
//	@Override
//	public void regist(UsersDto usersDto) {
//	}
	
	@Override
	public List<UsersDto> getRank(Map<String, Integer> total) {
		return sqlSession.selectList("users.grade_point_rank", total);
	}
	//회원 정보를 읽어옴.
	@Override
	public UsersDto getInfo(String id) {
		return sqlSession.selectOne("users.info", id);
	}
	@Override
	public List<UsersDto> getUserList(Map<String, Integer> total) {
		return sqlSession.selectList("users.user_list", total);
	}
	@Override
	public int getUserNo(String id) {
		return sqlSession.selectOne("users.get_users_no",id);
	}
	

}
