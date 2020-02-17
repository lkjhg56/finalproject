package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UploadQuestionDto {
	//문제 업로드
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
	//user_costom_question
	private int user_no;//유저 넘버
	private String category_name;
/*************************************************/
	private String id;
/*************************************************/
	private int correct_ratio;
}
