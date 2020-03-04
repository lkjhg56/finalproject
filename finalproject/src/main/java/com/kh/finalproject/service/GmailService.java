package com.kh.finalproject.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.kh.finalproject.entity.CertDto;
import com.kh.finalproject.repository.CertDao;

public class GmailService implements EmailService{

	@Autowired
	private JavaMailSender sender;
	
	@Autowired
	private CertDao certDao;
	
	@Autowired
	private RandomService randomService;
	
	@Override
	public String sendCertMessage(String email, String cert) {
		
		try {
		// 메세지 객체 생성
		SimpleMailMessage message = new SimpleMailMessage();
		
		// 정보 설정 : 대상정보, 제목, 내용
		String[] to = {email};
		message.setTo(to);
		
		message.setSubject("[Q master] 인증을 위한 이메일입니다.");
		
		message.setText("인증번호는 " + cert + "입니다.");
		
		sender.send(message);
		return "success";
		}
		
		catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
	}
	
	@Transactional//이 표시가 붙은 메소드는 기능이 하나로 합쳐져서 실행관리
	@Override
	public void sendChangePasswordMail(String email) throws Exception {
		// 1. 랜덤 번호를 3자리 생성
		String cert = randomService.generateCertificationNumber(3);
		
		// 2. DB에 랜덤 번호 /이메일 /시각을 저장 
		certDao.regist(CertDto.builder().email(email).cert_no(cert).build());
		
		// 3. 이메일 전송
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		
		helper.setTo(email);
		helper.setSubject("[Q master] 비밀번호 변경 메일입니다.");
		
		//주소 생성
//		String url = "http://localhost:8080/sts21/pw/change?cert="+cert+"&email="+email;
		String url = ServletUriComponentsBuilder
							.fromCurrentContextPath()
							.port(8080)
							.path("/users/pw/change")
							.queryParam("cert", cert)
							.queryParam("email", email)
							.toUriString();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("<h1>비밀번호 변경을 위해 하단 링크를 누르세요.</h1>");
		buffer.append("<h2>");
		buffer.append("<a href='");
		buffer.append(url);
		buffer.append("'>");
		buffer.append("이동");
		buffer.append("</a>");
		buffer.append("</h2>");
		
		helper.setText(buffer.toString(), true);
		
		sender.send(message);
		
		
	}

	@Override
	public String sendEmail(String email) {
		try {
			// 메세지 객체 생성
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
			
			// 정보 설정 : 대상정보, 제목, 내용
			String[] to = {email}; //받는사람의 이메일
			helper.setTo(to);			
			
			helper.setSubject("[Q master] 게시글 처리 안내입니다.");
			
			StringBuffer buffer = new StringBuffer();
			buffer.append("<h3>");
			buffer.append("안녕하세요? Q master 관리자입니다.");
			buffer.append("</h3>");
			buffer.append("<h3>");
			buffer.append("항상 Q master를 이용해주시고 많은 관심 가져주셔서 진심으로 감사드립니다.");
			buffer.append("</h3>");
			buffer.append("<h3>");
			buffer.append("회원님이 작성한 게시글에 신고가 접수되어 안내드립니다.");
			buffer.append("</h3>");	
			buffer.append("<h3>");
			buffer.append("같은 게시글에 누적 신고 횟수가 3회 이상이 되면 관리자에 의해 삭제 처리 될 수 있으며, 이용제한 조치가 진행 될 수 있으니 유의하시기 바랍니다.");
			buffer.append("</h3>");	
			
			helper.setText(buffer.toString(), true);
			
			sender.send(message);
			
			return "success";
			} 
		
		catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		
	}
}
	
	
	

