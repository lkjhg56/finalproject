package com.kh.finalproject.repository;

import java.util.List;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UserQuestionResultDto;

public interface UploadQuestionDao {
	void upload(UploadQuestionDto uploadQuestionDto);
	int questionSequece();
	int questionResultSequece();
	int userSequence();
	List<UploadQuestionDto> getList();
	UploadQuestionDto getOne();
	void fileUpload(UploadQuestionFileDto uploadQuestionFileDto);
	String makeDispositionString(UploadQuestionFileDto uploadQuestionFileDto) throws Exception;
	
	void updateQustion(UploadQuestionDto uploadQuestionDto);
	void updateFile(UploadQuestionFileDto uploadQuestionFileDto);
	UploadQuestionFileDto getFile(int question_no);
	void fileDelete2(int question_no, int user_custom_question_no);
	String timeCheck();
	int question_true();
	int question_false();
	UploadQuestionDto question_all(int question_no);
	void insert_result(UserQuestionResultDto userQuestionResultDto);
	int userPriority(int question_no, int result_no);
	
}
