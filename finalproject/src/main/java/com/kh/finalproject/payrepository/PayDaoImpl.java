package com.kh.finalproject.payrepository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.payentity.PayDto;

public class PayDaoImpl implements PayDao{

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public void insertReady(PayDto payDto) {
		sqlSession.insert("pay.ready", payDto);
		
	}

	@Override
	public void insertSuccess(PayDto payDto) {
		sqlSession.insert("pay.success", payDto);
		
	}
	@Override
	public PayDto get(int num) {
			return sqlSession.selectOne("pay.get", num);
	}

	@Override
	public void insertRevoke(PayDto payDto2) {
		sqlSession.insert("pay.revoke", payDto2);
		
	}

	
	

}
