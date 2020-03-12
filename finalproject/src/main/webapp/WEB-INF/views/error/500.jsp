<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	a{
		color: #777;
	}
</style>

<div class="container">
	<h1>500 내부 서버 오류</h1>
	죄송합니다. 잘못된 스크립트가 실행되어 웹 서버가 요청사항을 수행할 수 없습니다. <br><br>
	<div>
		<a href="${pageContext.request.contextPath}/">Home으로 이동</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>