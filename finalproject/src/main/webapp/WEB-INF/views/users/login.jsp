<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
*{
    box-sizing: border-box;
}

.backBody{
	height: 700px;
	padding-top:40px;
	ackground-position:center;
	background-repeat: no-repeat; 
}
h1 {
	margin-top: 100px;
	text-align: center;
	font-size: 40px;
	letter-spacing: -1px
}

#wrap {
	width: 400px;
	border: 1px solid #ddd;
	background-color: #fff;
	padding: 15px;
	margin : auto auto;
	margin-bottom:10px;
	height: 300px;
	overflow: hidden;
}

#loginbox {
	width: 380px;
	padding: 5px;
	margin: 20px auto;
}

</style>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="backBody">
<h1>로그인</h1>
	<div id="wrap">
	<form action="login" method="post">
		<div id="loginbox">
		ID : <input type="text" name="id" required><br><br>
		PW : <input type="password" name="pw" required><br><br>
		<a href="join.jsp"><span class="join">회원가입하기</span></a>
		<input class="login_btn" type="submit" value="Login">
		</div>
	</form>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
id = ${id}, grade = ${grade}
