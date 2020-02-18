package com.kh.finalproject.paycontroller;

import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.finalproject.payentity.PayListDto;
import com.kh.finalproject.payservice.PayService;
import com.kh.finalproject.payvo.KakaoPayReadyVO;
import com.kh.finalproject.payvo.KakaoPaySuccessReadyVO;
import com.kh.finalproject.payvo.KakaoPaySuccessReturnVO;
import com.kh.finalproject.payvo.PayReadyReturnVO;
import com.kh.finalproject.payvo.PaymentInfoVO;
import com.kh.finalproject.payvo.TempVO;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pay/kakao")
@Slf4j
public class KakaoPayController {
	
	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	private PayService payService;
	
	
	@PostMapping("payment")
	public String payment(@ModelAttribute PaymentInfoVO vo, HttpSession session) throws URISyntaxException {
		log.info("voinfo = {}", vo);
		int orderNum = sqlSession.selectOne("orderNum");
		
		KakaoPayReadyVO readyVo = KakaoPayReadyVO.builder()
																					.partner_order_id(String.valueOf(orderNum))
																					.partner_user_id(vo.getPartner_user_id())
																					.item_name(vo.getItem_name())
																					.total_amount(vo.getTotal_amount())
																					.tax_free_amount(vo.getTax_free_amount())
																					.quantity(vo.getQuantity())
																					.build();
		log.info("ordernum = {}", readyVo.getPartner_order_id());
		PayReadyReturnVO result = payService.ready(readyVo);
		session.setAttribute("tid", result.getTid());
		session.setAttribute("ready", readyVo);
		session.setAttribute("product", vo);
		log.info("tidinfo={}", result.getTid());
		
		
		return "redirect:"+result.getNext_redirect_pc_url();
		
	}
	@GetMapping("/success")
	public String success(@RequestParam String pg_token, HttpSession session, Model model) throws URISyntaxException {
		String tid = (String)session.getAttribute("tid");
		KakaoPayReadyVO vo =(KakaoPayReadyVO) session.getAttribute("ready");
		PaymentInfoVO infovo = (PaymentInfoVO)session.getAttribute("product");
		session.removeAttribute("tid");
		session.removeAttribute("ready");
		session.removeAttribute("product");
		
		int token = infovo.getToken();
		String id = vo.getPartner_user_id();
		int pastPoint = sqlSession.selectOne("pay.getPoint", id);
		int newpoint = pastPoint +token;
		
		
		TempVO tempVo = TempVO.builder()
													.id(id)
													.point(newpoint)
													.build();
		
		
		log.info("tempvoid={}", tempVo.getId());
		log.info("temppoint={}", tempVo.getPoint());
		sqlSession.update("pay.updatePoint", tempVo);
		log.info("idtest={}", vo.getPartner_order_id());
		
		KakaoPaySuccessReadyVO data = KakaoPaySuccessReadyVO.builder()
																									.cid("TC0ONETIME")
																									.tid(tid)
																									.partner_order_id(vo.getPartner_order_id())
																									.partner_user_id(vo.getPartner_user_id())
																									.pg_token(pg_token)
																									.build();
		KakaoPaySuccessReturnVO result = payService.approve(data);
		log.info("result = {}", result);
		int newPoint = sqlSession.selectOne("pay.getPoint", id);
		
		String order_id = vo.getPartner_user_id();
		log.info("idcheck={}", order_id);
		List<PayListDto> listDto = sqlSession.selectList("paylist", order_id);
		
		for(PayListDto dto : listDto) {
			log.info("dtocheck={}", dto.getItem_name());
		}
		
		
		model.addAttribute("paylist", listDto);
		model.addAttribute("result", result);
		model.addAttribute("point", newPoint);
		model.addAttribute("token", token);
		return "pay/success";
	}

}
