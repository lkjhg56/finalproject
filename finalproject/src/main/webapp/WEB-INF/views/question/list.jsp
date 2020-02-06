<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>문제 목록</h1>
<table border="1">
	<tr>
		<th>No.</th>
		<th>제목</th>
		<th>조회수</th>
		<th>추천수</th>
		<th>정답비율</th>
		<th>문제 확인</th>
		<th>문제 풀기</th>
	</tr>
<c:forEach var="question" items="${list}">
	<tr>
		<td>${question.question_no}</td>
		<td>${question.question_title}</td>
		<td>${question.question_content}</td>
		<td>${question.question_no}</td>
		<td>${question.question_no}</td>
		<td><a href="content?question_no=${question.question_no}">확인</a></td>
		<td><a href="solve?question_no=${question.question_no}">풀기</a></td>
	</tr>
</c:forEach>
</table>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>