package com.kh.finalproject.repository;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.vo.SetScoreVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestDaoImpl implements TestDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<TestDto> getList() {
		return sqlSession.selectList("test.list");
	}

	@Override
	public List<CategoryDto> getDetailList(int tno) {
		return sqlSession.selectList("test.categoryList", tno);
	}

	@Override
	public List<TestQuestionDto> getQuestionList(String categoryname) {
		return sqlSession.selectList("test.questionList", categoryname);
	}

	@Override
	public List<CategoryDto> getQuestionList2(String categoryname) {
		
		return sqlSession.selectList("test.questionList2", categoryname);
	}

	@Override
	public int getScore(int rno, String category_no, String csname) {
		log.info("rno={}", rno);
		log.info("csname ={}", csname);
		
		TestQuestionDto testDto =TestQuestionDto.builder()
												.csname(csname)
												.category_no(category_no)
												.build();
		
		List<TestQuestionDto> questionList = sqlSession.selectList("test.getQuesNum", testDto);
		
		int questionCount = questionList.size();
		int correctCount = sqlSession.selectOne("test.getCorrectNum", rno);
		log.info("questioncount={}", questionCount);
		log.info("correctCount={}", questionCount);
				int score = 100/questionCount*correctCount;
		SetScoreVO scoreVO = SetScoreVO.builder()
															.score(score)
															.rno(rno)
															.build();
																
		
		sqlSession.update("sumScore", scoreVO);
		

		log.info("correct ={}", correctCount);
		log.info("score={}", score);
		return score;
		
	}

	@Override
	public TestQuestionDto getDto(String categoryname,HttpSession session) {
	
		
		int no= (int)session.getAttribute("no");

	TestQuestionDto dto= new TestQuestionDto();
	int count=no+1;
	
	session.setAttribute("no", count);
	
	
dto.setNo(count);
dto.setCsname(categoryname);
	
		return sqlSession.selectOne("test.questionDto", dto);
	}

	@Override
	public int getScore(int rno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDtocount(String csname, String category_no) {
	TestQuestionDto dto= TestQuestionDto.builder()
			.csname(csname)
			.category_no(category_no)
			.build();
		
		return sqlSession.selectOne("test.quescountDto", dto);
	}

}
