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
import com.kh.finalproject.entity.UserQuestionMultiResultDto;
import com.kh.finalproject.entity.UserQuestionResultDto;
import com.kh.finalproject.entity.UsersDto;
import com.kh.finalproject.repository.UploadQuestionDao;
import com.kh.finalproject.repository.UsersDao;
import com.kh.finalproject.vo.ExamResultVO;
import com.kh.finalproject.vo.UpdateQuestionVO;

@Service
public class UploadQuestionSeviceImpl implements UploadQuestionService {

	@Autowired
	private UploadQuestionDao uploadQuestionDao;
	@Autowired
	private UsersDao usersDao;
	//문제 업로드 C
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
		//파일 없을 경우		
		//2. 파일 업로드(문제 사진)
		/**********************************************************************/
		if(!updateQuestionVO.getFile().get(0).isEmpty()) {
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
	}
	//문제 수정 U
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
		
		//업데이트시 이미지파일이 있는 채로 수정을 원하는 경우
		if(!updateQuestionVO.getFile().get(0).isEmpty()) {
			//question_file 테이블 변경 전 파일 세부사항을 리스트에 옮김.(파일 개수가 몇개던 상관 없음.)
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
			//경로에 폴더 생성
			String Path="D:/upload/question_image";
			File dir = new File(Path);
			dir.mkdir();
			
			//파일 수정
			//기존 DB 불러오기
			List<UploadQuestionFileDto> delete = 
					uploadQuestionDao.getFile2(updateQuestionVO.getQuestion_no());
			//기존 파일 및 DB가 없는 경우 update가 아닌 새로운 data를 insert해줘야함.
			if(!delete.isEmpty()) {
				//기존 파일 및 DB가 있는 경우	
				//기존 파일과 DB를 전량 삭제
				for(int j = 0;j<delete.size();j++) {
					String filepath = "D:/upload/question_image/"+delete.get(j).getFile_save_name();		
					File file = new File(filepath);
					//실제 파일 삭제
					file.delete();
					//DB 내용 삭제
					uploadQuestionDao.deleteFile(delete.get(j).getQuestion_file_no());
					}
			}	
			//그리고 받아온 파일과 DB를 전량 부어넣는다.
			for(int j = 0;j<list.size();j++ ) {
				UploadQuestionFileDto dto = list.get(j);
				uploadQuestionDao.fileUpload(dto);
				File target = new File(dir, dto.getFile_save_name());
				updateQuestionVO.getFile().get(j).transferTo(target);
			}
		}
	}		
	
	//문제 삭제 D
	@Override
	public void questionDelete(int question_no, int user_custom_question_no) {
		//삭제할 파일 정보를 불러온다.
		//여러개가 있을경우도 있으니 리스트로 받아와야 한다.
		List<UploadQuestionFileDto> delete = uploadQuestionDao.getFile2(question_no);
		if(delete != null) {
			for(int i = 0;i<delete.size();i++) {
				String filepath = "D:/upload/question_image/"+delete.get(i).getFile_save_name();
				File file = new File(filepath);
				file.delete();				
			}
		}
		//데이터베이스 삭제
		uploadQuestionDao.fileDelete2(question_no, user_custom_question_no);			
	}
	//단일 문제 해결 R
	@Override
	public UserQuestionResultDto questionSolve(UpdateQuestionVO updateQuestionVO) {
		//조회수 증가 기능.
		uploadQuestionDao.update_read_count(updateQuestionVO.getQuestion_no());
		UploadQuestionDto uploadQuestionDto = uploadQuestionDao.question_all(updateQuestionVO.getQuestion_no());
		String time=uploadQuestionDao.timeCheck();
		String result_time = updateQuestionVO.getHour()+":"+updateQuestionVO.getMin()+":"
							 +updateQuestionVO.getSec()+":"+updateQuestionVO.getMilisec();
		int question_result_no=uploadQuestionDao.questionResultSequece();
		//정답률 업데이트
		uploadQuestionDao.correct_ratio(updateQuestionVO.getQuestion_no());
			
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
//			UsersDto usersDto = usersDao.getInfo(updateQuestionVO.getId());
//			uploadQuestionDao.givePointforSolving(usersDto.getUser_no());
		}else {
			userQuestionResultDto.setResult(0);
		}
		uploadQuestionDao.insert_result(userQuestionResultDto);
		UserQuestionResultDto resultDto = uploadQuestionDao.userPriority(updateQuestionVO.getQuestion_no(), question_result_no);
		userQuestionResultDto.setUser_priority(resultDto.getUser_priority());
		return userQuestionResultDto;
		
	}
	//저장된 이미지 호출 R
	@Override
	public ResponseEntity<ByteArrayResource> downloadImg(int question_file_no) throws Exception {
		UploadQuestionFileDto uploadQuestionFileDto = uploadQuestionDao.getFile3(question_file_no);
		File directory = new File("D:/upload/question_image");
		//directory의 위치에 있는 profile_no란 이름의 파일을 찾아서 불러온 뒤 반환
		File file = new File(directory, String.valueOf(uploadQuestionFileDto.getFile_save_name()));
//		if(!file.exists()) {
//			return ResponseEntity.notFound().build();
//		}
		byte[] data = FileUtils.readFileToByteArray(file);
		ByteArrayResource resource = new ByteArrayResource(data);
		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.contentLength(uploadQuestionFileDto.getFile_size())
				.header(HttpHeaders.CONTENT_ENCODING, "UTF-8")
				.header(HttpHeaders.CONTENT_DISPOSITION, 
						uploadQuestionDao.makeDispositionString(uploadQuestionFileDto))
				.body(resource);
	}
	//저장된 문제 여러개 호출 및 랜덤으로 출제 R
	@Override
	public List<UploadQuestionDto> multiQuestion(int wantQuestion) {

		Random r = new Random();
		List<UploadQuestionDto> list = uploadQuestionDao.getListWithImage();
		List<UploadQuestionDto> choice_list = new ArrayList<>();
		while(choice_list.size()<wantQuestion) {
			UploadQuestionDto uploadQuestionDto = list.get(r.nextInt(list.size()));
			if (!choice_list.contains(uploadQuestionDto)) {
				choice_list.add(uploadQuestionDto);
			}else {
				uploadQuestionDto =list.get(r.nextInt(list.size()));
			}
		}
//		확인용
		for(int i = 0 ;i<choice_list.size();i++) {
			for(int j = 0;j<choice_list.get(i).getFiles().size();j++) {				
			}
		}

		return choice_list;
	}
	//다중문제 해결한 결과값 출력 R
	@Override
	public List<UserQuestionResultDto> checkMulti(ExamResultVO examResultVO) {
		List<UserQuestionResultDto> list = new ArrayList<>();
		for(ExamResultVO vo : examResultVO.getQuestion()) {
			list.add(UserQuestionResultDto.builder()
					.question_no(vo.getNo())//문제 번호
					.question_answer(vo.getAnswer())//사용자가 적은 답
					.tried_user(vo.getId())//수험자
					.build());			
		}		
		//정답여부를 체크해준다. question_no로 원래 답을 호출하여 위 리스트내에
		for(int i = 0;i<list.size();i++ ) {
			int answer = list.get(i).getQuestion_answer();
			UploadQuestionDto uploadQuestionDto = uploadQuestionDao.isCorrect(list.get(i).getQuestion_no());
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
	//다중 문제 풀이 결과값 저장.
	@Override
	public UserQuestionMultiResultDto insert_multi(List<UserQuestionResultDto> list, UserQuestionResultDto userQuestionResultDto) {
		//다음은 결과값(본 시험의 총점, 정답률, 문제 개수 등)을 취합하여 DB내에 question_multi_result 테이블을 생성하여 insert해줘야함.
		int total_question = list.size();//문제 총 개수
		int correct_count = 0;//맞은 개수
		String result_time = userQuestionResultDto.getHour()+":"+userQuestionResultDto.getMin()+":"
				 +userQuestionResultDto.getSec()+":"+userQuestionResultDto.getMilisec();
		for(int i=0;i<total_question;i++) {
			//맞은 개수를 count해줘야함.
			boolean correct = list.get(i).getResult()==1;
			if(correct) {
				correct_count++;
			}
		}
		UserQuestionMultiResultDto multi_dto = UserQuestionMultiResultDto.builder()
			.user_no(uploadQuestionDao.getUserNo(list.get(0).getTried_user()))
			.total_question(total_question)
			.correct_count(correct_count)
			.incorrect_count(total_question-correct_count)
			.sum_score((100/total_question) * correct_count)//소수점 2자리까지
			.result_time(result_time)
			.build();
		uploadQuestionDao.insert_multi_result(multi_dto);
		return multi_dto;
	}
}
