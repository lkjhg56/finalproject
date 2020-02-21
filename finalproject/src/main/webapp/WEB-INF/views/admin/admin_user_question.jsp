<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="container">
<div><h1>문제 목록</h1></div>
<table class="table table-hover">
<thead class="thead-light">
	<tr>
		<th>No.</th>
		<th>카테고리</th>
		<th>제목</th>
		<th>조회수</th>
		<th>정답비율</th>
		<th>출제자</th>
		<th>문제 확인</th>
	</tr>
</thead>
<tbody>	
<c:forEach var="question" items="${list}">
	<tr>
		<td>${question.question_no}</td>
		<td>${question.category_name}</td>
		<td><a href="solve?question_no=${question.question_no}">${question.question_title}</a></td>
		<td>${question.read_count}</td>
		<td>${question.correct_ratio}%</td>
		<td>${question.id}</td>
		<td><a href="content?question_no=${question.question_no}">문제확인</a></td>
	</tr>
</c:forEach>
</tbody>
</table>
<!-- 네비게이션 -->
<jsp:include page="/WEB-INF/views/template/navigator.jsp">
<jsp:param name="pno" value="${pno}" />
	<jsp:param name="count" value="${count}" />
	<jsp:param name="navsize" value="${navsize}" />
	<jsp:param name="pagesize" value="${pagesize}" />
	<jsp:param name="board_category" value="${board_category}"/>
</jsp:include>
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
