<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	a{
		color: #777;
	}
</style>

<div class="container">
	<h1>405 Method Not Allowed</h1>
		웹 페이지를 볼 수 있는 권한이 없습니다. <br><br>
	<div>
		<a href="${pageContext.request.contextPath}/">Home으로 이동</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>