package com.kh.finalproject.paycontroller;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.kh.finalproject.entity.UsersDto;

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
	//프리미엄 회원 전환
	 @PostMapping("premium")
	 @ResponseBody
	 public String premium(@ModelAttribute UsersDto usersDto, HttpSession session) {
		 //isPremium 바꾸고 point 500 차감
		 String id = (String) session.getAttribute("id");
		 int point = sqlSession.selectOne("users.is",id);
		 
		 if(point < 500) {
			 return "no";
		 }
		 else {
		 sqlSession.update("users.premium", id);
		 sqlSession.update("users.point", id);
		 return "yes";
		 }
	 }
}
