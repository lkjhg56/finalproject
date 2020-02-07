package com.kh.finalproject.service;

import com.kh.finalproject.entity.GradePointDto;
import com.kh.finalproject.entity.UsersDto;

public interface GradePointService {

	void givePoint(GradePointDto pointDto,UsersDto usersDto);
}
