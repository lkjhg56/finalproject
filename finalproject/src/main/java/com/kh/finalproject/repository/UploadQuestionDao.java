package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UserQuestionMultiResultDto;
import com.kh.finalproject.entity.UserQuestionResultDto;

public interface UploadQuestionDao {
	void upload(UploadQuestionDto uploadQuestionDto);
	int questionSequece();
	int questionResultSequece();
	int userSequence();
	List<UploadQuestionDto> getList();
	void fileUpload(UploadQuestionFileDto uploadQuestionFileDto);
	String makeDispositionString(UploadQuestionFileDto uploadQuestionFileDto) throws Exception;
	int getUserNo(String id);
	void updateQustion(UploadQuestionDto uploadQuestionDto);
	void updateFile(UploadQuestionFileDto uploadQuestionFileDto);
	UploadQuestionFileDto getFile(int question_no);
	List<UploadQuestionFileDto> getFile2(int question_no);
	void fileDelete2(int question_no,int user_custom_question_no);
	String timeCheck();
	int question_true(int question_no);
	int question_false(int question_no);
	UploadQuestionDto question_all(int question_no);
	List<UploadQuestionDto> question_user_all();
	void insert_result(UserQuestionResultDto userQuestionResultDto);
	UserQuestionResultDto userPriority(int question_no, int result_no);
	UploadQuestionDto isCorrect(int question_no);
	void givePointforSolving(int user_no);
	void insert_multi_result(UserQuestionMultiResultDto userQuestionMultiResultDto);
	void correct_ratio(int question_no);
	void update_read_count(int question_no);
	UploadQuestionFileDto getFile3(int question_file_no);
	List<UploadQuestionDto> getListWithImage();
	void deleteFile(int question_file_no);
	List<UploadQuestionDto> getListWithImageByNumber(int question_no);
	int questionCount();
	List<UploadQuestionDto> mapList(Map<String, Integer> param);
	List<UploadQuestionDto> idList(String id);
	int getUserCustomNo(int user_custom_question_no);

}
