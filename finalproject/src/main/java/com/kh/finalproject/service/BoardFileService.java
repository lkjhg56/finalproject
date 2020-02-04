package com.kh.finalproject.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.BoardDto;




public interface BoardFileService {
	void regist(BoardDto boardDto, List<MultipartFile> board_file) throws IllegalStateException, IOException;


}
