package com.kh.finalproject.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@NoArgsConstructor@AllArgsConstructor@Builder
public class UpdateQuestionVO {
	//���� ���ε�
	private int question_no;//�ϱ� 2 ���̺� �ܷ�Ű�� ���� ����.
	private int user_custom_question_no; 
	private String question_title; 
	private String question_content; 
	private String question_answer; 
	private String question_solution;
	private int question_premium; 
	private String answer1;
	private String answer2;
	private String answer3;
	private String answer4;
	private String answer5;
/*************************************************/
	//user_costom_question
	private int user_no;//���� �ѹ�
	private String category_name;
/*************************************************/
	private int question_file_no;//��ȣ(�⺻Ű)
	private String file_upload_name;//�ø��̸�
	private String file_save_name;//�����̸�
	private String file_type;//��������
	private long file_size;//����ũ��
	private List<MultipartFile> file;
}
