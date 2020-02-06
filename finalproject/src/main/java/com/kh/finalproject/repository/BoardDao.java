package com.kh.finalproject.repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardFileDto;

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
		
		
}
