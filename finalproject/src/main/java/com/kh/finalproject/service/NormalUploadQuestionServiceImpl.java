package com.kh.finalproject.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.finalproject.entity.CategoryDto;
import com.kh.finalproject.entity.SolutionDto;
import com.kh.finalproject.entity.TestDto;
import com.kh.finalproject.entity.TestQuestionDto;
import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UploadTestQuestionFileDto;
import com.kh.finalproject.repository.NormalUploadQuestionDao;
import com.kh.finalproject.vo.NormalUpdateQuestionVO;
import com.kh.finalproject.vo.UpdateQuestionVO;
@Service
public class NormalUploadQuestionServiceImpl implements NormalUploadQuestionService{

	
	@Autowired
	private NormalUploadQuestionDao  NormalUploadQuestionDao;

	@Override
	public void normalquestionUpload(NormalUpdateQuestionVO normalUpdateQuestionVO) throws Exception {
		//1. DB에 등록
			
				
				int no= NormalUploadQuestionDao.testquestionSequece();
			int category_no=NormalUploadQuestionDao.categorySequence();
				
			int test_no = NormalUploadQuestionDao.testSequece();
				
			
			TestDto testDto=TestDto.builder()
					.tno(test_no)
					.test_category(normalUpdateQuestionVO.getTest_category())////////////
					.build();
				
			CategoryDto categoryDto =CategoryDto.builder()
					.category_no(category_no)
					.csname(normalUpdateQuestionVO.getCsname())
					.test_no(test_no)
					.lim_hour(0)
					.lim_min(0)
					.build();
			
				TestQuestionDto testQuestionDto =TestQuestionDto.builder()
						.no(no)
//						.test_no(normalUpdateQuestionVO.getTest_question_no())//
						.csname(normalUpdateQuestionVO.getCsname())
						.category_no(normalUpdateQuestionVO.getCategory_no())
						.question(normalUpdateQuestionVO.getQuestion())
						.ispremium(0)
						.answer(normalUpdateQuestionVO.getAnswer())
						.correct(0)
						.incorrect(0)
						.dis1(normalUpdateQuestionVO.getDis1())
						.dis2(normalUpdateQuestionVO.getDis2())
						.dis3(normalUpdateQuestionVO.getDis3())
						.dis4(normalUpdateQuestionVO.getDis4())
						.dis5(normalUpdateQuestionVO.getDis5())
						.rate(0)
						.build();

				SolutionDto solutionDto =SolutionDto.builder()
					.solution(normalUpdateQuestionVO.getSolution())
					.question_no(no)
					.user_no(62)
						.build();
				NormalUploadQuestionDao.upload(testQuestionDto,categoryDto,testDto,solutionDto);
//				
//				2. 파일 업로드(문제 사진)
//				**********************************************************************/
//				(1)DB에 등록
//				VO에서 받아온 내용을 DTO에 옮긴다.
				List<UploadTestQuestionFileDto> list = new ArrayList<>();
			for(MultipartFile mf : normalUpdateQuestionVO.getFile()) {
					list.add(
							UploadTestQuestionFileDto.builder()
							.file_save_name(UUID.randomUUID().toString())
							.file_upload_name(mf.getOriginalFilename())
							.file_type(mf.getContentType())
							.file_size(mf.getSize())
							.test_question_no(no)
							.build()
							
							);
				}		
				//새 디렉토리 생성
				File dir = new File("D:/upload/normalquestion_image");
				dir.mkdir();
				
				for(int i=0;i<list.size();i++) {
					MultipartFile mf = normalUpdateQuestionVO.getFile().get(i);
				UploadTestQuestionFileDto uploadTestQuestionFileDto = list.get(i);
				NormalUploadQuestionDao.fileUpload(uploadTestQuestionFileDto);
					File target = new File(dir, uploadTestQuestionFileDto.getFile_save_name());
				mf.transferTo(target);
				}
		
	}
	

}
