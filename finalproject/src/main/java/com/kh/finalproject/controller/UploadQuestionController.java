package com.kh.finalproject.controller;

import java.io.File;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.service.UploadQuestionService;
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
		return "question/questions";
	}
	//파일 다운로드(미리보기)
	@GetMapping("/image")
	public ResponseEntity<ByteArrayResource> previewImg(@RequestParam int question_no) throws Exception{
		UploadQuestionFileDto uploadQuestionFileDto = sqlSession.selectOne("question.getFile",question_no);	
		File directory = new File("D:/upload/question_image");
		//directory의 위치에 있는 profile_no란 이름의 파일을 찾아서 불러온 뒤 반환
		
		File file = new File(directory, String.valueOf(uploadQuestionFileDto.getFile_save_name()));
		byte[] data = FileUtils.readFileToByteArray(file);

		ByteArrayResource resource = new ByteArrayResource(data);
		
		return ResponseEntity.ok()
				//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(uploadQuestionFileDto.getFile_size())
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						uploadQuestionDao.makeDispositionString(uploadQuestionFileDto))
				.body(resource);
	}
	@GetMapping("/update")
	public String update(@RequestParam int question_no, Model model) {
		UploadQuestionDto uploadQuestionDto = sqlSession.selectOne("question.getTotal", question_no);
		UploadQuestionFileDto uploadQuestionFileDto = sqlSession.selectOne("question.getFile",question_no);
		model.addAttribute("questionDto",uploadQuestionDto);
		model.addAttribute("fileDto",uploadQuestionFileDto);
		return "question/update";
	}
	@PostMapping("/update")
	public String update2(@ModelAttribute UpdateQuestionVO updateQuestionVO) {
		System.out.println(updateQuestionVO.getFile_upload_name());
		uploadQuestionService.questionUpdate(updateQuestionVO);
		return "question/content";
	}
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", uploadQuestionDao.getList());
		return "question/list";
	}
	@GetMapping("/content")
	public String content(@RequestParam int question_no, Model model) {
		UploadQuestionDto uploadQuestionDto = sqlSession.selectOne("question.getTotal", question_no);
		model.addAttribute("questionDto",uploadQuestionDto);
		return "question/content";
	}
	
}
