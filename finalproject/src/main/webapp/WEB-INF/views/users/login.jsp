<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>로그인</h1>
<form action="login" method="post">
	ID : <input type="text" name="id" required>
	PW : <input type="password" name="pw" required>
	<input type="submit" value="로그인">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
id = ${id}, grade = ${grade}