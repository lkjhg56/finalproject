package com.kh.finalproject.repository;

import java.util.List;

import com.kh.finalproject.entity.UploadQuestionDto;

public interface UploadQuestionDao {
	void upload(UploadQuestionDto uploadQuestionDto);
	void update(UploadQuestionDto uploadQuestionDto, String admin);
	UploadQuestionDto getOne();
	List<UploadQuestionDto> getList();
}
