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
		//�������� �̸� �̾Ƽ� ����.
		int seq= uploadQuestionDao.questionSequece();
		
		UploadQuestionDto uploadQuestionDto = UploadQuestionDto.builder()
				.question_no(seq)
				.question_title(updateQuestionVO.getQuestion_title())
				.user_custom_question_no(updateQuestionVO.getUser_custom_question_no())
				.question_content(updateQuestionVO.getQuestion_content())
				.question_answer(updateQuestionVO.getQuestion_answer())
				.question_solution(updateQuestionVO.getQuestion_solution())
				.user_no(updateQuestionVO.getUser_no())
				.category_name(updateQuestionVO.getCategory_name())
				.answer1(updateQuestionVO.getAnswer1())
				.answer2(updateQuestionVO.getAnswer2())
				.answer3(updateQuestionVO.getAnswer3())
				.answer4(updateQuestionVO.getAnswer4())
				.answer5(updateQuestionVO.getAnswer5())				
				.build();

		uploadQuestionDao.upload(uploadQuestionDto);
		
		//2. ���� ���ε�
		/**********************************************************************/
		//(1) ���� ���� ����		
		//VO���� ���� ���� ������ list�� �ӽ� ����
		List<UploadQuestionFileDto> list = new ArrayList<>();
		for(MultipartFile mf : updateQuestionVO.getFile()) {
			list.add(UploadQuestionFileDto.builder()
				.file_save_name(UUID.randomUUID().toString())
				.file_upload_name(mf.getOriginalFilename())
				.file_type(mf.getContentType())
				.file_size(mf.getSize())				
				.build());			
		}
		
		//������ ��� ����
		File dir = new File("D:/upload/question_image");
		dir.mkdir();
		
		for(int i=0;i<list.size();i++) {
			MultipartFile mf = updateQuestionVO.getFile().get(i);//���� ���� �����ϱ�
			UploadQuestionFileDto dto = list.get(i);
			File target = new File(dir, dto.getFile_save_name());
			mf.transferTo(target);
			uploadQuestionDao.fileUpload(dto);
		}
		/**********************************************************************/
		//(2) DB�� �Է�
		UploadQuestionFileDto uploadQuestionFileDto = UploadQuestionFileDto.builder()
				.question_no(seq)//�̸� ���� ������ �ֱ�
				.file_upload_name(updateQuestionVO.getFile_upload_name())
				.file_save_name(updateQuestionVO.getFile_save_name())
				.file_type(updateQuestionVO.getFile_type())
				.file_size(updateQuestionVO.getFile_size())
				.build();
		uploadQuestionDao.fileUpload(uploadQuestionFileDto);

	}
}
