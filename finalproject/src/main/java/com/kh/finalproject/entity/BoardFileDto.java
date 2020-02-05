package com.kh.finalproject.entity;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class BoardFileDto {
	private int	board_file_no, 
						board_origin_content_no;
	
	private long board_file_size;
	
	private String board_file_upload_name, 
							board_file_save_name, 
							board_file_type;

}
