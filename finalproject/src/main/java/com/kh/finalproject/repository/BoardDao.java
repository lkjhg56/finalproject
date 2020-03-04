package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardReplyDto;
import com.kh.finalproject.entity.BoardReportDto;

public interface BoardDao {
	//1. 등록기능
		void regist(BoardDto boardDto);
		
	//2. 게시판 전체글 조회기능
		List<BoardDto> getList(Map<String, Integer> param);
		
	//3.게시글 단일조회 기능
		BoardDto get(int board_no);

	//4.카테고리별 게시판 조회기능
		List<BoardDto> getCategoryList(Map<String, String> param);
		
	//5.게시글 번호 자동 발급	
		int getsequence();
		
	//6. 게시글 수정
		void edit(BoardDto boardDto);

	//7.게시글 삭제
		void delete(int board_no);

	//8.게시글 검색
		List<BoardDto> search(Map<String, String> param);
		
	//9.댓글 조회
		List<BoardReplyDto> getReplyList(Map<String, Integer> param);
		
	//10.댓글 삭제
		void replyDelete(int board_reply_no);
		
	//11.댓글 수정
		void replyEdit(BoardReplyDto boardReplyDto);
		
	//12.댓글 등록
		void replyRegist(BoardReplyDto boardReplyDto);
		
	//13.댓글 수 업데이트
		void replyCount(int board_reply_origin);
		
	//14.게시판 전체 글 수 구하기
		int boardCount();
		
	//15.게시판 카테고리별 글 수 구하기
		int boardCategoryCount(String board_category);
		
	//16.게시판 검색별 글 개수 구하기
		int boardSearchCount(Map<String, String> param);
		
	//17.댓글 개수 구하기
		int boardReplyCount(int board_no);
		
	//18.댓글 정보 가져오기
		BoardReplyDto getReply(int board_reply_no);
		
	//19.대댓글 등록하기
		void rereplyRegist(BoardReplyDto boardReplyDto);
		
	//20.조회수 증가
		void readCount(int board_no);
		
	//21.신고 등록하기(게시글)
		void reportRegist(BoardReportDto boardReportDto);
		
	//22.신고 등록하기2(댓글)
		void reportRegist2(BoardReportDto boardReportDto);
			
	//23.신고게시글 목록 조회
		List<BoardReportDto> getReportList(Map<String, String> param);
	
	//24.신고글 전체 개수 구하기
		int boardReportCount();
		
	//25.게시판 카테고리별 글 수 구하기
		int reportCategoryCount(String board_category);
			
	//26.게시판 검색 목록
		List<BoardReportDto> reportSearch(Map<String, String> param);
		
	//27.신고게시글 카테고리별 목록 조회
		List<BoardReportDto> getReportCGList(Map<String, String> param);
		
	//28.게시글 누적 신고횟수 구하기
		int reportCountAdd(int report_board_no);
		
	//29.신고 댓글 목록 조회
		List<BoardReportDto> getReportRPList(Map<String, String> param);
		
	//30.신고댓글 전체 개수 구하기
		int reportRPCount();
			
	//31.댓글 누적 신고횟수 구하기
		int reportCountAdd2(int report_reply_no);
		
	//31.댓글 검색 목록
		List<BoardReportDto> reportRPSearch(Map<String, String> param);
		
	//32.신고게시글 카테고리별 목록 검색
		List<BoardReportDto> reportCategorySearch(Map<String, String> param);
}
