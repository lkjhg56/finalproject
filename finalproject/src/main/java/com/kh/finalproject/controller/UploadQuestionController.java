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
import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
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
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		return "question/solve";
	}
	@PostMapping("/solve")
	public String solve2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) {
		model.addAttribute("result", uploadQuestionService.questionSolve(updateQuestionVO));
		return "question/solve_result";
	}
	//문제 풀기(여러문제 한페이지당 최대 4문제)
	@GetMapping("/multi")
	public String multi(@RequestParam int wantQuestion, Model model) {
		List<UploadQuestionDto> list = uploadQuestionService.multiQuestion(wantQuestion);
		model.addAttribute("count",wantQuestion);
		model.addAttribute("list", list);
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
		UserQuestionMultiResultDto multi_dto = uploadQuestionService.insert_multi(list,dto);
		model.addAttribute("multi", multi_dto);
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
		return "redirect:list";
	}	
	//파일 다운로드(미리보기)
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_file_no) throws Exception{
		return uploadQuestionService.downloadImg(question_file_no);
	}
	//문제 정보 호출
	@GetMapping("/content")
	public String content(@RequestParam int question_no, Model model) {
		//이미지 리스트로 불러옴
		model.addAttribute("questionDto", uploadQuestionDao.question_all(question_no));
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		return "question/content";
	}
	//문제 수정
	@GetMapping("/update")
	public String update(@RequestParam int question_no, Model model) {
		List<UploadQuestionFileDto> image = uploadQuestionDao.getFile2(question_no);
		model.addAttribute("image",image);
		model.addAttribute("questionDto",uploadQuestionDao.question_all(question_no));
		return "question/update";
	}
	@PostMapping("/update")
	public String update2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) throws Exception {		
		uploadQuestionService.questionUpdate(updateQuestionVO);
		return "redirect:content?question_no="+updateQuestionVO.getQuestion_no();
	}	
	//문제 삭제
	@GetMapping("/delete")
	public String delete(@RequestParam int question_no, @RequestParam int user_custom_question_no, Model model) {
		uploadQuestionService.questionDelete(question_no, user_custom_question_no);
		return "redirect:list";
	}
	//문제 전체 리스트 호출(Users 테이블과 조인됨)
	@GetMapping("/list")
	public String list(Model model) {
		//정답률 계산하여 출력해줘야함.
		model.addAttribute("list",uploadQuestionDao.question_user_all());
		return "question/list";
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
	public String normallist(@RequestParam int no, String csname,String category_no,  Model model) {

		TestQuestionDto dto=TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();
		List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal3",dto);
		model.addAttribute("list",list2);

		return "question/normallist";
	}
	
	//일반문제 상세보기
	@GetMapping("/normalcontent")
	public String normalcontent(@RequestParam int no, Model model) {
	
		
		NormalUpdateQuestionVO normalUpdateQuestionVO=sqlSession.selectOne("question.getContent2", no);
		model.addAttribute("questionDto",normalUpdateQuestionVO);
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
	
	
	//일반 문제 삭제
	@GetMapping("/normaldelete")
	public String delete2(@RequestParam int no, String csname,String category_no,  Model model) {
		
		normalUploadQuestionService.questionDelete(no);
		TestQuestionDto dto=TestQuestionDto.builder()
				.csname(csname)
				.category_no(category_no)
				.build();
		List<TestQuestionDto> list2=sqlSession.selectList("question.getNormal3",dto);
		model.addAttribute("list",list2);

		return "question/normallist";
	}


}
