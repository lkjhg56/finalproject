<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<h1>나의 시험 내역</h1>

<div>

	<table class="table">
		<thead>
			<tr>
				<th width="40%">cs_no</th>
				<th>sumscore</th>
				<th>test_date</th>
			</tr>
		</thead>
		
		<tbody align="center">
		
			<c:forEach var="test_result" items="${test_result}">
				<tr>
					<td>${test_result.cs_no}</td>
					<td>${test_result.sumscore}</td>
					<td>${test_result.test_date}</td>
				</tr>
			</c:forEach>
			
			<!-- 검색한 경우 -->
			<c:forEach var="search_result" items="${search_result}">
				<tr>
					<td>${search_result.cs_no}</td>
					<td>${search_result.sumscore}</td>
					<td>${search_result.test_date}</td>
				</tr>
			</c:forEach>
			
		</tbody>
	</table>
	
	<form action="test_result" method="post">
		<input type="text" name="keyword" placeholder="cs_no">
		<input type="submit" value="검색">
	</form>

</div>