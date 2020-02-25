<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div class="container">
	<div class="title d-flex justify-content-center jumbotron"><h1>시험 결과</h1></div>
	<div></div>
	<div><strong>
		<c:choose>
			<c:when test="${result.result==1}">
				정답입니다.
			</c:when>
			<c:otherwise>
				오답입니다.
			</c:otherwise>
		</c:choose>
		</strong>
		<hr>
			<h4>해설</h4>
			${result.question_solution}<hr>
			<h4>상세 결과</h4>
			선택하신 정답 : ${result.user_conclusion}<br>
			문제 정답 : ${result.question_answer}<br>
			문제 풀이 시간 : ${result.min}분 ${result.sec}초<br>
			이 문제를 맟춘 누적 사용자 수 : ${result.question_true}<br>
			이 문제를 틀린 누적 사용자 수 : ${result.question_false}<hr>
			<h4>포인트</h4>
		<c:choose>
			<c:when test="${result.result==1}">
				3포인트가 적립됬습니다.<br>
			</c:when>
			<c:otherwise>
				다음 기회에....
			</c:otherwise>
		</c:choose>
		<br>
		<div class="jumbotron"></div>
		<div>
			<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}">처음 페이지로</a>
			<a type="button" class="btn btn-primary"  href="${pageContext.request.contextPath}/question/list">유저 문제 목록으로</a>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>