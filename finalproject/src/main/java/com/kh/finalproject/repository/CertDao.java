package com.kh.finalproject.repository;

import com.kh.finalproject.entity.CertDto;

public interface CertDao {
	void regist(CertDto certDto);

	boolean check(String email, String cert);

	void delete(String email);
}
