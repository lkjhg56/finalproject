package com.kh.finalproject.repository;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardFileDto;

public interface BoardFileDao {
	
	int getsequence();
	
	void fileUpload(BoardFileDto boardfileDto);
	
	BoardFileDto getFile(int board_file_no);

	//파일 물리저장
	void save(String name, MultipartFile board_file) throws IllegalStateException, IOException;
	byte[] get(int board_file_no) throws IOException;	//번호를 받아서 바이트배열을 내놓는다
}
