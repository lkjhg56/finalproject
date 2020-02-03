package com.kh.finalproject.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public interface EncryptService {
	String ceasarEncrypt(String origin, int offset);	//암호화
	String ceasarDecrypt(String origin, int offset);	//복호화
	String xorEncrypt(String origin, int offset);		//xor 암호화
	
	String AES256Encrypt(String origin, String offset) throws UnsupportedEncodingException, GeneralSecurityException;
	String AES256Decrypt(String origin, String offset) throws GeneralSecurityException;
}
