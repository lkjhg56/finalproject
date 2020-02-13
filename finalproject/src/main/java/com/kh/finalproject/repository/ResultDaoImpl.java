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
	
	//시험 내역 조회
	@Override
	public List<ResultDto> getList(Map<String, String> total) {
		return sqlSession.selectList("resultDto.test_result",total);	
	}

	//시험 내역 검색 조회
	@Override
	public List<ResultDto> searchList(Map<String, String> total) {
		return sqlSession.selectList("resultDto.search_result", total);
	}
	
	//시험 내역 조회 개수
//	@Override
//	public int getCount(String users_id) {
//		int count = sqlSession.selectOne("resultDto.getCount", users_id);
//		return count;
//	}
	
	//시험 내역 검색 개수
	@Override
	public int search_getCount(Map<String, String> ready) {
		int count = sqlSession.selectOne("resultDto.search_getCount", ready);
		return count;
	}


}
