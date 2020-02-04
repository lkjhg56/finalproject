package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ResultDto {

	private int rno;
	private String user_id;
	private int test_no;
	private int sumscore;
	private int detail_score;
	private String test_date;
	private String used_time;
	private int  cus_ques_no;
	private String cs_no;
}
