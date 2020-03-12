package com.kh.finalproject.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.kh.finalproject.entity.UsersDto;

public class PremiumFilter implements Filter{


	
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
	
		String ispremium = (String) req.getSession().getAttribute("isPremium");
		
	
		
System.out.println(ispremium+"aaaa");

		if (ispremium.equals("1")) {
			chain.doFilter(request, response);

		}
		
		
		
		else {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath()+"/users/info");
		}
		
	}

	
	
	
	
}
