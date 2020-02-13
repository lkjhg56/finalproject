package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder@NoArgsConstructor@AllArgsConstructor
public class UsersDto {
	
	private int user_no;
	private String name;
	private String id;
	private String pw;
	private String email;
	private String grade;
	private int point;
	private int grade_point;
	private String join_date;
	private String phone;
	private String postcode;
	private String address;
	private String detailAddress;
	private String extraAddress;
	private int cash;
	private String is_premium;
	private int rank;
	
}
