package com.kh.finalproject.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.repository.BoardDao;
import com.kh.finalproject.service.BoardFileService;



@Controller
@RequestMapping("board")
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
									@RequestParam List<MultipartFile> board_file) throws IllegalStateException, IOException {
		
		boardfileService.regist(boardDto, board_file);
		
		return "redirect:list";
	}
	
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
	
	
	//게시글 상세 조회
	@GetMapping("/content")
	public String content(@RequestParam int board_no,
										Model model) {
		boardDao.get(board_no);
		model.addAttribute("boardDto", boardDao.get(board_no));
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
	

}
