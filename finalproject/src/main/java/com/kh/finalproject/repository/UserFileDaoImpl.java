package com.kh.finalproject.repository;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.UserFileDto;

public class UserFileDaoImpl implements UserFileDao{

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void fileUpload(UserFileDto userFileDto) {
		sqlSession.insert("users.join_file", userFileDto);		
	}

	@Override
	public UserFileDto getFile(int user_no) {
		return sqlSession.selectOne("users.getFileOne", user_no);
	}

	@Override
	public String makeDispositionString(UserFileDto userFileDto) throws UnsupportedEncodingException {
		StringBuffer buffer = new StringBuffer();
		buffer.append("attachment;");
		buffer.append("filename=");
		buffer.append("\"");
		buffer.append(URLEncoder.encode(userFileDto.getFile_upload_name(), "UTF-8"));
		buffer.append("\"");
		return buffer.toString();
	}

	@Override
	public void delete(int user_no) {
		sqlSession.delete("users.deleteFile", user_no);
	}
	
}
