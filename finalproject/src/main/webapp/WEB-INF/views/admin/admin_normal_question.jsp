<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div class="container">

<h1>일반 문제 목록</h1>
<table class="table table-hover" style="text-align: center">

<thead class="thead-light">
	<tr>
		<th>No.</th>
		<th>종류</th>
		<th>회차</th>
		<th>문제명</th>
		<th>정답비율</th>
		<th>문제 확인</th>
		
	
	</tr>
	</thead>
	<tbody>	
<c:forEach var="question" items="${list}">
	<tr>
		<td>${question.no}</td>
		<td>${question.csname}</td>
		<td>${question.category_no}</td>
		<td>${question.question}</td>
		<td>${question.rate}</td>
		<td><a href="normalcontent?no=${question.no}">확인</a></td>
	
		
	</tr>
</c:forEach>
</tbody>
</table>
<div class="container" style="text-align: center">
<jsp:include page="/WEB-INF/views/template/navigator.jsp">
<jsp:param name="pno" value="${pno}" />
	<jsp:param name="count" value="${count}" />
	<jsp:param name="navsize" value="${navsize}" />
	<jsp:param name="pagesize" value="${pagesize}" />
	<jsp:param name="board_category" value="${board_category}"/>
</jsp:include>
</div>


</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>