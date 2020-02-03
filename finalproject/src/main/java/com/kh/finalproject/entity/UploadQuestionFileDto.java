package com.kh.finalproject.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UploadQuestionFileDto{
	//question_file
	private int question_file_no;//번호(기본키)
	private String file_upload_name;//올린이름
	private String file_save_name;//실제이름
	private String file_type;//파일유형
	private long file_size;//파일크기
	private int question_no;
	private List<MultipartFile> file;
}
