package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardReplyDto;

public interface BoardDao {
	//1. 등록기능
		void regist(BoardDto boardDto);
		
	//2. 게시판 전체글 조회기능
		List<BoardDto> getList();
		
	//3.게시글 단일조회 기능
		BoardDto get(int board_no);

	//4.카테고리별 게시판 조회기능
		List<BoardDto> getCategoryList(String board_category);
		
	//5.게시글 번호 자동 발급	
		int getsequence();
		
	//6. 게시글 수정
		void edit(BoardDto boardDto);

	//7.게시글 삭제
		void delete(int board_no);

	//8.게시글 검색
		List<BoardDto> search(Map<String, String> param);
		
	//9.댓글 조회
		List<BoardReplyDto> getReplyList(int board_reply_origin);
		
	//10.댓글 삭제
		void replyDelete(int board_reply_no);
		
	//11.댓글 수정
		void replyEdit(BoardReplyDto boardReplyDto);
		
	//12.댓글 등록
		void replyRegist(BoardReplyDto boardReplyDto);
		
	//13.댓글 조회수 업데이트
		void replyCount(int board_reply_origin);
		
		
}
