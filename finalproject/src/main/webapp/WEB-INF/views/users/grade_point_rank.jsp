<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<h1 align="center">등급 포인트 랭킹</h1>
	
	<table class="table">
		
		<thead align="center">
			<tr>
				<th>name</th>
				<th>grade_point</th>
			</tr>
		</thead>
		
		<tbody align="center">
		
			<c:forEach var="grade_point_rank" items="${grade_point_rank}">
				<tr>
					<td>${grade_point_rank.name}</td>
					<td>${grade_point_rank.grade_point}</td>
				</tr>
			</c:forEach>
		</tbody>
		
	</table>
	
	<div class="row">
		<!-- 네비게이터(navigator) -->
		<jsp:include page="/WEB-INF/views/template/navigator.jsp">
			<jsp:param name="pno" value="${pno}"/>
			<jsp:param name="count" value="${count}"/>
			<jsp:param name="navsize" value="${navsize}"/>
			<jsp:param name="pagesize" value="${pagesize}"/>
		</jsp:include>
	</div>
	
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>		


