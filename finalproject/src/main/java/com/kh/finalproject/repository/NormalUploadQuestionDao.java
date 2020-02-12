package com.kh.finalproject.repository;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.SolutionDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;

import com.kh.finalproject.entity.UploadTestQuestionFileDto;

public interface NormalUploadQuestionDao {

	
	int testquestionSequece();
	int categorySequence();
	int testSequece();
	
	void upload(TestQuestionDto testQuestionDto,CategoryDto categoryDto,TestDto testDto,SolutionDto solutionDto);
	void fileUpload(UploadTestQuestionFileDto uploadTestQuestionFileDto);
}
