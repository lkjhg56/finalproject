package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardReportDto {
	private int report_no, report_board_no, report_reply_no, report_count;
	private String report_board_writer, reporter, report_content, report_reason, report_date;
	
	private int board_no, board_replycount, rn, board_readcount;
	private String 	board_category, board_title, board_wdate, board_content, board_writer;
	
	private int board_reply_no;
	private String board_reply_content;
	private String board_reply_wdate;
	private String board_reply_writer;
	private int board_reply_origin;
	private int groupno, superno, depth;
}
