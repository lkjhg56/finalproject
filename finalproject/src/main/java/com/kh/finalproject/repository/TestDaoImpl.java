package com.kh.finalproject.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDaoImpl implements TestDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<TestDto> getList() {
		return sqlSession.selectList("test.list");
	}

	@Override
	public List<CategoryDto> getDetailList(int tno) {
		return sqlSession.selectList("test.categoryList", tno);
	}

	@Override
	public List<TestQuestionDto> getQuestionList(String categoryname) {
		return sqlSession.selectList("test.questionList", categoryname);
	}

	@Override
	public List<CategoryDto> getQuestionList2(String categoryname) {
		
		return sqlSession.selectList("test.questionList2", categoryname);
	}

}
