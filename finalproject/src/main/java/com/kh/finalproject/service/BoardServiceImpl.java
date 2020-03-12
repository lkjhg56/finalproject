package com.kh.finalproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.repository.BoardDao;

@Service
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardDao boardDao;

	@Override
	public void readCount(int board_no) {
		boardDao.readCount(board_no);	//조회수 증가
	}

	@Override
	public List<BoardDto> search(Map<String, String> param) {
		List<BoardDto> search = boardDao.search(param);
		return search;
	}
	

}
