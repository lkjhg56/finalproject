package com.kh.finalproject.repository;

import java.util.List;
import java.util.Map;

import com.kh.finalproject.entity.UsersDto;

public interface UsersDao {
	List<UsersDto> getRank(Map<String, Integer> total);
}
