package com.kh.finalproject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.UploadQuestionDao;

@RunWith(SpringJUnit4ClassRunner.class)//실행 환경을 설정 : 테스트 실행 방법 확장 
@WebAppConfiguration//웹 어플리케이션의 기본 설정 무시
@ContextConfiguration(locations={ //불러올 환경의 설정파일 지정 
		"file:src/main/webapp/WEB-INF/spring/root-context.xml",
		"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml" 
})

public class QTest {
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private SqlSession sqlSession;
	
	@Test
	public void questionSequece() {
		int s = sqlSession.selectOne("question.getNo","dlgudwn");
//		System.out.println(s);
	}
	
}
