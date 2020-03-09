<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
<style>
#page-wrapper {
    padding-left: 250px;
}
  
#sidebar-wrapper {
  position: absolute;
    width: 230px;
    height: 740px;
    margin-left: -485px;
    font-size: smaller;
    font-family: sans-serif;
    background: #343A40;
    overflow-x: hidden;
    overflow-y: auto;
}

#page-content-wrapper {
  width: 100%;
  padding: 20px;
}
/* 사이드바 스타일 */

.sidebar-nav {
  width: 250px;
  margin: 0;
  padding: 0;
  list-style: none;
}

.sidebar-nav li {
  text-indent: 1.5em;
  line-height: 2.8em;
}

.sidebar-nav li a {
  display: block;
  text-decoration: none;
  color: #999;
}

.sidebar-nav li a:hover {
  color: black;
  background: rgba(255, 255, 255, 0.2);
}

.sidebar-nav > .sidebar-brand {
  font-size: 1.3em;
  line-height: 3em;
}

</style>
<div class="container">
<h1 align="center">나의 포인트 내역</h1>
<div id="page-wrapper">
	<div id="sidebar-wrapper">
		<ul class="sidebar-nav">
			<li class="sidebar-brand">
				<a href="${context}/users/info">내 정보</a></li>
			<li><a href="${context}/users/change">• 정보 수정하기</a></li>
			
			<c:choose>
				<c:when test="${isAdmin}">
					<li><a href="${context}/users/user_list">• 회원 목록</a></li>
					<li><a href="${pageContext.request.contextPath}/question/normalupload">• 일반문제 업로드</a></li>
					<li><a href="${pageContext.request.contextPath}/question/normallist">• 일반문제 List</a></li>
					<li><a href="${pageContext.request.contextPath}/question/list">• 유저문제 List</a></li>
					<li><a href="${pageContext.request.contextPath}/admin/main">• admin page</a></li>
				</c:when>
			
				<c:otherwise> 
					<li><a href="${pageContext.request.contextPath}/question/upload">• 문제 업로드</a></li>
					<li><a href="${context}/question/my_upload_list">• 내가 업로드한 문제</a></li>
				</c:otherwise>
			</c:choose>
			
			<li><a href="${context}/users/test_result">• 내가 본 시험 내역</a></li>
			<li><a href="${context}/users/my_grade_point">• 나의 포인트 내역</a></li>
			<li><a href="${context}/users/test_point">• 포인트 줘보기 test</a></li>
			<li><a href="${context}/users/grade_point_rank">• 등급 포인트 랭킹</a></li>
			<li><a id="bye" href="${context}/users/bye">• 탈퇴하기</a></li>
			<li><a href="${context}/users/logout">• 로그아웃</a></li>
		</ul>
	</div>
</div>
<div  style="text-align:center">
	<table class="table table-hover">
		<thead align="center" class="thead-dark">
			<tr>
				<th>no</th>
				<th width="40%">내역</th>
				<th>포인트</th>
				<th>날짜</th>
			</tr>
		</thead>
		
		<tbody align="center">
			<c:forEach var="my_grade_point" items="${my_grade_point}">
				<tr>
					<td>${my_grade_point.rn}</td>
					<td>${my_grade_point.point_type}</td>
					<td>${my_grade_point.get_point}</td>
					<td>${my_grade_point.get_date.substring(0,16)}</td>
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
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>