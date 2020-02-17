package com.kh.finalproject.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UserQuestionMultiResultDto;
import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.service.NormalUploadQuestionService;
import com.kh.finalproject.service.UploadQuestionService;
import com.kh.finalproject.vo.ExamResultVO;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;
import com.kh.finalproject.vo.UpdateQuestionVO;

@Controller
@RequestMapping("/question")
public class UploadQuestionController {
	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private SqlSession sqlSession;
	@Autowired
	private UploadQuestionService uploadQuestionService;	
	@Autowired
	private NormalUploadQuestionService normalUploadQuestionService;
	@Autowired
	private NormalUploadQuestionDao normalUploadQuestionDao;
	//문제 풀기(한문제)
	@GetMapping("/solve")
	public String solve(@RequestParam int question_no, Model model) {
		model.addAttribute("questionDto",uploadQuestionDao.question_all(question_no));
		return "question/solve";
	}
	@PostMapping("/solve")
	public String solve2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) {
		model.addAttribute("result", uploadQuestionService.questionSolve(updateQuestionVO));
		return "question/solve_result";
	}
	//문제 풀기(여러문제 한페이지당 최대 4문제)
	@GetMapping("/multi")
	public String multi(Model model) {
		//향후 원하는 문제개수를 받을 수 있도록 해야함.
		int wantQuestion=10;
		model.addAttribute("count",wantQuestion);
		model.addAttribute("list", uploadQuestionService.multiQuestion(wantQuestion));
		return "question/multi";
	}
	@PostMapping("/multi")
	public String multi2(@ModelAttribute ExamResultVO examResultVO,
			@ModelAttribute UserQuestionResultDto dto, Model model) {	
		//문제를 푼 시간
		model.addAttribute("time",dto);
		//각 문제에 대한 번호, 결과값을 비교하는 서비스
		List<UserQuestionResultDto> list = uploadQuestionService.checkMulti(examResultVO);		
		model.addAttribute("list", list);
		uploadQuestionService.insert_multi(list,dto);
		return "question/multi_result";
	}
	//문제 업로드
	@GetMapping("/upload")
	public String upload() {
		return "question/upload";
	}
	@PostMapping("/upload")
	public String upload2(@ModelAttribute UpdateQuestionVO updateQuestionVO, HttpSession session, Model model) throws Exception {
		updateQuestionVO.setUser_no(sqlSession.selectOne("question.getNo", (String)session.getAttribute("id")));
		uploadQuestionService.questionUpload(updateQuestionVO);
		model.addAttribute("list",sqlSession.selectList("question.getTotal2"));
		return "question/list";
	}	
	//파일 다운로드(미리보기)
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_no) throws Exception{
			return uploadQuestionService.downloadImg(question_no);					
	}
	//문제 수정
	@GetMapping("/update")
	public String update(@RequestParam int question_no, Model model) {
		model.addAttribute("questionDto",sqlSession.selectOne("question.getTotal", question_no));
		return "question/update";
	}
	@PostMapping("/update")
	public String update2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) throws Exception {		
		uploadQuestionService.questionUpdate(updateQuestionVO);
		model.addAttribute("questionDto",updateQuestionVO);
		return "question/content";
	}
	
	//문제 삭제
	@GetMapping("/delete")
	public String delete(@RequestParam int question_no, @RequestParam int user_custom_question_no, Model model) {
		uploadQuestionService.questionDelete(question_no, user_custom_question_no);
		model.addAttribute("list",sqlSession.selectList("question.getTotal2"));
		return "question/list";
	}

	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("list",sqlSession.selectList("question.getTotal2"));
		return "question/list";
	}
	//문제 정보 호출
	@GetMapping("/content")
	public String content(@RequestParam int question_no, Model model) {
		model.addAttribute("questionDto",sqlSession.selectOne("question.getTotal", question_no));
		return "question/content";
	}
	//일반문제만들기
	@GetMapping("/normalupload")
	public String normalupload() {
		return "question/normalupload";
	}
	
	
	@PostMapping("/normalupload")
	public String normalupload2(@ModelAttribute NormalUpdateQuestionVO normalUpdateQuestionVO, HttpSession session,Model model) throws Exception {
		
//		uploadQuestionService.questionUpload(updateQuestionVO);
		
		normalUploadQuestionService.normalquestionUpload(normalUpdateQuestionVO);
		List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal",normalUpdateQuestionVO);
//		model.addAttribute("vo",normalUpdateQuestionVO);
		model.addAttribute("list",list2);
		
		return "question/normallist";
	}
	
	
	//일반문제 목록
	@GetMapping("/normallist")
	public String normallist(Model model) {

		
		List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal");
		model.addAttribute("list",list2);
		
		return "question/normallist";
	}
	
	//일반문제 상세보기
	@GetMapping("/normalcontent")
	public String normalcontent(@RequestParam int no, Model model) {
	
		
		TestQuestionDto testQuestionDto =sqlSession.selectOne("question.getContent",no);
		model.addAttribute("questionDto",testQuestionDto);
		return "question/normalcontent";
	}	
	//일반문제 파일 미리보기
	@GetMapping("/qimage")
	public ResponseEntity<ByteArrayResource> previewImg2(@RequestParam int no) throws Exception{
	
		return normalUploadQuestionService.downloadImg(no);
		

	}
	
	
	
	@GetMapping("/normalupdate")
	public String normalupdate(@RequestParam int no, Model model) {
//		TestQuestionDto testQuestionDto= sqlSession.selectOne("question.getContent", no);
		
		NormalUpdateQuestionVO normalUpdateQuestionVO=sqlSession.selectOne("question.getContent2", no);
		model.addAttribute("questionDto",normalUpdateQuestionVO);
		return "question/normalupdate";
	}
	
	
	
	@PostMapping("/normalupdate")
	public String normalupdate2(@ModelAttribute NormalUpdateQuestionVO normalUpdateQuestionVO, Model model) throws Exception {		
		
		normalUploadQuestionService.normalquestionUpdate(normalUpdateQuestionVO);
		
		model.addAttribute("questionDto",normalUpdateQuestionVO);
		return "question/normalcontent";
	}
	

}
