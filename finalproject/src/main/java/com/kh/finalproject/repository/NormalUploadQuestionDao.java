package com.kh.finalproject.repository;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.SolutionDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;

import com.kh.finalproject.entity.UploadTestQuestionFileDto;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;

public interface NormalUploadQuestionDao {

	
	int testquestionSequece();
	int categorySequence();
	int testSequece();
	int categoryExist(NormalUpdateQuestionVO normalUpdateQuestionVO);
	
	void upload(TestQuestionDto testQuestionDto,CategoryDto categoryDto,TestDto testDto,SolutionDto solutionDto);
	void fileUpload(UploadTestQuestionFileDto uploadTestQuestionFileDto);
	void upload2(TestQuestionDto testQuestionDto,SolutionDto solutionDto);
}
