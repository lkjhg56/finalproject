package com.kh.finalproject.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ExamResultVO {
	private int question_no;
	private String question_title;
	private List<Integer> question_answer;
/*************************************************/
	private String id;
/*************************************************/
	//시간
	private int hour;
	private int min;
	private int sec;
	private int milisec;
	private int countData;
}
