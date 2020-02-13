package com.kh.finalproject.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardDto {
	private int board_no, board_replycount, rn;
	private String 	board_category, board_title, board_wdate, board_content, board_writer;
}
