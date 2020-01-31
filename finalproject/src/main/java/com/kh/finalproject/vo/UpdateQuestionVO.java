package com.kh.finalproject.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UpdateQuestionVO {
	//문제 업로드
	private int question_no;//하기 2 테이블에 외래키로 묶여 있음.
	private int user_custom_question_no; 
	private String question_title; 
	private String question_content; 
	private String question_answer; 
	private String question_solution;
	private int question_premium; 
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
/*************************************************/
	//user_costom_question
	private int user_no;//유저 넘버
	private String category_name;
/*************************************************/
	private int question_file_no;//번호(기본키)
	private String file_upload_name;//올린이름
	private String file_save_name;//실제이름
	private String file_type;//파일유형
	private long file_size;//파일크기
	private List<MultipartFile> file;
}
