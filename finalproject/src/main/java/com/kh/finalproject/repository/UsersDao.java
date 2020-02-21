package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.UsersDto;

public interface UsersDao {
	
	//가입
//	int getsequence();
//	void regist(UsersDto usersDto);
	
	//grade_point 랭킹
	List<UsersDto> getRank(Map<String, Integer> total);
	//user 정보보기
	UsersDto getInfo(String id);
	//회원 목록보기(관리자)
	List<UsersDto> getUserList(Map<String, Integer> total);

}
