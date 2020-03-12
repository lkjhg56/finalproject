<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	a{
		color: #777;
	}
</style>

<div class="container">
	<h1>404 Not Found</h1>
	죄송합니다. 현재 찾을 수 없는 페이지를 요청 하셨습니다.
	존재하지 않는 주소를 입력하셨거나,
	요청하신 페이지의 주소가 변경, 삭제되어 찾을 수 없습니다. <br><br>
	<div>
		<a href="${pageContext.request.contextPath}/">Home으로 이동</a>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>