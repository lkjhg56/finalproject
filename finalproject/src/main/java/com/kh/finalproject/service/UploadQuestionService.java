package com.kh.finalproject.service;

import com.kh.finalproject.entity.UploadQuestionDto;

public interface UploadQuestionService {
	void upload(UploadQuestionDto uploadQuestionDto);
	void update(UploadQuestionDto uploadQuestionDto, String admin);
}
