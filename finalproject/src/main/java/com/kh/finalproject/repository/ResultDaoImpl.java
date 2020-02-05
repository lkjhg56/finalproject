package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.ResultDto;

import oracle.jdbc.proxy.annotation.GetProxy;

@Repository
public class ResultDaoImpl implements ResultDao{
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private HttpServletRequest req;
	
	@Override
	public List<ResultDto> getList(String users_id) {
		return sqlSession.selectList("resultDto.test_result", users_id);	
	}

	@Override
	public List<ResultDto> searchList(Map<String, String> ready) {
		return sqlSession.selectList("resultDto.search_result", ready);
	}


}
