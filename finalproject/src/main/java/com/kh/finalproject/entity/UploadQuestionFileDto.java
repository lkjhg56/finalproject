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
	private int question_file_no;
	private String file_upload_name;
	private String file_save_name;
	private String file_type;
	private long file_size;
	private int question_no;
	private List<MultipartFile> file;
}
