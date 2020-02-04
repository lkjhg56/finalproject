package com.kh.finalproject.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.CertDto;

public class CertDaoImpl implements CertDao{
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public void regist(CertDto certDto) {
		sqlSession.insert("cert.regist", certDto);
		
	}

	@Override
	public boolean check(String email, String cert) {

		// select * from cert where email = ? and cert_no = ?
		
		Map<String, Object> param = new HashMap<>();
		param.put("email", email);
		param.put("cert_no", cert);
		
//		CertDto certDto = sqlSession.selectOne("cert.", ? );
		CertDto certDto = sqlSession.selectOne("cert.check", param );
		
		return certDto != null;
	}

	@Override
	public void delete(String email) {
		sqlSession.delete("cert.delete", email);
	}

}
