package com.kh.finalproject.repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardFileDto;
import com.kh.finalproject.entity.BoardReplyDto;

@Repository

public class BoardDaoImpl implements BoardDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void regist(BoardDto boardDto) {
		sqlSession.insert("board.regist", boardDto);		
	}

	@Override
	public List<BoardDto> getList(Map<String, Integer> param) {		
		return sqlSession.selectList("board.list", param);
	}

	@Override
	public BoardDto get(int board_no) {		
		return sqlSession.selectOne("board.get", board_no); //board_no를 주고 단일조회한다.
	}

	@Override
	public List<BoardDto> getCategoryList(Map<String, String> param) {
		//board_category를 주고 선택한 카테고리에 해당하는 게시판목록 조회한다.
		return sqlSession.selectList("board.getCategoryList", param); 
	}

	@Override
	public int getsequence() {
		return sqlSession.selectOne("board.getSequence");
	}

	@Override
	public void edit(BoardDto boardDto) {
		sqlSession.update("board.edit", boardDto);
		
	}

	@Override
	public void delete(int board_no) {
		sqlSession.delete("board.delete", board_no);
		
	}

	@Override
	public List<BoardDto> search(Map<String, String> param) {		
		return sqlSession.selectList("board.search", param);
	}

	@Override
	public List<BoardReplyDto> getReplyList(int board_reply_origin) {		
		return sqlSession.selectList("board.ReplyList", board_reply_origin);
	}

	@Override
	public void replyDelete(int board_reply_no) {
		sqlSession.delete("board.deleteReply", board_reply_no);
		
	}

	@Override
	public void replyEdit(BoardReplyDto boardReplyDto) {
		sqlSession.update("board.editReply", boardReplyDto);
		
	}

	@Override
	public void replyRegist(BoardReplyDto boardReplyDto) {
		sqlSession.insert("board.registReply", boardReplyDto);
		
	}

	@Override
	public void replyCount(int board_reply_origin) {
		sqlSession.update("board.calculate", board_reply_origin);
		
	}

	@Override
	public int boardCount() {		
		return sqlSession.selectOne("board.boardCount");

	}

	@Override
	public int boardCategoryCount(String board_category) {
		return sqlSession.selectOne("board.boardCategoryCount", board_category);
	}

	@Override
	public int boardSearchCount(Map<String, String> param) {
		return sqlSession.selectOne("board.boardSearchCount", param);

	}

	@Override
	public BoardFileDto getFile(int board_no) {
		return sqlSession.selectOne("board.getFile2",board_no);		
	}

	@Override
	public String makeDispositionString(BoardFileDto boardfileDto) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attachment;");
		buffer.append("filename=");
		buffer.append("\"");
		buffer.append(URLEncoder.encode(boardfileDto.getBoard_file_upload_name(), "UTF-8"));
		buffer.append("\"");
		return buffer.toString();
	}

	@Override
	public List<BoardFileDto> getFileList(int board_no) {
		return sqlSession.selectList("board.getFile2",board_no);	
	}

	




}
