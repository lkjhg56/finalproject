package com.kh.finalproject.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.vo.UpdateQuestionVO;

@Service
public class UploadQuestionSeviceImpl implements UploadQuestionService {

	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	
	@Override
	public void questionUpload(UpdateQuestionVO updateQuestionVO) throws Exception{
		//1. DB에 등록
		int seq= uploadQuestionDao.questionSequece();
		int userSeq=uploadQuestionDao.userSequence();
		
		UploadQuestionDto uploadQuestionDto = UploadQuestionDto.builder()
				.user_custom_question_no(userSeq)
				.user_no(updateQuestionVO.getUser_no())
				.category_name(updateQuestionVO.getCategory_name())
				.question_no(seq)
				.question_title(updateQuestionVO.getQuestion_title())
				.question_content(updateQuestionVO.getQuestion_content())
				.question_answer(updateQuestionVO.getQuestion_answer())
				.question_solution(updateQuestionVO.getQuestion_solution())
				.answer1(updateQuestionVO.getAnswer1())
				.answer2(updateQuestionVO.getAnswer2())
				.answer3(updateQuestionVO.getAnswer3())
				.answer4(updateQuestionVO.getAnswer4())
				.answer5(updateQuestionVO.getAnswer5())				
				.build();

		uploadQuestionDao.upload(uploadQuestionDto);
		
		//2. 파일 업로드(문제 사진)
		/**********************************************************************/
		//(1)DB에 등록
		//VO에서 받아온 내용을 DTO에 옮긴다.
		List<UploadQuestionFileDto> list = new ArrayList<>();
		for(MultipartFile mf : updateQuestionVO.getFile()) {
			list.add(UploadQuestionFileDto.builder()
				.file_save_name(UUID.randomUUID().toString())
				.file_upload_name(mf.getOriginalFilename())
				.file_type(mf.getContentType())
				.file_size(mf.getSize())
				.question_no(seq)
				.build());
		}		
		//새 디렉토리 생성
		File dir = new File("D:/upload/question_image");
		dir.mkdir();
		
		System.out.println(seq);
		for(int i=0;i<list.size();i++) {
			MultipartFile mf = updateQuestionVO.getFile().get(i);
			UploadQuestionFileDto dto = list.get(i);
			uploadQuestionDao.fileUpload(dto);
			File target = new File(dir, dto.getFile_save_name());
			mf.transferTo(target);
		}
	}
	
	@Override
	public void questionUpdate(UpdateQuestionVO updateQuestionVO) {
		//question 테이블 변경
		UploadQuestionDto uploadQuestionDto = UploadQuestionDto.builder()
				.category_name(updateQuestionVO.getCategory_name())//user_custom_question 테이블
				.question_title(updateQuestionVO.getQuestion_title())
				.question_content(updateQuestionVO.getQuestion_content())
				.answer1(updateQuestionVO.getAnswer1())
				.answer2(updateQuestionVO.getAnswer2())
				.answer3(updateQuestionVO.getAnswer3())
				.answer4(updateQuestionVO.getAnswer4())
				.answer5(updateQuestionVO.getAnswer5())				
				.question_answer(updateQuestionVO.getQuestion_answer())
				.question_solution(updateQuestionVO.getQuestion_solution())
				.question_premium(updateQuestionVO.getQuestion_premium())
				.build();
		uploadQuestionDao.updateQustion(uploadQuestionDto);
		
		//question_file 테이블 변경
		
		UploadQuestionFileDto uploadQuestionFileDto = UploadQuestionFileDto.builder()
				.file_save_name(updateQuestionVO.getFile_save_name())
				.file_upload_name(updateQuestionVO.getFile_upload_name())
				.file_type(updateQuestionVO.getFile_type())
				.file_size(updateQuestionVO.getFile_size())
				.build();
		uploadQuestionDao.updateFile(uploadQuestionFileDto);
	}
}
