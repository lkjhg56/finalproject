package com.kh.finalproject.repository;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardFileDto;

public interface BoardFileDao {
	
	int getsequence();
	
	void fileUpload(BoardFileDto boardfileDto);

	//파일 물리저장
	void save(String name, MultipartFile board_file) throws IllegalStateException, IOException;
	byte[] get(int board_file_no) throws IOException;	//번호를 받아서 바이트배열을 내놓는다
	
	//게시판 파일 가져오기
		BoardFileDto getFile(int board_file_no);
	
	//파일 불러오기 내부 기능
		String makeDispositionString(BoardFileDto boardfileDto) throws UnsupportedEncodingException;
	
	//게시글 파일 번호 가져오기
		BoardFileDto getFileNo(int board_no);
		
	//파일 수정
		void editFile(BoardFileDto boardfileDto);
		
	//파일 삭제
		void deleteFile(int board_file_no);
}
