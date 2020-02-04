package com.kh.finalproject.repository;

import java.util.List;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;

public interface TestDao {
	List<TestDto> getList();
	List<CategoryDto> getDetailList(int tno);
	List<TestQuestionDto> getQuestionList(String categoryname);
	List<CategoryDto> getQuestionList2(String categoryname);
	int getScore(int rno);
	TestQuestionDto getDto(String categoryname);
}
