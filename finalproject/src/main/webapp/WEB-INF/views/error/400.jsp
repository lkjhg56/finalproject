<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	a{
		color: #777;
	}
</style>

<div class="container">
	<h1>400 Bad Request</h1>
	죄송합니다. 잘못된 요청으로 실행할 수 없습니다. <br><br>
	<div>
		<a href="${pageContext.request.contextPath}/">Home으로 이동</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>