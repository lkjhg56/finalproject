package com.kh.finalproject.payrestcontroller;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.kh.finalproject.payentity.PayDto;
import com.kh.finalproject.payrepository.PayDao;
import com.kh.finalproject.payvo.KakaoPayRevokeReturnVO;
import com.kh.finalproject.payvo.TempVO;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/paycheck")
@Slf4j
public class KakaoPayRestController {

	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private PayDao payDao;
	
	@PostMapping("confirm")
	public void confirm(@RequestParam int num, int point, String id) {
		log.info("testest={}", point);
		TempVO tempVo = TempVO.builder()
													.id(id)
													.point(point)
													.build();
		sqlSession.update("pay.updatePoint", tempVo);
		sqlSession.update("pay.confirmup", num);


	}
	@PostMapping("cancel")
	public KakaoPayRevokeReturnVO cancel(@RequestParam int num) throws URISyntaxException {
		PayDto payDto = payDao.get(num);
		
		RestTemplate template = new RestTemplate();
		
		HttpHeaders header = new HttpHeaders();
		header.add("Authorization", "KakaoAK 80a1800fa42c30d9191e0af0f2c2c97d");
		header.add("Content-Type", "application/x-www-form-urlencoded; charset = UTF-8"); 
		header.add("accept", "application/json; charset=UTF-8");
		
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("cid", payDto.getCid());
		body.add("tid", payDto.getTid());
		body.add("cancel_amount", String.valueOf(payDto.getTotal_amount()));
		body.add("cancel_tax_free_amount", "0");
//		body.add("cancel_vat_amount", "");
		body.add("cancel_available_amount", String.valueOf(payDto.getTotal_amount()));
		
		HttpEntity<MultiValueMap<String, String>> entity= new HttpEntity<>(body, header);
		URI uri = new URI("https://kapi.kakao.com/v1/payment/cancel");
	
		
		KakaoPayRevokeReturnVO returnVO=
				template.postForObject(uri, entity, KakaoPayRevokeReturnVO.class);
		
		
		PayDto payDto2=PayDto.builder()
											.aid(returnVO.getAid())
											.cid(returnVO.getCid())
											.tid(returnVO.getTid())
											.partner_order_id(returnVO.getPartner_order_id())
											.partner_user_id(returnVO.getPartner_user_id())
											.created_at(returnVO.getCreated_at())
											.item_name(returnVO.getItem_name())
											.quantity(returnVO.getQuantity())
											.total_amount(-1*returnVO.getCanceled_amount().getTotal())
											.status("취소")
										.build();
		
		payDao.insertRevoke(payDto2);
		
		
		return returnVO;
		
	}
	
}
