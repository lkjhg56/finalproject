package com.kh.finalproject.repository;

import java.util.List;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;

public interface UploadQuestionDao {
	void upload(UploadQuestionDto uploadQuestionDto);
	int questionSequece();
	int userSequence();
	List<UploadQuestionDto> getList();
	UploadQuestionDto getOne();
	void fileUpload(UploadQuestionFileDto uploadQuestionFileDto);
	String makeDispositionString(UploadQuestionFileDto uploadQuestionFileDto) throws Exception;
	void updateQustion(UploadQuestionDto uploadQuestionDto);
	void updateFile(UploadQuestionFileDto uploadQuestionFileDto);
	UploadQuestionFileDto fileDelete(int question_no);
}
