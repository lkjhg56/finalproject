<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<style>
* {
	font-family: binggrae;
	box-sizing: border-box;
}
.container {
    max-width: 1140px;
    margin: auto auto;
}
h1 {
	margin-top: 10px;
    margin-bottom: 30px;
    text-align: center;
    font-size: 62px;
    letter-spacing: -1px;
}
#p{
	font-size: 20px;
	margin-top: 10px;
    margin-bottom: 5px;
}
.login_box{
	display: inline-block;
    width: 400px;
    border: 1px solid #ddd;
    padding-top: 20px;
    padding-bottom: 10px;
    padding-left: 30px;
    padding-right: 30px;
    margin: auto 368px;
    margin-top: 30px;
    height: 330px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
.login_box input[type=text] {
	display: block;
	font-size: 18px;
	border: 0px;
	outline: #efefef;
	border-bottom: 1px solid #565960;
	width: 330px;
	height: 2rem;
	padding: 10px 5px;
	margin-bottom: 25px; 
}

.login_box input[type=password] {
	display: block;
	font-size: 18px;
	border: 0px;
	outline: #efefef;
	border-bottom: 1px solid #565960 ;
	width: 330px;
	height: 2rem;
	padding: 10px 5px;
	margin-bottom: 25px;
}
#login {
    display: block;
	font-size: 18px;
    padding: 5px 10px;
    width: 330px;
    height: 45px;
}
</style>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="container">

<h1>Qmaster</h1>

<div class="login_box">
	<form action="login" method="post">
		<p id="p">ID</p>
		<input type="text" name="id" required>
		 
		 <p id="p">PASSWORD</p>
		<input type="password" name="pw" required>
		
		<input class="btn btn-primary" id="login" type="submit" value="Login">
	</form>
		<div style="text-align: right">
			<a href="join"><span class="join">회원가입</span></a>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
id = ${id}, grade = ${grade}
