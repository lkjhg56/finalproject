package com.kh.finalproject.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
			
		int test_no = NormalUploadQuestionDao.testSequece();
		int category_no=NormalUploadQuestionDao.categorySequence();
				
				int no= NormalUploadQuestionDao.testquestionSequece();
				
				int categoryExist=NormalUploadQuestionDao.categoryExist(normalUpdateQuestionVO);
	
	int testExist=NormalUploadQuestionDao.testExist(normalUpdateQuestionVO);
				if(categoryExist>0) {
					
					
			
						TestQuestionDto testQuestionDto =TestQuestionDto.builder()
								.no(no)
//								.test_no(normalUpdateQuestionVO.getTest_question_no())//
								.csname(normalUpdateQuestionVO.getCsname())//수정!!
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
							///문제당시간
								.build();

						
						
						
						SolutionDto solutionDto =SolutionDto.builder()
							.solution(normalUpdateQuestionVO.getSolution())
							.question_no(no)
							.user_no(101)
								.build();
						
					
					
					NormalUploadQuestionDao.upload2(testQuestionDto, solutionDto);
				}
				
				else if(testExist>0) {
					
					CategoryDto categoryDto =CategoryDto.builder()
							.category_no(category_no)
							.csname(normalUpdateQuestionVO.getCsname())
							.test_no(testExist)
							.lim_hour(0)
							.lim_min(10)
							.build();
					
						TestQuestionDto testQuestionDto =TestQuestionDto.builder()
								.no(no)
//								.test_no(normalUpdateQuestionVO.getTest_question_no())//
								.csname(normalUpdateQuestionVO.getCsname())//수정!!
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
							.user_no(101)
								.build();
						
						NormalUploadQuestionDao.upload3(categoryDto, testQuestionDto, solutionDto);
				}
					
					
				else {
					
					TestDto testDto=TestDto.builder()
							.tno(test_no)
							.test_category(normalUpdateQuestionVO.getTest_category())////////////
							.build();
						
					CategoryDto categoryDto =CategoryDto.builder()
							.category_no(category_no)
							.csname(normalUpdateQuestionVO.getCsname())
							.test_no(test_no)
							.lim_hour(0)
							.lim_min(10)
							.build();
					
						TestQuestionDto testQuestionDto =TestQuestionDto.builder()
								.no(no)
//								.test_no(normalUpdateQuestionVO.getTest_question_no())//
								.csname(normalUpdateQuestionVO.getCsname())//수정!!
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
							.user_no(101)
								.build();
						
					
					
					NormalUploadQuestionDao.upload(testQuestionDto,categoryDto,testDto,solutionDto);
					
				}
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
				
				
				
				if(!uploadTestQuestionFileDto.getFile_upload_name().isEmpty()) {
					
					NormalUploadQuestionDao.fileUpload(uploadTestQuestionFileDto);
					File target = new File(dir, uploadTestQuestionFileDto.getFile_save_name());
					mf.transferTo(target);
				}
				}
		
	}

	@Override
	public ResponseEntity<ByteArrayResource> downloadImg(int no) throws Exception {
		
		
		UploadTestQuestionFileDto uploadTestQuestionFileDto=NormalUploadQuestionDao.getFile(no);
		File directory = new File("D:/upload/normalquestion_image");
		//directory의 위치에 있는 profile_no란 이름의 파일을 찾아서 불러온 뒤 반환
		
		File file = new File(directory, String.valueOf(uploadTestQuestionFileDto.getFile_save_name()));
		
		
		byte[] data = FileUtils.readFileToByteArray(file);

		ByteArrayResource resource = new ByteArrayResource(data);
		
		return ResponseEntity.ok()
				//.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(uploadTestQuestionFileDto.getFile_size())
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						NormalUploadQuestionDao.makeDispositionString(uploadTestQuestionFileDto)
						)
				.body(resource);
	}

	@Override
	public void normalquestionUpdate(NormalUpdateQuestionVO normalUpdateQuestionVO) throws Exception {
		//test_question 테이블 변경
		
		TestQuestionDto testQuestionDto=TestQuestionDto.builder()
				.no(normalUpdateQuestionVO.getNo())
				.csname(normalUpdateQuestionVO.getCsname())//수정!!
				.category_no(normalUpdateQuestionVO.getCategory_no())
				.question(normalUpdateQuestionVO.getQuestion())
				.ispremium(normalUpdateQuestionVO.getIspremium())
				.answer(normalUpdateQuestionVO.getAnswer())
				.correct(normalUpdateQuestionVO.getCorrect())
				.incorrect(normalUpdateQuestionVO.getIncorrect())
				.dis1(normalUpdateQuestionVO.getDis1())
				.dis2(normalUpdateQuestionVO.getDis2())
				.dis3(normalUpdateQuestionVO.getDis3())
				.dis4(normalUpdateQuestionVO.getDis4())
				.dis5(normalUpdateQuestionVO.getDis5())
				.rate(normalUpdateQuestionVO.getRate())
				.build();
		
	NormalUploadQuestionDao.updateQustion(normalUpdateQuestionVO);
	UploadTestQuestionFileDto fileo=	NormalUploadQuestionDao.getFile(testQuestionDto.getNo());
	
if(fileo==null) {
//	2. 파일 업로드(문제 사진)
//	**********************************************************************/
//	(1)DB에 등록
//	VO에서 받아온 내용을 DTO에 옮긴다.
	List<UploadTestQuestionFileDto> list = new ArrayList<>();
for(MultipartFile mf : normalUpdateQuestionVO.getFile()) {
		list.add(
				UploadTestQuestionFileDto.builder()
				.file_save_name(UUID.randomUUID().toString())
				.file_upload_name(mf.getOriginalFilename())
				.file_type(mf.getContentType())
				.file_size(mf.getSize())
				.test_question_no(normalUpdateQuestionVO.getNo())
				.build()
				
				);
	}		
	//새 디렉토리 생성
	File dir = new File("D:/upload/normalquestion_image");
	dir.mkdir();
	
	for(int i=0;i<list.size();i++) {
		MultipartFile mf = normalUpdateQuestionVO.getFile().get(i);
	UploadTestQuestionFileDto uploadTestQuestionFileDto = list.get(i);
	
	
	
	if(!uploadTestQuestionFileDto.getFile_upload_name().isEmpty()) {
		
		NormalUploadQuestionDao.fileUpload(uploadTestQuestionFileDto);
		File target = new File(dir, uploadTestQuestionFileDto.getFile_save_name());
		mf.transferTo(target);
	}
	}

	
	
	
}
else {
	//test_question_file 테이블 변경	
	List<UploadTestQuestionFileDto> list = new ArrayList<>();
	for(MultipartFile mf : normalUpdateQuestionVO.getFile()) {
		if(!mf.isEmpty()) {
			list.add(UploadTestQuestionFileDto.builder()
					.file_save_name(UUID.randomUUID().toString())
					.file_upload_name(mf.getOriginalFilename())
					.file_type(mf.getContentType())
					.file_size(mf.getSize())
					.test_question_no(normalUpdateQuestionVO.getNo())
					.build()); 
		}
	}		
	//변경된 파일을 다시 저장.	
	String Path="D:/upload/normalquestion_image";
	File dir = new File(Path);
	dir.mkdir();

	for(int i=0;i<list.size();i++) {				
		MultipartFile mf = normalUpdateQuestionVO.getFile().get(i);
		
		if(!mf.isEmpty()) {
			//기존 파일의 save와 같으면 삭제하지 않고 틀리면 삭제. 기존 파일은 DB내에서 문제NO를 이용하여 검색하여 붙여야함.
			UploadTestQuestionFileDto	 delete = 
					NormalUploadQuestionDao.getFile(normalUpdateQuestionVO.getNo());
			
			
			
			if(delete!=null) {	
				String filepath = "D:/upload/normalquestion_image/"+delete.getFile_save_name();		
				File file = new File(filepath);
				file.delete();
				
				UploadTestQuestionFileDto dto = list.get(i);
				NormalUploadQuestionDao.updateFile(dto);
				File target = new File(dir, dto.getFile_save_name());
				mf.transferTo(target);
			}
			/*******************************************************/
		}
}

	
}

	
	
	}

	@Override
	public void questionDelete(int no,String csname) {
		//파일여부
		//no로 파일 구하기
		
		
		
		UploadTestQuestionFileDto	 delete = 
				NormalUploadQuestionDao.getFile(no);
		
	if(delete!=null) {
		
		String filepath = "D:/upload/normalquestion_image/"+delete.getFile_save_name();
		File file = new File(filepath);
		file.delete();
		
	}	
		
	
		
		NormalUploadQuestionDao.fileDelete2(no,csname);
	}
	

}
