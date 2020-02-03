package com.kh.finalproject.repository;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.BoardDto;

@Repository
public class BoardDaoImpl implements BoardDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void regist(BoardDto boardDto) {
		sqlSession.insert("board.regist", boardDto);
		
	}

	@Override
	public List<BoardDto> getList() {		
		return sqlSession.selectList("board.list");
	}

	@Override
	public BoardDto get(int board_no) {		
		return sqlSession.selectOne("board.get", board_no); //board_no를 주고 단일조회한다.
	}

	@Override
	public List<BoardDto> getCategoryList(String board_category) {
		//board_category를 주고 선택한 카테고리에 해당하는 게시판목록 조회한다.
		return sqlSession.selectList("board.getCategoryList", board_category); 
	}

}
