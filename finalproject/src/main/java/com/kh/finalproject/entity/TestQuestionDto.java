package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TestQuestionDto {

	private int no;
	
	private int test_no;
	
	private String csname;
	private String categoryname;//csname대신
	private String category_no;
	private String question;
	private int ispremium;
	private int answer;
	private int correct, incorrect;
	private String dis1, dis2, dis3, dis4, dis5;
	private int rate;
	private int lim_hour, lim_min;
	private int rno, end;

	
}
