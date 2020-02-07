package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardReplyDto {

	private int board_reply_no;
	private String board_reply_content;
	private String board_reply_wdate;
	private String board_reply_writer;
	private int board_reply_origin;

}