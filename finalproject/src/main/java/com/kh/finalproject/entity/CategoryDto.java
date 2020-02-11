package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class CategoryDto {

	private int category_no;
	private String csname;
	private int test_no;
	private int lim_hour, lim_min;

}
