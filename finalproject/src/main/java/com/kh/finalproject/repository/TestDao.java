package com.kh.finalproject.repository;

import java.util.List;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.TestDto;

public interface TestDao {
	List<TestDto> getList();
	List<CategoryDto> getDetailList(int tno);
	List<CategoryDto> getQuestionList(String categoryname);
	List<CategoryDto> getQuestionList2(String categoryname);
}
