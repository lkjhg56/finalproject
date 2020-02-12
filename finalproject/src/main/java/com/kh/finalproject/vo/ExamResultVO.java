package com.kh.finalproject.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data@AllArgsConstructor@NoArgsConstructor@Builder
public class ExamResultVO {
	private int no;
	private int answer;
	private List<ExamResultVO> question;
/*************************************************/
	private String id;//tried_user에 넣는다.
/*************************************************/
}
