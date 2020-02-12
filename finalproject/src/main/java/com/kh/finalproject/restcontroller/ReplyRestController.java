package com.kh.finalproject.restcontroller;

import java.util.List;

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
	
	@Autowired
	private BoardDao boardDao;
	

	
	//댓글 등록
		@PostMapping("/insert")
		public String insert(@RequestParam String board_reply_content, String board_reply_writer,
														@RequestParam int board_reply_origin,
														HttpSession session) {
			String writer = (String) session.getAttribute("id");
			
			if(writer != null) {
				int no = sqlSession.selectOne("board.getReplySequence");

				BoardReplyDto boardReplyDto = BoardReplyDto.builder()
										.board_reply_no(no)
										.board_reply_content(board_reply_content)
										.board_reply_writer(board_reply_writer)
										.board_reply_origin(board_reply_origin)
										.build();
				sqlSession.insert("board.registReply", boardReplyDto);
				BoardReplyDto result = sqlSession.selectOne("board.getOneReply", no);
				
				boardDao.replyCount(board_reply_origin);
				return "success";		
			}			
			else {
				return "fail";
			}

		}
		
	
	//댓글 삭제
	@PostMapping("/delete")
	public String delete(@RequestParam int board_reply_no,
									@RequestParam int board_reply_origin) {
		
		sqlSession.delete("board.deleteReply", board_reply_no);
		boardDao.replyCount(board_reply_origin);
		
		return "success";
	}
	
	
	//댓글 수정
	@PostMapping("/edit2")
	public String replyEdit(@ModelAttribute BoardReplyDto boardReplyDto,
											@ModelAttribute BoardDto boardDto,
											HttpSession session) {
		System.out.println(boardReplyDto.getBoard_reply_no());
		boardDao.replyEdit(boardReplyDto);			
		
		return "success";
	}
	
	//댓글 목록 조회
//	@PostMapping("/list")
	public List<BoardReplyDto> getList(@RequestParam int board_reply_origin) {
		log.info("iscome?");
		BoardReplyDto replyDto =BoardReplyDto.builder()
																					.board_reply_origin(board_reply_origin)
																					.build();
				
				
		List<BoardReplyDto> ReplyDto=sqlSession.selectList("board.ReplyList", replyDto);
				
				return ReplyDto;
	}

}
