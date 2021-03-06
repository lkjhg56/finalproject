package com.kh.finalproject.restcontroller;

import java.io.File;
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
import com.kh.finalproject.entity.BoardFileDto;
import com.kh.finalproject.entity.BoardReplyDto;
import com.kh.finalproject.repository.BoardDao;
import com.kh.finalproject.repository.BoardFileDao;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/board2")
@Slf4j
public class BoardRestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BoardFileDao boardfileDao;
	
	//댓글 등록
		@PostMapping("/insert")
		public String insert(@RequestParam String board_reply_content, 
										@RequestParam int board_reply_origin, 
										@RequestParam(required = false, defaultValue = "0") int superno,
										@RequestParam(required = false, defaultValue = "0") int groupno,
										HttpSession session) {
			//댓글 번호 생성
			int no = sqlSession.selectOne("board.getReplySequence");
			String board_reply_writer = (String) session.getAttribute("id");
			
			//새댓글작성 (글번호와 그룹번호는 동일, 부모글 0번 , 차수 0)
			if(superno == 0) { 
				
				BoardReplyDto boardReplyDto = BoardReplyDto.builder()
										.board_reply_no(no)
										.board_reply_content(board_reply_content)
										.board_reply_writer(board_reply_writer)
										.board_reply_origin(board_reply_origin)
										.groupno(no)
										.build();
//				sqlSession.insert("board.registReply", boardReplyDto);
//				BoardReplyDto result = sqlSession.selectOne("board.getOneReply", no);
				boardDao.replyRegist(boardReplyDto);
				
				boardDao.replyCount(board_reply_origin);
				return "success";		
				
			}
			
			//대댓글 작성
			else {				
				//리플 번호 주고 리플 정보 가져오기 (그룹번호는 부모 그룹 번호, superno = 부모글 번호, 차수 = 부모글 + 1)
				BoardReplyDto target = boardDao.getReply(superno);
				target.setBoard_reply_no(no);
				target.setSuperno(superno);
				target.setGroupno(groupno);
				target.setDepth(target.getDepth()+1);
				target.setBoard_reply_writer(board_reply_writer);
				target.setBoard_reply_content(board_reply_content);
				target.setBoard_reply_origin(board_reply_origin);
				
				boardDao.rereplyRegist(target);
				boardDao.replyCount(board_reply_origin);
				
				return "success";		
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
	
	//이미지 삭제
	@PostMapping("/deleteImg")
	public String deleteImg(@RequestParam int board_no) {
		
			BoardDto boardDto = boardDao.get(board_no);
		
		//(1)기존 파일 모두 삭제하기
			List<BoardFileDto> delete = boardfileDao.getFileNo(boardDto.getBoard_no()); //파일 정보 list형태로 가져오기
				
		//반복문으로 파일 삭제 실행
		for(int i = 0; i < delete.size(); i++) {
			//DB에 저장된 파일 정보 삭제
			boardfileDao.deleteFile(board_no);
			
			//실제 파일 삭제
			String filepath = "D:/upload/kh2b/board_files/" + delete.get(i).getBoard_file_save_name();			
			File file = new File(filepath);
			file.delete();				
		}		
		
		return "success";
	}
	
	
	//댓글 수정
	@PostMapping("/edit2")
	public String replyEdit(@ModelAttribute BoardReplyDto boardReplyDto,
											@ModelAttribute BoardDto boardDto,
											HttpSession session) {
		boardDao.replyEdit(boardReplyDto);			
		
		return "success";
	}
	
	//댓글 목록 조회
//	@PostMapping("/list")
	public List<BoardReplyDto> getList(@RequestParam int board_reply_origin) {
		BoardReplyDto replyDto =BoardReplyDto.builder()
																					.board_reply_origin(board_reply_origin)
																					.build();
				
				
		List<BoardReplyDto> ReplyDto=sqlSession.selectList("board.ReplyList", replyDto);
				
				return ReplyDto;
	}


}
