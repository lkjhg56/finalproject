package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardReportDto {
	private int report_no, report_board_no, report_reply_no, report_count;
	private String report_board_writer, reporter, report_content, report_reason, report_date;
}
