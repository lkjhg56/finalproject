<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<div class="container">
<h1 align="center">회원 목록</h1>
<table class="table table-hover">
		
		<thead align="center">
			<tr>
				<th>no</th>
				<th>회원 번호</th>
				<th>이름</th>
				<th>아이디</th>
				<th>가입일</th>
				<th>비고</th>
			</tr>
		</thead>
		
		<tbody align="center">
		
			<c:forEach var="user_list" items="${user_list}">
				<tr>
					<td>${user_list.rn}</td>
					<td>${user_list.user_no}</td>
					<td>${user_list.name}</td>
					<td>${user_list.id}</td>
					<td>${user_list.join_date.substring(0,10)}</td>
					<td>
						<form action="info" method="post">
							<input type="text" name="id" value="${user_list.id}" hidden>
							<button type="submit">회원정보 보기</button>
						</form>
						<form action="bye" method="post">
							<input type="text" name="id" value="${user_list.id}" hidden>
							<button type="submit">강제 탈퇴</button>
						</form>
					</td>
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
</div>	
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>	