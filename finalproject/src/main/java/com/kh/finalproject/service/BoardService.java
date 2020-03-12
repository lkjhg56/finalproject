package com.kh.finalproject.service;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.BoardDto;

public interface BoardService {
	//게시글 조회수 업데이트
	void readCount(int board_no);
	
	//게시글 검색
	List<BoardDto> search(Map<String, String> param);
}
