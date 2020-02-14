<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>

<h1 align="center">나의 시험 내역</h1>

<div>

	<table class="table">
		<thead align="center">
			<tr>
				<th>no</th>
				<th width="40%">cs_no</th>
				<th>sumscore</th>
				<th>test_date</th>
			</tr>
		</thead>
		
		<tbody align="center">
			
			<!-- 검색한 경우 -->
			<c:forEach var="search_result" items="${search_result}">
				<tr>
					<td>${search_result.rn}</td>
					<td>${search_result.cs_no}</td>
					<td>${search_result.sumscore}</td>
					<td>${search_result.test_date}</td>
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
	
	<form action="test_result_search" method="get">
		<input type="text" name="keyword" placeholder="cs_no">
		<input type="submit" value="검색">
	</form>

</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>