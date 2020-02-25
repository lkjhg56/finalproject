<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
//이메일 인증
	$(function(){

	$("#wow").hide();
	
//		.email-form이 전송되면 send 주소로 비동기 신호를 전송(ajax)
	$(".cert_email").submit(function(e){
		e.preventDefault();
		
		$(this).find("input[type=submit]").prop("disabled", true);
		$(this).find("input[type=submit]").val("인증번호 발송중...");

		//var url = $(this).attr("action"); 
		var method = $(this).attr("method");
		var data = $(this).serialize();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/users/pw/input",
			type:"post",
			data:data,
			success:function(resp){
				if(resp == "success"){
					$(".cert_email").find("input[type=submit]").val("인증번호 발송완료");
					$("#wow").show();
				}
			}
		});
	});
});
	
</script>
<div class="container">
<h1>비밀번호 변경을 위한 이메일 인증</h1>

<form action="input" method="post">
	<input type="email" name="email" placeholder="이메일 입력" required>
	<input type="submit" class="cert_email" value="인증메일 발송">
</form>
	<h3 id="wow">인증메일을 전송하였습니다. 이메일을 확인하세요.</h3>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
