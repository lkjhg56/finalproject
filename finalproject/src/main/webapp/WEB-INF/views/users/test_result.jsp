<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
<style>
/* 검색바 */
.search{
	box-sizing: border-box;
    width: 300px;
    height: 50px;
    font-family: fa5-proxima-nova,"Helvetica Neue",Helvetica,Arial,sans-serif;
    background-color: #f1f3f5;
    border-width: 1px;
    border-style: none;
	border-radius: 9999px;
    padding-left: 1.5rem;
    padding-right: 1.5rem;
	overflow: visible;
}
.button{
	box-sizing: border-box;
	background: rgba(0,0,0,0);
	border-style: none;
	margin-left: -50px;
}
.footer-color {
    margin-top: 100px;
}
</style>
<div class="container">
<h1 align="center">나의 시험 내역</h1>
<jsp:include page="/WEB-INF/views/template/sidebar.jsp"></jsp:include>
<div  style="text-align:center">

	<table class="table table-hover">
		<thead align="center" class="thead-dark">
			<tr>
				<th>no</th>
				<th width="40%">시험</th>
				<th>점수</th>
				<th>날짜</th>
			</tr>
		</thead>
	<!-- 시험 내역이 없을 때 -->
		<tbody align="center">
		</tbody>

	
	<!-- 시험 내역이 있을 때 -->
		<tbody align="center">
			<c:if test="${empty test_result}">
				<tr>
					<td colspan="4" align="center">시험 내역이 없습니다.</td>
				</tr>
			</c:if>	
			<c:forEach var="test_result" items="${test_result}">
				<tr>
					<td>${test_result.rn}</td>
					<td>${test_result.cs_no}</td>
					<td>${test_result.sumscore}</td>
					<td>${test_result.test_date.substring(0,16)}</td>
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
		<input type="text" class="search" name="keyword" placeholder="Search">
		<button type="submit" class="button"><i class="fa fa-search"></i></button>
	</form>

</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>