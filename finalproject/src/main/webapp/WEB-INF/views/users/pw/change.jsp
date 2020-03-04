<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>

//비밀번호 재확인
	$(function(){
		
		$("#change").click(function(){
			var pw = $("input[name=pw]").val();
			var ppww = $("input[name=ppww]").val();
			var regex = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,16}$/;
			
			if(pw == ""){
				window.alert("비밀번호를 입력해주세요.");
			}
			
			else if(ppww == ""){
				window.alert("비밀번호 확인을 해주세요.");
			}
			
			else if(!regex.test(pw)){
				window.alert("비밀번호 형식(8~16자 영문자,숫자,특수문자)을 맞춰주세요!");
				$("input[name=pw]").focus().val("");

			}
			
			else{
				
				if(pw != ppww){
					window.alert("비밀번호가 맞지 않습니다.");
					$("input[name=ppww]").focus().val("");
				}
				else{
					$("input[name=email]").focus();
				}

			}
			
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
.pass_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 25px;
    padding-bottom: 25px;
    padding-left: 20px;
    padding-right: 20px;
    margin: auto 232px;
    margin-top: 10px;
    height: 350px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
.pass_box input[name=pw] {
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
.pass_box input[name=ppww] {
	font-size: 20px;
    border: 0px;
    outline: #efefef;
    border-bottom: 1px solid #565960;
    width: 300px;
    height: 3rem;
    padding: 20px;
    margin-top: 10px;
    margin-left: 145px;
    padding-top: 20px;
}
#change {
	display: block;
    font-size: 18px;
    margin-top: 20px;
    margin-left: 145px;
    padding: 5px 10px;
    height: 50px;
    width: 300px;
}
#change:hover {
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
<div class="pass_box">
<h2>새로운 비밀번호로 변경하세요.</h2>

<form action="change" method="post"> 
	<input type="password" name="pw" maxlength="16" placeholder="8~16자 영문자,숫자,특수문자" required><br>
	<input type="password" name="ppww" placeholder="비밀번호  확인" required>
	<input type="submit" class="btn btn-primary" id="change" value="비밀번호 변경하기">	
</form>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>