package com.kh.finalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;
import com.kh.finalproject.entity.BoardFileDto;
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
	
	@Autowired
	private BoardFileDto boardfileDto;
	
	@Autowired
	private SqlSession sqlSession;
	
////////////글 작성///////////////////////////
	@GetMapping("/regist")
	public String regist() {
		return "board/regist";
	}
	
	@PostMapping("/regist")
	public String regist(@ModelAttribute BoardDto boardDto,
									@RequestParam(required = false) List<MultipartFile> board_file,
									HttpSession session) throws IllegalStateException, IOException {		

		String board_writer = (String) session.getAttribute("id");
		log.info(board_writer);
		boardDto.setBoard_writer(board_writer);
		boardfileService.registWithFile(boardDto, board_file);
		return "redirect:list";			
	}
	
	
/////////////글 수정(본인글, 관리자만)//////////
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
	
	
///////////////글 삭제(본인글, 관리자만)/////////////
	
	@GetMapping("/delete")
	public String delete(@RequestParam int board_no) {
		boardDao.delete(board_no);
		return "redirect:list";
	}
	
	
/////////////////게시글 상세 조회///////////////////
	@GetMapping("/content")
	public String content(@RequestParam int board_no,
										Model model) throws Exception {
		boardDao.get(board_no);
		model.addAttribute("boardDto", boardDao.get(board_no));
		boardDao.getReplyList(board_no);
		model.addAttribute("boardReplyDto", boardDao.getReplyList(board_no));
		
		List<BoardFileDto> list = new ArrayList<>();
		
		//파일정보도 리스트로 담아서 첨부
		List<BoardFileDto> dto = sqlSession.selectList("board.getFileNO", board_no);
		for(int i = 0; i < dto.size(); i ++) {
			System.out.println("board_file_no = "+dto.get(i).getBoard_file_no());
		}
		
		for(int i = 0; i < dto.size(); i ++) {
			int board_file_no = dto.get(i).getBoard_file_no();
			boardfileDto = boardDao.getFile(board_file_no);
			System.out.println("파일정보 = "+boardfileDto);		
			list.add(boardfileDto);
		}
		
		model.addAttribute("list", list);
		
		return "board/content";
	}

	////////////////////////////이미지 1개 보여주기//////////////////////////////
	@GetMapping("/boardimg")
	public ResponseEntity<ByteArrayResource> getImg(@RequestParam int board_file_no) throws Exception{
		return boardfileService.getImg(board_file_no);
	}
	
	
	
////////////////////전체 목록 조회///////////////////
	@GetMapping("/list")
	public String list(@RequestParam(defaultValue = "전체") String board_category, Model model, HttpServletRequest request) {
		System.out.println(board_category);
		
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
				System.out.println("start = " + start + " , finish = " + finish);
			
		//**************************************************************************************
		//			 		하단 네비게이터 계산하기
		//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
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
		
		
		//카테고리별 네비게이터 적용
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
		//		하단 네비게이터 계산하기
		//		- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
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
			request.setAttribute("board_category", board_category);
			
			boardDao.getCategoryList(param);
			model.addAttribute("list", boardDao.getCategoryList(param));			

			return "board/list";
		}
		
	}
		
		
////////////////////게시글 검색//////////////////////////
		@GetMapping("/search")
		public String search(@RequestParam(required = false) String type,
											@RequestParam(required = false) String keyword,
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
			Map<String, String> param = new HashMap<>();
			param.put("start", String.valueOf(start));
			param.put("finish", String.valueOf(finish));
			param.put("type", type);
			param.put("keyword", keyword);
				
				
			int count = boardDao.boardSearchCount(param); //검색결과 전체글 개수를 구하는 메소드 
			System.out.println(count);
			
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
			System.out.println(pagecount);
			
			int startBlock = (pno -1) / navsize * navsize + 1;
			int finishBlock = startBlock + (navsize -1);
			
			//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
			if(finishBlock > pagecount){
				finishBlock = pagecount;
			}
			
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

