package com.kh.finalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
		int no = boardDto.getBoard_no();
		return "redirect:content?board_no="+no;		
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
		public String list(Model model, HttpServletRequest request) {
			
			//페이지 크기
			int pagesize = 10;
			//네비게이터 크기
			int navsize = 10;
			
			//페이징 추가
			int pno;
			try{
				pno = Integer.parseInt(request.getParameter("pno"));
				if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
			}
			catch(Exception e){
				pno = 1;
			}
				
			int finish = pno * pagesize;
			int start = finish - (pagesize - 1);
				System.out.println("start = " + start + " , finish = " + finish);
			
//**************************************************************************************
//		 		하단 네비게이터 계산하기
//				- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
//**************************************************************************************
			int count = boardDao.boardCount(); //전체글 개수를 구하는 메소드 
			System.out.println(count);
			
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
			System.out.println(pagecount);
			
			int startBlock = (pno -1) / navsize * navsize + 1;
			int finishBlock = startBlock + (navsize -1);
			
			//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
			if(finishBlock > pagecount){
				finishBlock = pagecount;
			}
			
			Map<String, Integer> param = new HashMap<>();
			param.put("start", start);
			param.put("finish", finish);
			model.addAttribute("list", boardDao.getList(param));
			
			request.setAttribute("pno", pno);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pagesize);
			request.setAttribute("navsize", navsize);
			
			return "board/list";
		}
		
		
/////////////////////////////////////////////////////////////////////////////////////////////		
	//게시판 카테고리별 조회
		@PostMapping("/list")
		public String list(@RequestParam String board_category,
									Model model,
									HttpServletRequest request) {
			
			//전체 카테고리 선택시
			if(board_category.equals("전체")) {
				//페이지 크기
				int pagesize = 10;
				//네비게이터 크기
				int navsize = 10;
				
				//페이징 추가
				int pno;
				try{
					pno = Integer.parseInt(request.getParameter("pno"));
					if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
				}
				catch(Exception e){
					pno = 1;
				}
					
				int finish = pno * pagesize;
				int start = finish - (pagesize - 1);
				//	System.out.println("start = " + start + " , finish = " + finish);
				
			//**************************************************************************************
//			 		하단 네비게이터 계산하기
//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
			//**************************************************************************************
				int count = boardDao.boardCount(); //전체글 개수를 구하는 메소드 
				int pagecount = (count + pagesize) / pagesize; //전체 페이지 수 11개
				
				int startBlock = (pno -1) / navsize * navsize + 1;
				int finishBlock = startBlock + (navsize -1);
				
				//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
				if(finishBlock > pagecount){
					finishBlock = pagecount;
				}
				
				Map<String, Integer> param = new HashMap<>();
				param.put("start", start);
				param.put("finish", finish);	
				
				request.setAttribute("pno", pno);
				request.setAttribute("count", count);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("navsize", navsize);
				
				model.addAttribute("list", boardDao.getList(param));
				
				return "board/list";
			}
			
			
			
			//다른 카테고리 선택시
			else {
				//페이지 크기
				int pagesize = 10;
					
				//네비게이터 크기
				int navsize = 3;
				
				//페이징 추가
				int pno;
				try{
					pno = Integer.parseInt(request.getParameter("pno"));
					if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
				}
				catch(Exception e){
					pno = 1;
				}
					
				int finish = pno * pagesize;
				int start = finish - (pagesize - 1);
				//	System.out.println("start = " + start + " , finish = " + finish);
				
			//**************************************************************************************
//			 		하단 네비게이터 계산하기
//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
			//**************************************************************************************
				int count = boardDao.boardCategoryCount(board_category); //전체글 개수를 구하는 메소드 
				System.out.println("카테고리 글수 = "+count);
				int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
				
				int startBlock = (pno -1) / navsize * navsize + 1;
				int finishBlock = startBlock + (navsize -1);
				
				//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
				if(finishBlock > pagecount){
					finishBlock = pagecount;
				}
				
				Map<String, String> param = new HashMap<>();
				param.put("start", String.valueOf(start));
				param.put("finish", String.valueOf(finish));
				param.put("board_category", board_category);
				
				request.setAttribute("pno", pno);
				request.setAttribute("count", count);
				request.setAttribute("pagesize", pagesize);
				request.setAttribute("navsize", navsize);
				
				boardDao.getCategoryList(param);
				model.addAttribute("list", boardDao.getCategoryList(param));
				
				return "board/list";						
			}							
		}
		
		
		//게시글 검색
		@GetMapping("/search")
		public String search() {
			return "board/search";
		}
		
		//게시글 검색
		@PostMapping("/search")
		public String search(@RequestParam String type,
										@RequestParam String keyword,
										Model model,
										HttpServletRequest request) {
			//페이지 크기
			int pagesize = 10;
			//네비게이터 크기
			int navsize = 10;
			
			//페이징 추가
			int pno;
			try{
				pno = Integer.parseInt(request.getParameter("pno"));
				if(pno <= 0) throw new Exception(); //음수를 입력하면 예외를 발생시킨다
			}
			catch(Exception e){
				pno = 1;
			}
				
			int finish = pno * pagesize;
			int start = finish - (pagesize - 1);
				System.out.println("start = " + start + " , finish = " + finish);
			
//**************************************************************************************
//		 		하단 네비게이터 계산하기
//				- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
//**************************************************************************************
			int count = boardDao.boardCount(); //전체글 개수를 구하는 메소드 
			System.out.println(count);
			
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
			System.out.println(pagecount);
			
			int startBlock = (pno -1) / navsize * navsize + 1;
			int finishBlock = startBlock + (navsize -1);
			
			//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
			if(finishBlock > pagecount){
				finishBlock = pagecount;
			}
			
			Map<String, String> param = new HashMap<>();
			param.put("start", String.valueOf(start));
			param.put("finish", String.valueOf(finish));
			param.put("type", type);
			param.put("keyword", keyword);
//			System.out.println("Map<String, String> param"+param);
			model.addAttribute("search", boardDao.search(param));
			
			request.setAttribute("pno", pno);
			request.setAttribute("count", count);
			request.setAttribute("pagesize", pagesize);
			request.setAttribute("navsize", navsize);
			request.setAttribute("type", type);
			request.setAttribute("keyword", keyword);
			
			
			return "board/search";
		}
				
		
}

