package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor@ AllArgsConstructor
public class TestDto {
	private int tno;
	private String test_category;
	private int test_session;

}
