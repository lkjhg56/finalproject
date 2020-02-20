package com.kh.finalproject.paycontroller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay")
public class PayController {
	@Autowired
	SqlSession sqlSession;
	
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("list", sqlSession.selectList("getCashList"));
		return "pay/pointpayment";
	}
	
	@GetMapping("premium")
	public String premium() {
		return "pay/premium";
	}
}
