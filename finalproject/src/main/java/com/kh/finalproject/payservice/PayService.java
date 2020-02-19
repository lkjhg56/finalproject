package com.kh.finalproject.payservice;

import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.kh.finalproject.payvo.KakaoPaySuccessReadyVO;
import com.kh.finalproject.payvo.KakaoPaySuccessReturnVO;
import com.kh.finalproject.payvo.PayReadyReturnVO;
import com.kh.finalproject.payvo.PayReadyVO;


@Service
public interface PayService{
	PayReadyReturnVO ready(PayReadyVO vo) throws URISyntaxException;


	KakaoPaySuccessReturnVO approve(KakaoPaySuccessReadyVO data, int num1) throws URISyntaxException;
}
