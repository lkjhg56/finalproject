package com.kh.finalproject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.kh.finalproject.payvo.KakaoPayReadyReturnVO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayTest01 {

	@Test
	public void pay() throws URISyntaxException {
		RestTemplate template = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK 3edde6682465edef6d3f9b7108d9b782");
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", "TC0ONETIME");
		body.add("partner_order_id", "1");
		body.add("partner_user_id", "user");
		body.add("item_name", "sample");
		body.add("quantity", "1");
		body.add("total_amount", "1000");
		body.add("tax_free_amount", "0");
		body.add("approval_url", "http://localhost:8080/finalproject/pay/success");
		body.add("fail_url", "http://localhost:8080/finalproject/pay/fail");
		body.add("cancel_url", "http://localhost:8080/finalproject/pay/cancel");
		
		HttpEntity<MultiValueMap<String, String>> entity
					= new HttpEntity<>(body, headers);
	URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");
	KakaoPayReadyReturnVO vo = template.postForObject(uri, entity, KakaoPayReadyReturnVO.class);
		
	}
	
	
}
