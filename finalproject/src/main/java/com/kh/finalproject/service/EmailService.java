package com.kh.finalproject.service;

public interface EmailService {

	String sendCertMessage(String email, String cert);
	void sendChangePasswordMail(String email) throws Exception;
	
	String sendEmail(String email);

}
