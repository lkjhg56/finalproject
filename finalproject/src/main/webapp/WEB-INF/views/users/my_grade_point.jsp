<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<h1 align="center">나의 포인트 내역</h1>


	<table class="table">
		<thead align="center">
			<tr>
				<th>no</th>
				<th>포인트</th>
				<th width="40%">내역</th>
				<th>날짜</th>
			</tr>
		</thead>
		
			<c:forEach var="my_grade_point" items="${my_grade_point}">
		<tbody align="center">
				<tr>
					<td>${my_grade_point.rn}</td>
					<td>${my_grade_point.get_point}</td>
					<td>${my_grade_point.point_type}</td>
					<td>${my_grade_point.get_date}</td>
				</tr>
			
		</tbody>
			</c:forEach>
	</table>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>