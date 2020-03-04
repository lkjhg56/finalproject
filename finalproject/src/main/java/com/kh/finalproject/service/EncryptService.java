package com.kh.finalproject.service;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

public interface EncryptService {
	String AES256Encrypt(String origin, String offset) throws UnsupportedEncodingException, GeneralSecurityException;
	String AES256Decrypt(String origin, String offset) throws GeneralSecurityException;
}
