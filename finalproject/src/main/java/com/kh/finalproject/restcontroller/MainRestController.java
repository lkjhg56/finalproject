package com.kh.finalproject.restcontroller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.TestDao;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/main")
@Slf4j
public class MainRestController {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private TestDao testDao;
	
	@PostMapping("search")
	public UsersDto search(@RequestParam String id) {
		
		UsersDto userInfo = sqlSession.selectOne("users.info", id);
		
		return userInfo;
	}
	
	@PostMapping("getRank")
	public List<UsersDto> rank(){
		Map<String, Object> getRank = new HashMap<>();
		getRank.put("start", 1);
		getRank.put("finish", 10);
		List<UsersDto> rank = sqlSession.selectList("users.grade_point_rank", getRank);
		
		return rank;
	}
	@PostMapping("gList")
	public List<String> qlist(){
		List<String> gList = testDao.getFrequency();
		return gList;
	}
	@PostMapping("newQList")
	public List<UploadQuestionDto> nList(){
		List<UploadQuestionDto> nList = sqlSession.selectList("nList");
		log.info("test123={}", nList.isEmpty());
		return nList;
		
	}
	
}
