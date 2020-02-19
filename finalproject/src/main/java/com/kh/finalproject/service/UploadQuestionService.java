package com.kh.finalproject.service;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UserQuestionMultiResultDto;
import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.vo.ExamResultVO;
import com.kh.finalproject.vo.UpdateQuestionVO;

public interface UploadQuestionService {
	void questionUpload(UpdateQuestionVO updateQuestionVO) throws Exception;
	void questionUpdate(UpdateQuestionVO updateQuestionVO) throws Exception;
	void questionDelete(int question_no,int user_custom_question_no);
	UserQuestionResultDto questionSolve(UpdateQuestionVO updateQuestionVO);
	ResponseEntity<ByteArrayResource> downloadImg(int question_no) throws Exception;
	List<UploadQuestionDto> multiQuestion(int wantQuestion);
	List<UserQuestionResultDto> checkMulti(ExamResultVO examResultVO);
	UserQuestionMultiResultDto insert_multi(List<UserQuestionResultDto> list, UserQuestionResultDto userQuestionResultDto);
}