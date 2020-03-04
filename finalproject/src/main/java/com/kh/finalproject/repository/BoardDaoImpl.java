package com.kh.finalproject.repository;

import java.io.File;
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
import com.kh.finalproject.entity.BoardReportDto;

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

	//게시글 내용 수정
	@Override
	public void edit(BoardDto boardDto) {
		sqlSession.update("board.edit", boardDto);
		
	}

	//게시글 삭제
	@Override
	public void delete(int board_no) {
		sqlSession.delete("board.delete", board_no);
		
		//실제로 저장된 첨부 파일들 삭제
				List<BoardFileDto> delete = sqlSession.selectList("board.getFileNO", board_no); //파일 정보 list형태로 가져오기
				
				//반복문으로 파일 삭제 실행
				for(int i = 0; i < delete.size(); i++) {
					
					//실제 파일 삭제
					String filepath = "D:/upload/kh2b/board_files/" + delete.get(i).getBoard_file_save_name();				
					File file = new File(filepath);
					file.delete();				
				}			
	}

	@Override
	public List<BoardDto> search(Map<String, String> param) {		
		return sqlSession.selectList("board.search", param);
	}
	
	@Override
	public void readCount(int board_no) {
		sqlSession.update("board.readCount", board_no);		
	}

////////////////////////댓글///////////////////////////////////
	@Override
	public List<BoardReplyDto> getReplyList(Map<String, Integer> param) {		
		return sqlSession.selectList("board.ReplyList", param);
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
	public void rereplyRegist(BoardReplyDto boardReplyDto) {
		sqlSession.insert("board.registRereply", boardReplyDto);		
	}
	
	
//////////////////////네비게이터//////////////////////////
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
	public int boardReplyCount(int board_no) {
		return sqlSession.selectOne("board.boardReplyCount", board_no);
	}

	@Override
	public BoardReplyDto getReply(int board_reply_no) {
		return sqlSession.selectOne("board.getOneReply", board_reply_no);
	}

	//게시글신고등록
	@Override
	public void reportRegist(BoardReportDto boardReportDto) {
		sqlSession.insert("board.insertReport", boardReportDto);		
	}
	//댓글신고등록
	@Override
	public void reportRegist2(BoardReportDto boardReportDto) {
		sqlSession.insert("board.insertReport2", boardReportDto);			
	}

	@Override
	public List<BoardReportDto> getReportList(Map<String, String> param) {
		return sqlSession.selectList("board.reportList", param);
	}

	//신고 게시글 개수 조회
		@Override
		public int boardReportCount() {
			return sqlSession.selectOne("board.boardReportCount");
		}

		@Override
		public int reportCategoryCount(String board_category) {
			return sqlSession.selectOne("board.ReportCategoryCount", board_category);
		}

		@Override
		public List<BoardReportDto> getReportCGList(Map<String, String> param) {
			return sqlSession.selectList("board.reportCategoryList", param);
		}

		@Override
		public List<BoardReportDto> reportSearch(Map<String, String> param) {
			return sqlSession.selectList("board.reportSearch", param);
		}

		@Override
		public int reportCountAdd(int report_board_no) {
			return sqlSession.selectOne("board.reportCountAdd", report_board_no);
		}

		@Override
		public List<BoardReportDto> getReportRPList(Map<String, String> param) {
			return sqlSession.selectList("board.reportRPList", param);
		}

		@Override
		public int reportRPCount() {
			return sqlSession.selectOne("board.replyReportCount");
		}

		@Override
		public int reportCountAdd2(int report_reply_no) {
			return sqlSession.selectOne("board.reportCountAdd2", report_reply_no);
		}

		@Override
		public List<BoardReportDto> reportRPSearch(Map<String, String> param) {
			return sqlSession.selectList("board.reportRPSearch", param);
		}

		@Override
		public List<BoardReportDto> reportCategorySearch(Map<String, String> param) {
			return sqlSession.selectList("board.reportCategorySearch", param);
		}
}
