package com.kh.finalproject.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class UserFileDto {
	private int user_file_no;
	private int user_no;
	private String file_upload_name;
	private String file_save_name;
	private String file_type;
	private long file_size;
}
