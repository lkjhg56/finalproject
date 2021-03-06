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

		
		TestQuestionDto testDto =TestQuestionDto.builder()
												.csname(csname)
												.category_no(category_no)
												.build();
		
		List<TestQuestionDto> questionList = sqlSession.selectList("test.getQuesNum", testDto);
		
		int questionCount = questionList.size();
		int correctCount = sqlSession.selectOne("test.getCorrectNum", rno);

		
				int score = 100/questionCount*correctCount;
		SetScoreVO scoreVO = SetScoreVO.builder()
															.score(score)
															.rno(rno)
															.build();
																
		
		sqlSession.update("sumScore", scoreVO);
		


		return score;
		
	}

	@Override
	public TestQuestionDto getDto(String categoryname,int no,HttpSession session ) {
	
		
		

	TestQuestionDto dto= new TestQuestionDto();
	int count=no+1;
	
	session.setAttribute("no", count);
	
	
dto.setNo(count);
dto.setCsname(categoryname);
dto.setCategoryname(categoryname);
	
		return sqlSession.selectOne("test.questionDto", dto);
	}

	@Override
	public int getScore(int rno) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDtocount(String categoryname, String session) {
	TestQuestionDto dto= TestQuestionDto.builder()
//			.csname(categoryname)
			.category_no(session)
			.categoryname(categoryname)
			.build();
		
		return sqlSession.selectOne("test.quescountDto", dto);
	}

	@Override
	public List<String> getFrequency() {
		List<String> getName = sqlSession.selectList("prequencyList");

		
		
		return getName;
	}

	@Override
	public int getisCorrect(int rno) {
		int correctCount = sqlSession.selectOne("test.getCorrectNum", rno);
		
		
		
		return correctCount;
	}

	@Override
	public int getCount(String category_no, String csname) {
		TestQuestionDto testDto =TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();
		
	int count=	sqlSession.selectOne("test.getCountCorrect", testDto);
		return count;
	}

}
