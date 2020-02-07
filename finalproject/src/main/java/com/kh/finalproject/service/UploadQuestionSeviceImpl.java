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
import com.kh.finalproject.entity.UserQuestionResultDto;
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
		
		for(int i=0;i<list.size();i++) {
			MultipartFile mf = updateQuestionVO.getFile().get(i);
			UploadQuestionFileDto dto = list.get(i);
			uploadQuestionDao.fileUpload(dto);
			File target = new File(dir, dto.getFile_save_name());
			mf.transferTo(target);
		}
	}
	//문제 수정
	@Override
	public void questionUpdate(UpdateQuestionVO updateQuestionVO) throws Exception {

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
				.question_no(updateQuestionVO.getQuestion_no())
				.user_custom_question_no(updateQuestionVO.getUser_custom_question_no())
				.build();
		uploadQuestionDao.updateQustion(uploadQuestionDto);

			//question_file 테이블 변경	
			List<UploadQuestionFileDto> list = new ArrayList<>();
			for(MultipartFile mf : updateQuestionVO.getFile()) {
				if(!mf.isEmpty()) {
					list.add(UploadQuestionFileDto.builder()
							.file_save_name(UUID.randomUUID().toString())
							.file_upload_name(mf.getOriginalFilename())
							.file_type(mf.getContentType())
							.file_size(mf.getSize())
							.question_no(updateQuestionVO.getQuestion_no())
							.build());
				}
			}		
			//변경된 파일을 다시 저장.	
			String Path="D:/upload/question_image";
			File dir = new File(Path);
			dir.mkdir();

			for(int i=0;i<list.size();i++) {				
					MultipartFile mf = updateQuestionVO.getFile().get(i);
					if(!mf.isEmpty()) {
					//기존 파일의 save와 같으면 삭제하지 않고 틀리면 삭제. 기존 파일은 DB내에서 문제NO를 이용하여 검색하여 붙여야함.
					UploadQuestionFileDto delete = uploadQuestionDao.fileDelete(updateQuestionVO.getQuestion_no());
					String filepath = "D:/upload/question_image/"+delete.getFile_save_name();		
					File file = new File(filepath);
					file.delete();
					/*******************************************************/
					UploadQuestionFileDto dto = list.get(i);
					uploadQuestionDao.updateFile(dto);
					File target = new File(dir, dto.getFile_save_name());
					mf.transferTo(target);
					}
			}
			
	}
	public void questionDelete(int question_no, int user_custom_question_no) {	
		uploadQuestionDao.fileDelete2(question_no,user_custom_question_no);
		UploadQuestionFileDto delete = uploadQuestionDao.fileDelete(question_no);
		String filepath = "D:/upload/question_image/"+delete.getFile_save_name();
		File file = new File(filepath);
		file.delete();
	}
	@Override
	public UserQuestionResultDto questionSolve(UpdateQuestionVO updateQuestionVO) {
		UploadQuestionDto uploadQuestionDto = uploadQuestionDao.question_all(updateQuestionVO.getQuestion_no());
		String time=uploadQuestionDao.timeCheck();
		String result_time = updateQuestionVO.getHour()+":"+updateQuestionVO.getMin()+":"
							 +updateQuestionVO.getSec()+":"+updateQuestionVO.getMilisec();
		int question_result_no=uploadQuestionDao.questionResultSequece();
		UserQuestionResultDto userQuestionResultDto = UserQuestionResultDto.builder()
				.hour(updateQuestionVO.getHour())
				.min(updateQuestionVO.getMin())
				.sec(updateQuestionVO.getSec())
				.milisec(updateQuestionVO.getMilisec())
				.question_answer(uploadQuestionDto.getQuestion_answer())
				.result_no(question_result_no)
				.user_conclusion(updateQuestionVO.getQuestion_answer())
				.result_time(result_time)
				.tried_user(updateQuestionVO.getId())
				.solveDate(time)
				.question_no(updateQuestionVO.getQuestion_no())
				.question_true(uploadQuestionDao.question_true())
				.question_false(uploadQuestionDao.question_false())
				.build();
		boolean result=updateQuestionVO.getQuestion_answer()==uploadQuestionDto.getQuestion_answer();		
		if(result) {
			userQuestionResultDto.setResult(1);
		}else {
			userQuestionResultDto.setResult(0);
		}
		uploadQuestionDao.insert_result(userQuestionResultDto);
		userQuestionResultDto.setUser_priority(uploadQuestionDao.userPriority(updateQuestionVO.getQuestion_no(), question_result_no));
		return userQuestionResultDto;
		
	}
}
