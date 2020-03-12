<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="container">

<h1>Qmaster</h1>

<div class="id_box">
	<form action="findId" method="post">
		<p id="p">NAME</p>
		<input type="text" name="name" required>
		 		
		 <p id="p">PHONE</p>
		<input type="password" name="phone" required>
		
		<input class="btn btn-primary" id="find" type="submit" value="아이디 찾기">
	</form>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>