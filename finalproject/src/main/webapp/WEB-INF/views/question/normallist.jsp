<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>일반 문제 목록</h1>
<table border="1">
	<tr>
		<th>No.</th>
		<th>종류</th>
		<th>회차</th>
		<th>문제명</th>
		<th>정답비율</th>
		<th>문제 확인</th>
		
	
	</tr>
<c:forEach var="question" items="${list}">
	<tr>
		<td>${question.no}</td>
		<td>${question.csname}</td>
		<td>${question.category_no}</td>
		<td>${question.question}</td>
		<td>${question.rate}</td>
		<td><a href="content?question_no=${question.no}">확인</a></td>
	
		
	</tr>
</c:forEach>
</table>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>