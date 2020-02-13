package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.ResultDto;

public interface ResultDao {
	List<ResultDto> getList(Map<String, String> total);
	List<ResultDto> searchList(Map<String,String> total);
//	int getCount(String users_id);
	int search_getCount(Map<String,String> ready);
}
