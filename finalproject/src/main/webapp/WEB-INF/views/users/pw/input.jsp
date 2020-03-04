<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
//이메일 인증
	$(function(){

	$("#cert").submit(function(e){
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
					$("#cert").find("input[type=submit]").val("인증번호 발송완료");
				}
			}
		});
	});
});
	
</script>
<style>
h2 {
	margin-top: 10px;
    margin-bottom: 30px;
    text-align: center;
    font-size: 40px;
    letter-spacing: -1px;
}
p{
	font-size: 18px;
	margin-top: 18px;
	margin-bottom: 6px;
}
.cert_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 25px;
    padding-bottom: 25px;
    padding-left: 20px;
    padding-right: 20px;
    margin: auto 232px;
    margin-top: 10px;
    height: 300px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
.cert_box input[type=email] {
	font-size: 20px;
    border: 0px;
    outline: #efefef;
    border-bottom: 1px solid #565960;
    width: 300px;
    height: 3rem;
    padding: 20px;
    margin-top: 5px;
    margin-left: 145px;
    padding-top: 20px;

}
#cert {
	display: block;
    font-size: 18px;
    margin-top: 20px;
    margin-left: 145px;
    padding: 5px 10px;
    height: 50px;
    width: 300px;
}
#cert:hover {
	display: block;
    font-size: 19px;
    margin-top: 20px;
    margin-left: 145px;
    padding: 5px 10px;
    height: 50px;
    width: 300px;
}
</style>
<div class="container">
<div class="cert_box">
<h2>비밀번호 변경을 위한 이메일 인증</h2>
	<form action="input" method="post">
		<input type="email" name="email" placeholder="이메일 입력" required>
		<input type="submit" class="btn btn-primary" id="cert" value="인증메일 발송">
	</form>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
