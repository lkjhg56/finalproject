package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.ResultDto;

public interface ResultDao {
	List<ResultDto> getList(String users_id);
	List<ResultDto> searchList(Map<String,String> ready);
}
