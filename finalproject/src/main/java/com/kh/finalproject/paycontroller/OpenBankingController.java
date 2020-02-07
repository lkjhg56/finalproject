package com.kh.finalproject.paycontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pay/openbank")
public class OpenBankingController {
	@GetMapping("callback")
	public String callback() {
		return "pay/openbank/callback";
	}
}
