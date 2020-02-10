<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<h1>새로운 비밀번호로 변경하세요.</h1>

<form action="change" method="post">
	<input type="password" name="pw" placeholder="비밀번호 입력" required>
	<input type="submit" value="비밀번호 변경하기">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>