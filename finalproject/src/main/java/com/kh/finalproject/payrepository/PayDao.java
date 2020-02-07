package com.kh.finalproject.payrepository;

import com.kh.finalproject.payentity.PayDto;

public interface PayDao {

	void insertReady(PayDto payDto);

	void insertSuccess(PayDto payDto);

}
