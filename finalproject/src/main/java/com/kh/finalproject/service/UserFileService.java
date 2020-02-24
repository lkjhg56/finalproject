package com.kh.finalproject.service;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.UsersDto;

public interface UserFileService {

	//회원 가입
	void JoinWithFile(UsersDto usersDto, List<MultipartFile> user_file) throws IllegalStateException, IOException;

	//프로필 보여주기
	ResponseEntity<ByteArrayResource> getImg(int user_no) throws Exception;

	//프로필 수정
	void ProfileEdit(List<MultipartFile> user_file) throws IllegalStateException, IOException;

	//프로필 삭제 (기본 이미지)
	void ProfileDelete(int user_no);

}
