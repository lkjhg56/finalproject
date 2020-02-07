package com.kh.finalproject;

import java.net.URISyntaxException;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.finalproject.payservice.PayService;
import com.kh.finalproject.payvo.KakaoPayReadyReturnVO;
import com.kh.finalproject.payvo.KakaoPayReadyVO;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {
	"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"
})
@WebAppConfiguration
@Slf4j
public class PayTest02 {

	@Autowired
	private PayService payService;
	
	@Test
	public void test() throws URISyntaxException {
		
		KakaoPayReadyVO vo = KakaoPayReadyVO.builder()
				.partner_order_id(UUID.randomUUID().toString())
				.partner_user_id("흥")
				.item_name("비싼거")
				.quantity(2)
				.total_amount(500)
				.vat_amount(10)
				.build();
		
		KakaoPayReadyReturnVO result = (KakaoPayReadyReturnVO) payService.ready(vo);
		log.info("주소 = {}", result.getNext_redirect_pc_url());
	}

}