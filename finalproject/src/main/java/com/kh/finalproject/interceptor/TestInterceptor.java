package com.kh.finalproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;
/* ���ͼ���(Interceptor)
 * -��û�� ����ä�� ����
 * -�� 3������ ����ç �� ����.
 * -preHandel : DispatcherServlet ���� ��
 *  return true�� ����, return false�� ����
 * -postHandle : Controller ���� ��(return ����, ���� �ȵ�.)
 * -afterCompletion : View ���� �� (return ����, ���� �ȵ�.)
 * 
 */
@Slf4j
public class TestInterceptor extends HandlerInterceptorAdapter{

//	//
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		log.info("preHandle ����!");
//		log.info("handler={}",handler.getClass());
//		if(handler instanceof HandlerMethod) {
//			HandlerMethod h  = (HandlerMethod) handler;
//			log.info("method={}",h.getMethod().getName());
//			log.info("annotation={}",h.getMethodAnnotation(RequestMapping.class));
//		}
//		return true;
//	}
//
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		log.info("posthandle");
//		log.info("handler={}",handler);
//		log.info("model&view={}",modelAndView);
//	}
//
//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		log.info("afterCompletion");
//		log.info("handler={}",handler);
//		log.info("exception={}",ex);
//	}
	
}
