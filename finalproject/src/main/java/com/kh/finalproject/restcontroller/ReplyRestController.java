package com.kh.finalproject.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardReplyDto;
import com.kh.finalproject.repository.BoardDao;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board2")
@Slf4j
public class ReplyRestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	//댓글 목록 조회
	@GetMapping("/replyList")
	public List<BoardReplyDto> replyList(){
		log.info("check is go");
		Map<String, Integer> param = new HashMap<>();
		param.put("board_reply_origin", 33);
		param.put("board_reply_no", 4);
		
		 List<BoardReplyDto> list = sqlSession.selectList("board.ReplyList", param);
		 log.info("is list={}", list.size());
		return null;
	}
	
	//댓글 등록
	@PostMapping("/insert")
	public BoardReplyDto insert(@RequestParam String board_reply_content, String board_reply_writer,
													@RequestParam int board_reply_origin,
													HttpSession session) {
		int no = sqlSession.selectOne("board.getReplySequence");

		BoardReplyDto boardReplyDto = BoardReplyDto.builder()
								.board_reply_no(no)
								.board_reply_content(board_reply_content)
								.board_reply_writer(board_reply_writer)
								.board_reply_origin(board_reply_origin)
								.build();
		sqlSession.insert("board.registReply", boardReplyDto);
		BoardReplyDto result = sqlSession.selectOne("board.getOneReply", no);

		return result;		
	}
	
	//댓글 삭제
	@DeleteMapping("/delete")
	public String delete(@RequestParam int board_reply_no) {
		
		sqlSession.delete("board.deleteReply", board_reply_no);
		return null;
	}
	
	
	//댓글 수정
		@PostMapping("/edit2")
		public String replyEdit(@ModelAttribute BoardReplyDto boardReplyDto,
												@ModelAttribute BoardDto boardDto,
												HttpSession session) {
		System.out.println(boardReplyDto.getBoard_reply_no());
//		boardDao.replyEdit(boardReplyDto);			
		sqlSession.update("board.editReply", boardReplyDto);
		
		return null;
	}

}
