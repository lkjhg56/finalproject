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
	private BoardDto boardDto;
	
	@Autowired
	private BoardDao boardDao;
	

	
	@PostMapping("/content")
	public String insert(@RequestParam String board_reply_content, String board_reply_writer,
									@RequestParam int board_reply_origin,
									HttpSession session) {
		
		BoardReplyDto boardReplyDto = BoardReplyDto.builder()
								.board_reply_content(board_reply_content)
								.board_reply_writer(board_reply_writer)
								.board_reply_origin(board_reply_origin)
								.build();
		
		sqlSession.insert("board.registReply", boardReplyDto);
		
		int no = boardDto.getBoard_no();
		return null;	
	}
	
	
	@DeleteMapping("/content")
	public String delete(@RequestParam int board_reply_no) {
		
		sqlSession.delete("board.deleteReply", board_reply_no);
		return null;
	}
	
	
	//댓글 수정
			@PutMapping("/content")
			public String replyEdit(@ModelAttribute BoardReplyDto boardReplyDto,
													@ModelAttribute BoardDto boardDto,
													HttpSession session) {
				System.out.println(boardReplyDto.getBoard_reply_no());
				boardDao.replyEdit(boardReplyDto);			
				
//				int no = boardDto.getBoard_no();
//				return "redirect:content?board_no="+no;
				return null;
			}
			
			@PostMapping("/list")
			public List<BoardReplyDto> getList() {
				log.info("iscome?");
				BoardReplyDto replyDto =BoardReplyDto.builder()
																							.board_reply_origin(33)
																							.board_reply_no(4)
																							.build();
						
						
				List<BoardReplyDto> ReplyDto=sqlSession.selectList("board.ReplyList", replyDto);
						
						return ReplyDto;
			}

}
