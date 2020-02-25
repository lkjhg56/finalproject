package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.RcorrectDto;
import com.kh.finalproject.entity.SolutionDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
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
	void upload3(CategoryDto categoryDto,TestQuestionDto testQuestionDto,SolutionDto solutionDto);
	UploadTestQuestionFileDto getFile(int no);
	
	String makeDispositionString(UploadTestQuestionFileDto uploadTestQuestionFileDto) throws Exception;
	
	void updateQustion(NormalUpdateQuestionVO normalUpdateQuestionVO);
	void updateFile(UploadTestQuestionFileDto uploadTestQuestionFileDto);
	
	void fileDelete2(int no,String csname);
	
	void onlyfileDelete(int no);
	
	int rqno(RcorrectDto rcorrectDto);
	
	int questionCount();
	
	List<TestQuestionDto> mapList(Map<String, Integer> param);
	
	List<TestQuestionDto> mapList2(Map<String, String> param2);
	
	
	int testExist(NormalUpdateQuestionVO normalUpdateQuestionVO);
}
