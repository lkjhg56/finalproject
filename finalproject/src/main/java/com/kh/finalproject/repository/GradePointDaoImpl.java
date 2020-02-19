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

}
