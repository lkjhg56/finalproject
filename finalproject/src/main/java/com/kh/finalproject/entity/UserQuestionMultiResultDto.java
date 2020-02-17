package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UserQuestionMultiResultDto {	
	private int multi_result_no;//결과 번호
	private int user_no;//외래키
	private int total_question;//총 문제 제출수
	private int correct_count;//맞은 개수
	private int incorrect_count;//틀린 개수
	private int sum_score;//점수 합계
	private String result_time;//걸린 시간
	private String solve_date;//수험일자
}
