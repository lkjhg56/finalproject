<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
//비밀번호 형식 검사
//	$(function(){
	
//		$("input[name=pw]").on("input",function(){
//			var pw = $("input[name=pw]").val();
//			var regex = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,16}$/;
		
//			if(regex.test(pw)){
//				console.log("굳굳!!!!!!!");
//			}
//			else{
//				console.log("노노노!!!!!!");
//			}
		
//		});
//	});	
$(function(){
	
	$(".change").click(function(){
		var pw = $("input[name=pw]").val();
		var regex = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,16}$/;
		
		if(!regex.test(pw)){
			window.alert("비밀번호 형식을 맞춰주세요.");
			$("input[name=pw]").focus().val("");

		}
		
		
	});
});	
</script>
<div class="container">
<h1>새로운 비밀번호로 변경하세요.</h1>

<form action="change" method="post">
	<input type="password" name="pw" placeholder="비밀번호 입력" required>
	<div class="pw">
		<h6>8~16자 영문자, 숫자, 특수문자 조합을 사용하세요.</h6>
	</div>
	<input type="submit" class="change" value="비밀번호 변경하기">	
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>