package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class DistractorDto {
	private int idsno;
	private int question_no;
	private int disno_num;
	private String disno_dist;
}
