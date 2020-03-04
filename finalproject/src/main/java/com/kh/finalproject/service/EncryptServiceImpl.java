package com.kh.finalproject.service;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Service;

@Service
public class EncryptServiceImpl implements EncryptService{

	@Override
	public String AES256Encrypt(String origin, String offset) throws GeneralSecurityException, UnsupportedEncodingException{
		SecureRandom sr = new SecureRandom();
		byte[] by = new byte[20];
		sr.nextBytes(by);	//랜덤으로 숫자를 넣으세요.
		
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		// 256bit의 키를  offset과 랜덤값인 by를 이용해서 넣는다. (반복 횟수 : 70000 / 결과물의 크기 :256)
		PBEKeySpec spec = new PBEKeySpec(offset.toCharArray(), by, 70000, 256);
		
		SecretKey secretKey = factory.generateSecret(spec);
		
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES"); 
		
		//암호화
		// CBC : Cipher Block Chaning mode
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(cipher.ENCRYPT_MODE, secret);
		
		AlgorithmParameters param = cipher.getParameters();
		
		//1단계 암호화 블록 생성
		byte[] initialBlock = param.getParameterSpec(IvParameterSpec.class).getIV();
		
		//텍스트 암호화
		byte[] encryptTextBlock = cipher.doFinal(origin.getBytes("UTF-8"));
		
		//결과를 합치는 과정
		// 결과물의 크기 = by의 길이 + initialBlock의 길이 + encryptTextBlock의 길이
		int len = by.length + initialBlock.length + encryptTextBlock.length;
		byte[] buffer = new byte[len];
		
		// by -> buffer (copy)
		System.arraycopy(by, 0, buffer, 0, by.length);
		// initialBlock -> buffer (copy)
		System.arraycopy(initialBlock, 0, buffer, by.length, initialBlock.length);
		// encryptTextBlock -> buffer (copy)
		System.arraycopy(encryptTextBlock, 0, buffer, by.length + initialBlock.length, encryptTextBlock.length);
		
		String result = Base64.getEncoder().encodeToString(buffer);
		return result;
	}

	@Override
	public String AES256Decrypt(String origin, String offset) throws GeneralSecurityException{
		//복원 객체 생성
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		//복구 작업 수행
		ByteBuffer buffer = ByteBuffer.wrap(Base64.getDecoder().decode(origin));
		
		//분해 항목 : by, initialBlock, encryptTextBlock
		byte[] by = new byte[20];
		buffer.get(by, 0, by.length);
		
		byte[] initialBlock = new byte[cipher.getBlockSize()];
		buffer.get(initialBlock, 0, initialBlock.length);
		
		int len = buffer.capacity()-by.length-initialBlock.length;
		byte[] encryptTextBlock = new byte[len];
		buffer.get(encryptTextBlock);
		
		//각각의 데이터 복구
		SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
		
		PBEKeySpec spec = new PBEKeySpec(offset.toCharArray(), by, 70000, 256);
		
		SecretKey secretKey = factory.generateSecret(spec);
		
		SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES"); 
		
		cipher.init(cipher.DECRYPT_MODE, secret, new IvParameterSpec(initialBlock));
		
		byte[] originTextBlock = cipher.doFinal(encryptTextBlock);
		String result = new String(originTextBlock);
		return result;
	}

}
