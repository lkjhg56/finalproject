package com.kh.finalproject.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;

import com.kh.finalproject.vo.NormalUpdateQuestionVO;
import com.kh.finalproject.vo.UpdateQuestionVO;

public interface NormalUploadQuestionService {
	void normalquestionUpload(NormalUpdateQuestionVO normalUpdateQuestionVO) throws Exception;
	void normalquestionUpdate(NormalUpdateQuestionVO normalUpdateQuestionVO) throws Exception;
	ResponseEntity<ByteArrayResource> downloadImg(int no) throws Exception;
	void questionDelete(int no);
}
