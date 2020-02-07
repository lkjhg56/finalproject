package com.kh.finalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardReplyDto;
import com.kh.finalproject.repository.BoardDao;
import com.kh.finalproject.service.BoardFileService;

import lombok.extern.slf4j.Slf4j;



@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardDto boardDto;
	
	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private BoardFileService boardfileService;
	
	//글 작성
	@GetMapping("/regist")
	public String regist() {
		return "board/regist";
	}
	
	@PostMapping("/regist")
	public String regist(@ModelAttribute BoardDto boardDto,
									@RequestParam(required = false) List<MultipartFile> board_file,
									HttpSession session) throws IllegalStateException, IOException {		

		String board_writer = (String) session.getAttribute("id");
		boardDto.setBoard_writer(board_writer);
		boardfileService.registWithFile(boardDto, board_file);
		return "redirect:list";			
	}
	
	
	//글 수정(본인글, 관리자만)
	@GetMapping("/edit")
	public String edit(@RequestParam int board_no,
									Model model) {
		boardDao.get(board_no);
		model.addAttribute("boardDto", boardDao.get(board_no));		
		return "board/edit";
	}
	
	@PostMapping("/edit")
	public String edit(@ModelAttribute BoardDto boardDto) {
		boardDao.edit(boardDto);
		return "board/content";		
	}
	
	//글 삭제(본인글, 관리자만)
	@GetMapping("/delete")
	public String delete(@RequestParam int board_no) {
		boardDao.delete(board_no);
		return "redirect:list";
	}
	
	
	//게시글 상세 조회
	@GetMapping("/content")
	public String content(@RequestParam int board_no,
										Model model) {
		boardDao.get(board_no);
		model.addAttribute("boardDto", boardDao.get(board_no));
		boardDao.getReplyList(board_no);
		model.addAttribute("boardReplyDto", boardDao.getReplyList(board_no));
		return "board/content";
	}

	
	//전체 목록 조회
		@GetMapping("/list")
		public String list(Model model) {
			model.addAttribute("list", boardDao.getList());
			return "board/list";
		}
		
	//게시판 카테고리별 조회
		@PostMapping("/list")
		public String list(@RequestParam String board_category,
									Model model) {
			
				if(board_category.equals("전체")) {
					model.addAttribute("list", boardDao.getList());
					return "board/list";
				}
				else {
					boardDao.getCategoryList(board_category);
					model.addAttribute("list", boardDao.getCategoryList(board_category));
					return "board/list";						
				}							
		}
		
		@GetMapping("/search")
		public String search() {
			return "board/search";
		}
		
		//게시글 검색
		@PostMapping("/search")
		public String search(@RequestParam String search_type,
										@RequestParam String search_keyword,
										Model model) {
			Map<String, String> param = new HashMap<>();
			param.put("search_type", search_type);
			param.put("search_keyword", search_keyword);
//			System.out.println("Map<String, String> param"+param);
			model.addAttribute("search", boardDao.search(param));
			return "board/search";
		}
		
		
//////////////////////////////////////////////////////////////////////
		//댓글 수정
		@PostMapping("/content")
		@ResponseBody
		public String replyEdit(@ModelAttribute BoardReplyDto boardReplyDto,
												HttpSession session) {
			System.out.println(boardReplyDto.getBoard_reply_no());
			boardDao.replyEdit(boardReplyDto);			
			
			String board_reply_writer = (String) session.getAttribute("id");
			boardReplyDto.setBoard_reply_writer(board_reply_writer);
			boardDao.replyRegist(boardReplyDto);
			return "redirect:content";
		}
		
}

