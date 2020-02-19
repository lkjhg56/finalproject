package com.kh.finalproject.payservice;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.finalproject.payentity.PayDto;
import com.kh.finalproject.payrepository.PayDao;
import com.kh.finalproject.payvo.KakaoPayReadyReturnVO;
import com.kh.finalproject.payvo.KakaoPaySuccessReadyVO;
import com.kh.finalproject.payvo.KakaoPaySuccessReturnVO;
import com.kh.finalproject.payvo.PayReadyReturnVO;
import com.kh.finalproject.payvo.PayReadyVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoPayService implements PayService{
	
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	private PayDao payDao; 
	
	@Override
	public PayReadyReturnVO ready(PayReadyVO vo) throws URISyntaxException {
		
		RestTemplate template = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK 3edde6682465edef6d3f9b7108d9b782");
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", "TC0ONETIME");
		body.add("partner_order_id", vo.getPartner_order_id());
		body.add("partner_user_id", vo.getPartner_user_id());
		body.add("item_name", vo.getItem_name());
		body.add("quantity", String.valueOf(vo.getQuantity()));
		body.add("total_amount", String.valueOf(vo.getTotal_amount()));
		body.add("tax_free_amount", String.valueOf(vo.getTax_free_amount()));
		
		
		String baseUrl = ServletUriComponentsBuilder
									.fromCurrentContextPath()
									.port(8080)
									.path("/pay/kakao/")
									.toUriString();
		
		
		body.add("approval_url", baseUrl+"success");
		body.add("fail_url", baseUrl+"fail");
		body.add("cancel_url", baseUrl+"cancel");
		
		HttpEntity<MultiValueMap<String, String>> entity
					= new HttpEntity<>(body, headers);
	URI uri = new URI("https://kapi.kakao.com/v1/payment/ready");
	KakaoPayReadyReturnVO returnVO = template.postForObject(uri, entity, KakaoPayReadyReturnVO.class);

	log.info("확인하는중");
	
	PayDto payDto=PayDto.builder()
									.cid("TC0ONETIME")
									.tid(returnVO.getTid())
									.partner_order_id(vo.getPartner_order_id())
									.partner_user_id(vo.getPartner_user_id())
									.created_at(returnVO.getCreated_at())
									.item_name(vo.getItem_name())
									.quantity(vo.getQuantity())
									.total_amount(vo.getTotal_amount())
									.status("준비")
									.build();
	
	payDao.insertReady(payDto);
	return returnVO;
	}

	@Override
	public KakaoPaySuccessReturnVO approve(KakaoPaySuccessReadyVO data, int num1) throws URISyntaxException {
		
		RestTemplate template = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "KakaoAK 3edde6682465edef6d3f9b7108d9b782");
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		headers.add("accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", data.getCid());
		body.add("tid", data.getTid());
		body.add("partner_order_id", data.getPartner_order_id());
		body.add("partner_user_id", data.getPartner_user_id());
		body.add("pg_token", data.getPg_token());
		
		HttpEntity<MultiValueMap<String, String>> entity
											= new HttpEntity<>(body, headers);
		
		URI uri = new URI("https://kapi.kakao.com/v1/payment/approve");
		
		KakaoPaySuccessReturnVO returnVO = 
				template.postForObject(uri, entity, KakaoPaySuccessReturnVO.class);
		

		
		PayDto payDto = PayDto.builder()
				.no(num1)
				.aid(returnVO.getAid())
				.cid("TC0ONETIME")
				.tid(returnVO.getTid())
				.partner_order_id(data.getPartner_order_id())
				.partner_user_id(data.getPartner_user_id())
				.created_at(returnVO.getCreated_at())
				.item_name(returnVO.getItem_name())
				.quantity(returnVO.getQuantity())
				.total_amount(returnVO.getAmount().getTotal())
				.status("완료")
				.build();
		
		payDao.insertSuccess(payDto);
		
		return returnVO;
	}

}
