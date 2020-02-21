package com.kh.finalproject.repository;

import java.io.UnsupportedEncodingException;

import com.kh.finalproject.entity.UserFileDto;

public interface UserFileDao {

	//가입할 때 파일 업로드
	void fileUpload(UserFileDto userFileDto);

	//파일 보여주기
	UserFileDto getFile(int user_no);
	String makeDispositionString(UserFileDto userFileDto) throws UnsupportedEncodingException;

	//파일 삭제
	void delete(int user_no);

}
