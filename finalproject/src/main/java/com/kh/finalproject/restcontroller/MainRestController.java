package com.kh.finalproject.restcontroller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.entity.UsersDto;

@RestController
@RequestMapping("/main")
public class MainRestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@PostMapping("search")
	public UsersDto search(@RequestParam String id) {
		
		UsersDto userInfo = sqlSession.selectOne("users.info", id);
		
		return userInfo;
	}
	
}
