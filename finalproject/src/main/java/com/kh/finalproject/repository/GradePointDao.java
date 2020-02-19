package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.GradePointDto;

public interface GradePointDao {
	
	//포인트 내역 조회
	List<GradePointDto> get_pointList(Map<String, String> total);
}
