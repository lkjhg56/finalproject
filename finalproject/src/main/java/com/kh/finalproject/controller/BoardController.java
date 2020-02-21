package com.kh.finalproject.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.kh.finalproject.repository.BoardFileDao;
import com.kh.finalproject.service.BoardFileService;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board")
@Slf4j
public class BoardController {
	
	@Autowired
	private BoardFileDao boardfileDao;
	
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
//		log.info(board_writer);
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
	public String edit(@ModelAttribute BoardDto boardDto,
									@RequestParam (required = false) List<MultipartFile> board_file) throws IllegalStateException, IOException {
		

		List<BoardFileDto> dto = boardfileDao.getFileList(boardDto.getBoard_no());
	
		if(!dto.isEmpty()) {
			for(int i = 0; i < dto.size(); i ++) {				
				int board_file_no = dto.get(i).getBoard_origin_content_no();	//file_no받아오기			
				boardfileDao.deleteFile(board_file_no);
				BoardFileDto delete = boardfileDao.getFile(board_file_no); //지울 파일의 실체 가져옴
				String filepath = "D:/upload/board_files/"+delete.getBoard_file_save_name();
				File file = new File(filepath);
				file.delete();
			}
		}

		boardfileService.editWithFile(boardDto, board_file);
		
		int no = boardDto.getBoard_no(); //게시글 번호 구해서 리다이렉트에 사용
		return "redirect:content?board_no="+no;		
	}	
	
	
///////////////글 삭제(본인글, 관리자만)/////////////
	
	@GetMapping("/delete")
	public String delete(@ModelAttribute BoardDto boardDto, @ModelAttribute BoardFileDto BoardFileDto) {
		List<BoardFileDto> dto = boardfileDao.getFileList(boardDto.getBoard_no());
//		System.out.println(dto.get(0).getBoard_origin_content_no());
		if(!dto.isEmpty()) {
			for(int i = 0; i < dto.size(); i ++) {				
				int board_file_no = dto.get(i).getBoard_origin_content_no();//181	
				List<BoardFileDto> delete = boardfileDao.getFile2(board_file_no); //지울 파일의 실체 가져옴
				String filepath = "D:/upload/board_files/"+delete.get(i).getBoard_file_save_name();
				File file = new File(filepath);
				file.delete();
				boardfileDao.deleteFile(board_file_no);
			}
		}

		return "redirect:list";
	}

/////////////////게시글 상세 조회///////////////////
	@GetMapping("/content")
	public String content(@RequestParam int board_no,
										Model model,
										HttpServletRequest request,
										HttpSession session) throws Exception {
		BoardDto getdto = boardDao.get(board_no);
		
		//추가 : 이미 읽은 글은 조회수 증가를 방지
		//[1] 세션에 있는 저장소를 꺼내고 없으면 신규 생성한다
		Set<Integer> memory = (Set<Integer>) session.getAttribute("memory");
		
		//memory가 없는 경우에는 null 값을 가진다
		if(memory == null){
			memory = new HashSet<>();	//세션에 저장소가 없으면 저장소를 1번 생성한다.
		}
		
		//[2] 처리를 수행한다
		String board_writer = (String) session.getAttribute("id");
//		System.out.println("sessionID = "+board_writer);
		if(board_writer != null) {
			boolean isMine = board_writer.equals(getdto.getBoard_writer()); //사용자ID == 작성자ID 라고 물어보는것
			boolean isFirst = memory.add(board_no); //배열에 조회한 글번호를 넣어서 처음 들어가면 true, 재조회라면 false임 
// 		System.out.println("memory="+memory); //배열에 저장된 글번호를 조회해 확인용.
			
			//[3] 처리를 마치고 저장소를 다시 세션에 저장한다
			session.setAttribute("memory", memory); //세션에 저장소가 들어간다.
			
			//남의 글이라면 == !isMine  조회수를 증가시킨다.
			//처음읽는 글이라면 == isFirst
			if(!isMine && isFirst){		
				getdto.setBoard_readcount(getdto.getBoard_readcount()+1); //의도적으로 화면에 표시되는 조회수를 1 증가시킨다.
				boardDao.readCount(board_no);	//조회수 증가
			}
			
			model.addAttribute("boardDto", boardDao.get(board_no));			
		}
		else {
			model.addAttribute("boardDto", boardDao.get(board_no));		
		}
		
		
		
		//파일정보도 리스트로 담아서 첨부
		List<BoardFileDto> filelist = new ArrayList<>();
		List<BoardFileDto> dto = sqlSession.selectList("board.getFileNO", board_no);		
		for(int i = 0; i < dto.size(); i ++) {
			int board_file_no = dto.get(i).getBoard_file_no();
			boardfileDto = boardfileDao.getFile(board_file_no);
//			System.out.println("파일정보 = "+boardfileDto);		
			filelist.add(boardfileDto);
		}
		
		model.addAttribute("filelist", filelist);
		
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
//			System.out.println("start = " + start + " , finish = " + finish);
		
	//**************************************************************************************
	//			 		하단 네비게이터 계산하기
	//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
	//**************************************************************************************
		int count = boardDao.boardReplyCount(board_no); //전체글 개수를 구하는 메소드 
//		System.out.println("!!!"+count);
		
		int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
//		System.out.println(pagecount);
		
		int startBlock = (pno -1) / navsize * navsize + 1;
		int finishBlock = startBlock + (navsize -1);
		
		//만약 마지막 블록이 페이지 수보다 크다면 수정 처리
		if(finishBlock > pagecount){
			finishBlock = pagecount;
		}
		
		Map<String, Integer> param = new HashMap<>();
		param.put("start", start);
		param.put("finish", finish);
		param.put("board_no", board_no);		
		model.addAttribute("boardReplyDto", boardDao.getReplyList(param));
		
		request.setAttribute("pno", pno);
		request.setAttribute("count", count);
		request.setAttribute("pagesize", pagesize);
		request.setAttribute("navsize", navsize);
		request.setAttribute("board_no", board_no);
		
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
//		System.out.println(board_category);
		
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
//				System.out.println("start = " + start + " , finish = " + finish);
			
		//**************************************************************************************
		//			 		하단 네비게이터 계산하기
		//					- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
		//**************************************************************************************
			int count = boardDao.boardCount(); //전체글 개수를 구하는 메소드 
//			System.out.println(count);
			
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
//			System.out.println(pagecount);
			
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
		//		하단 네비게이터 계산하기
		//		- 시작블록 = (현재페이지-1) / 네비게이터크기 * 네비게이터크기 +1	
		//**************************************************************************************
			int count = boardDao.boardCategoryCount(board_category); //전체글 개수를 구하는 메소드 
//			System.out.println("카테고리 글수 = "+count);
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
//				System.out.println("start = " + start + " , finish = " + finish);
			
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
//			System.out.println(count);
			
			int pagecount = (count + pagesize) / pagesize; //전체 페이지 수
//			System.out.println(pagecount);
			
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

