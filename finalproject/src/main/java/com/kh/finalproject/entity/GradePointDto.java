package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder@NoArgsConstructor@AllArgsConstructor
public class GradePointDto {

	private int point_no;
	private int users_no;
	private int get_point;
	private String get_date,point_type;
	private int rn;
}