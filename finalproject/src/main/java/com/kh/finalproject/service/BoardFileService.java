package com.kh.finalproject.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;




public interface BoardFileService {
	//1. 등록기능
	void regist(BoardDto boardDto);
	void registWithFile(BoardDto boardDto, List<MultipartFile> board_file) throws IllegalStateException, IOException;
	
	//2. 파일 불러오기
	ResponseEntity<ByteArrayResource> getImg(int board_no) throws Exception;
	
	//3.게시글 수정(+파일)
	void editWithFile(BoardDto boardDto, List<MultipartFile> board_file) throws IllegalStateException, IOException;

	//4.게시글 삭제 시 실제 파일 삭제
	void deleteRealfile(int board_no);
	
}
