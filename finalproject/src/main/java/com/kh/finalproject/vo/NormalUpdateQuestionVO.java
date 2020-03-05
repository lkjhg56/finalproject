package com.kh.finalproject.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class NormalUpdateQuestionVO {
	private int no;
	private String csname;
	private String category_no; 
	private String question; 
	private int ispremium; 
	private int answer;
	private int correct; 
	private int incorrect; 
	private String dis1;
	private String dis2;
	private String dis3;
	private String dis4;
	private String dis5;
	private int rate;
/*************************************************/
	
	
	//category_no 일단 제외

	private int LIM_HOUR;
	private int LIM_MIN;
/*************************************************/
	private int tno;
	private String test_category;
	
	
	
	/*************************************************/
	private int test_question_file_no;
	private String file_upload_name;
	private String file_save_name;
	private String file_type;
	private long file_size;
	private int test_question_no;
	private List<MultipartFile> file;
/*************************************************/
	private String id;
/*************************************************/
	
	

	private String solution;
	private int question_no;
	private int user_no;
	//출력용
	private int hour;
	private int min;
	private int sec;
	private int milisec;
	private int user_conclusion;
	
	private String question_premium;
	
	
	private int rqno, result_no, test_no, cus_ques, iscorrect;
	
}
