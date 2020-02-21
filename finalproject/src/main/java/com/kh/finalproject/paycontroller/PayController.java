package com.kh.finalproject.paycontroller;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {
	@Autowired
	SqlSession sqlSession;
	
	@GetMapping("list")
	public String list(Model model) {
		model.addAttribute("list", sqlSession.selectList("getCashList"));
		return "pay/pointpayment";
	}
	
	@GetMapping("premium")
	public String premium(@RequestParam int point, Model model) {
		model.addAttribute("point", point);
		return "pay/premium";
	}
}
