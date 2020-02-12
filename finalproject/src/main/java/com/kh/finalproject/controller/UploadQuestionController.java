package com.kh.finalproject.controller;

import java.util.ArrayList;
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

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.service.UploadQuestionService;
import com.kh.finalproject.vo.ExamResultVO;
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
	//문제 풀기(한문제)
	@GetMapping("/solve")
	public String solve(@RequestParam int question_no, Model model) {
		UploadQuestionDto uploadQuestionDto = uploadQuestionDao.question_all(question_no);
		model.addAttribute("questionDto",uploadQuestionDto);
		return "question/solve";
	}
	@PostMapping("/solve")
	public String solve2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) {
		UserQuestionResultDto userQuestionResultDto = uploadQuestionService.questionSolve(updateQuestionVO);
		model.addAttribute("result", userQuestionResultDto);
		return "question/solve_result";
	}
	//문제 풀기(여러문제 최대 4문제)
	@GetMapping("/multi")
	public String multi(Model model) {
		int wantQuestion=11;
		List<UploadQuestionDto> choice_list=uploadQuestionService.multiQuestion(wantQuestion);
		model.addAttribute("count",wantQuestion);
		model.addAttribute("list", choice_list);
		return "question/multi";
	}
	@PostMapping("/multi")
	public String multi2(@ModelAttribute ExamResultVO examResultVO,
			@ModelAttribute UserQuestionResultDto dto, Model model) {
		
		List<UserQuestionResultDto> list = new ArrayList<>();
		for(ExamResultVO vo : examResultVO.getQuestion()) {
			list.add(UserQuestionResultDto.builder()
					.question_no(vo.getNo())
					.question_answer(vo.getAnswer())
					.tried_user(vo.getId())
					.build());				
		}

		//문제를 푼 시간
		model.addAttribute("time",dto);
		//각 문제에 대한 번호, 결과값
		model.addAttribute("list",list);
		//정답여부, 정답률을 체크해준다. question_no로 원래 답을 호출하여 위 리스트내에
		UploadQuestionDto uploadQuestionDto = sqlSession.selectOne("question.getOne", list.get(0).getQuestion_no());
		uploadQuestionDto.getQuestion_answer();
		boolean result;
		list.get(0).getQuestion_no();
		list.get(0).getQuestion_answer();
		return "question/multi_result";
	}
	
	@GetMapping("/upload")
	public String upload() {
		return "question/upload";
	}
	@PostMapping("/upload")
	public String upload2(@ModelAttribute UpdateQuestionVO updateQuestionVO, HttpSession session) throws Exception {
		String id = (String)session.getAttribute("id");
		int sq = sqlSession.selectOne("question.getNo", id);
		updateQuestionVO.setUser_no(sq);
		uploadQuestionService.questionUpload(updateQuestionVO);
		return "question/list";
	}
	//파일 다운로드(미리보기)
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_no) throws Exception{
		return uploadQuestionService.downloadImg(question_no);
		
	}
	@GetMapping("/update")
	public String update(@RequestParam int question_no, Model model) {
		UploadQuestionDto uploadQuestionDto = sqlSession.selectOne("question.getTotal", question_no);
		model.addAttribute("questionDto",uploadQuestionDto);
		return "question/update";
	}
	@PostMapping("/update")
	public String update2(@ModelAttribute UpdateQuestionVO updateQuestionVO, Model model) throws Exception {		
		uploadQuestionService.questionUpdate(updateQuestionVO);
		model.addAttribute("questionDto",updateQuestionVO);
		return "question/content";
	}
	//아직 덜됨.
	@GetMapping("/delete")
	public String delete(@RequestParam int question_no,@RequestParam int user_custom_question_no) {
		uploadQuestionService.questionDelete(question_no, user_custom_question_no);
		return "question/list";
	}
	
	@GetMapping("/list")
	public String list(Model model) {
		List<UploadQuestionDto> list = sqlSession.selectList("question.getTotal2");
		model.addAttribute("list",list);
		return "question/list";
	}
	@GetMapping("/content")
	public String content(@RequestParam int question_no, Model model) {
		UploadQuestionDto uploadQuestionDto = sqlSession.selectOne("question.getTotal", question_no);
		model.addAttribute("questionDto",uploadQuestionDto);
		return "question/content";
	}
	
}
