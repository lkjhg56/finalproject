package com.kh.finalproject.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.kh.finalproject.entity.UploadQuestionDto;
import com.kh.finalproject.entity.UploadQuestionFileDto;
import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.vo.ExamResultVO;
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
					UploadQuestionFileDto delete = uploadQuestionDao.getFile(updateQuestionVO.getQuestion_no());
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
	public void questionDelete(int question_no,int user_custom_question_no) {
		UploadQuestionFileDto uploadQuestionFileDto=uploadQuestionDao.getFile(question_no);
		uploadQuestionDao.fileDelete2(question_no,user_custom_question_no,uploadQuestionFileDto.getQuestion_file_no());
		UploadQuestionFileDto delete = uploadQuestionDao.getFile(question_no);
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
		 System.out.println(updateQuestionVO.getId()); 
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
				.question_true(uploadQuestionDao.question_true(updateQuestionVO.getQuestion_no()))
				.question_false(uploadQuestionDao.question_false(updateQuestionVO.getQuestion_no()))
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
	@Override
	public ResponseEntity<ByteArrayResource> downloadImg(int question_no) throws Exception {
		UploadQuestionFileDto uploadQuestionFileDto = uploadQuestionDao.getFile(question_no);
		File directory = new File("D:/upload/question_image");
		//directory의 위치에 있는 profile_no란 이름의 파일을 찾아서 불러온 뒤 반환
		
		File file = new File(directory, String.valueOf(uploadQuestionFileDto.getFile_save_name()));
		if(!file.exists()) {
			return ResponseEntity.notFound().build();
		}
		byte[] data = FileUtils.readFileToByteArray(file);

		ByteArrayResource resource = new ByteArrayResource(data);
		
		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(uploadQuestionFileDto.getFile_size())
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						uploadQuestionDao.makeDispositionString(uploadQuestionFileDto))
				.body(resource);
	}
	@Override
	public List<UploadQuestionDto> multiQuestion(int wantQuestion) {
		Random r = new Random();
		List<UploadQuestionDto> list = uploadQuestionDao.getList();
		List<UploadQuestionDto> choice_list = new ArrayList<>();
		while(choice_list.size()<wantQuestion) {
			UploadQuestionDto uploadQuestionDto = list.get(r.nextInt(list.size()));
			if (!choice_list.contains(uploadQuestionDto)) {
				choice_list.add(uploadQuestionDto);
			}else {
				uploadQuestionDto =list.get(r.nextInt(list.size()));
			}
		}
		return choice_list;
	}
	@Override
	public List<UserQuestionResultDto> checkMulti(ExamResultVO examResultVO) {
		List<UserQuestionResultDto> list = new ArrayList<>();
		for(ExamResultVO vo : examResultVO.getQuestion()) {
			list.add(UserQuestionResultDto.builder()
					.question_no(vo.getNo())
					.question_answer(vo.getAnswer())
					.tried_user(vo.getId())
					.build());				
		}
		
		//정답여부, 정답률을 체크해준다. question_no로 원래 답을 호출하여 위 리스트내에
		for(int i = 0;i<list.size();i++ ) {
			int answer = list.get(i).getQuestion_answer();
			UploadQuestionDto uploadQuestionDto = uploadQuestionDao.isCorrect(list.get(i).getQuestion_no());
			//유저가 선택한 정답
			//정답여부 판별
			boolean result = uploadQuestionDto.getQuestion_answer() == answer;
			if(answer==0) {
				list.get(i).setResult(0);
			}else if(result){
				list.get(i).setResult(1);
			}else if(!result) {
				list.get(i).setResult(0);
			}
		}
		return list;
	}
}
