<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문제 마스터 홈페이지</title>
</head>
<body>
<!-- 메인페이지 -->
<main>
<!-- 운영자 페이지 넣어야 함. -->


<!-- 헤더  -->
<header>
<div class="container">
	<div class="row">
		<div class="col">
			<a href="${pageContext.request.contextPath}"><img src="${pageContext.request.contextPath}/res/image/logo3.png"></a>
		</div>
		<div class="col">
<!-- 네이게이션 -->	
		<c:choose>
			<c:when test="${id == null}">				
					<a href="${pageContext.request.contextPath}/users/join">회원 가입</a>
					<a href="${pageContext.request.contextPath}/users/login">로그인</a>			
					<a href="${pageContext.request.contextPath}/board/list">게시판</a>				
			</c:when>
			<c:otherwise>				
					<a href="${pageContext.request.contextPath}/users/info">회원정보</a>
					<a href="${pageContext.request.contextPath}/question/upload">유저문제 등록</a>
					<a href="${pageContext.request.contextPath}/question/list">유저문제 목록</a>
					<a href="${pageContext.request.contextPath}/question/multi">유저문제 여러문제(임시)</a>
					<a href="${pageContext.request.contextPath}/question/choose">일반문제 목록</a>
					<a href="${pageContext.request.contextPath}/board/list">게시판</a>
					<a href="${pageContext.request.contextPath}/users/logout">로그아웃</a>				
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>
	<hr>
</header>
<section>