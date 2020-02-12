package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UserQuestionResultDto {
	/*****************************************************/	
	//출력용
	private int hour;//
	private int min;//
	private int sec;//
	private int milisec;//
	private int question_answer;//
	private int question_true;//
	private int question_false;//
	private int user_priority;//
	/*****************************************************/
	//DB저장용
	private int result_no;//0,1 맞았는지 틀렸는지
	private String result_time;//11자, 수험 시간
	private String tried_user;// 수험자
	private String solveDate;//16자, 수험 날짜
	private int result;//0,1 맞았는지 틀렸는지
	private int user_conclusion;//고른 답
	private int question_no;//16자, 외래키
}
