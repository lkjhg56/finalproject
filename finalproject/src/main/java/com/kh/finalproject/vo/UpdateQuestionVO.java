package com.kh.finalproject.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UpdateQuestionVO {
	private int question_no;
	private int user_custom_question_no; 
	private String question_title; 
	private String question_content; 
	private int question_answer; 
	private String question_solution;
	private int question_premium; 
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
/*************************************************/
	private int user_no;
	private String category_name;
/*************************************************/
	private int question_file_no;
	private String file_upload_name;
	private String file_save_name;
	private String file_type;
	private long file_size;
	private List<MultipartFile> file;
/*************************************************/
	private String id;
}
