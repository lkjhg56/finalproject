<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>포인트 랭킹</h1>
		<table class="table">
		<thead>
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
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>		


