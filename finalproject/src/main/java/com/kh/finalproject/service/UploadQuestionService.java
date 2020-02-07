package com.kh.finalproject.service;

import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.vo.UpdateQuestionVO;

public interface UploadQuestionService {
	void questionUpload(UpdateQuestionVO updateQuestionVO) throws Exception;
	void questionUpdate(UpdateQuestionVO updateQuestionVO) throws Exception;
	void questionDelete(int question_no, int user_custom_question_no);
	UserQuestionResultDto questionSolve(UpdateQuestionVO updateQuestionVO);
}
