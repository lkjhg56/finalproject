<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<h1 align="center">등급 포인트 랭킹</h1>
	
	<table class="table">
		
		<thead align="center">
			<tr>
				<th>no</th>
				<th>Level</th>
				<th>name</th>
				<th>grade_point</th>
			</tr>
		</thead>
		
		<tbody align="center">
		
			<c:forEach var="grade_point_rank" items="${grade_point_rank}">
				<tr>
					<td>
						<c:choose>
							<c:when test="${grade_point_rank.rank == 1}">★★★1등★★★</c:when>
							<c:when test="${grade_point_rank.rank == 2}">★★2등★★</c:when>
							<c:when test="${grade_point_rank.rank == 3}">★3등★</c:when>
							<c:otherwise>${grade_point_rank.rank}등</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${grade_point_rank.grade_point <= 5}">Level 1</c:when>
							<c:when test="${ 5 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 20}">Level 2</c:when>
							<c:when test="${ 20 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 50}">Level 3</c:when>
							<c:when test="${ 50 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 100}">Level 4</c:when>
							<c:when test="${ 100 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 200}">Level 5</c:when>
							<c:when test="${ 200 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 400}">Level 6</c:when>
							<c:when test="${ 400 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 800}">Level 7</c:when>
							<c:when test="${ 800 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 1600}">Level 8</c:when>
							<c:when test="${ 1600 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 3200}">Level 9</c:when>
							<c:otherwise>Level 10</c:otherwise>
						</c:choose>
					</td>
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


