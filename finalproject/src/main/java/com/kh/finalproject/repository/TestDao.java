package com.kh.finalproject.repository;

import java.util.List;

import javax.servlet.http.HttpSession;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;


public interface TestDao {
	List<TestDto> getList();
	List<CategoryDto> getDetailList(int tno);
	List<TestQuestionDto> getQuestionList(String categoryname);
	List<CategoryDto> getQuestionList2(String categoryname);

	int getScore(int rno);
	
	TestQuestionDto getDto(String categoryname,int no,HttpSession session);

	int getScore(int rno, String category_no, String csname);
	
	int getDtocount(String csname,String category_no);
	List<String> getFrequency();

	int getisCorrect(int rno);
	
	
	int getCount( String category_no, String csname);
}
