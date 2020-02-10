<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
function appendResult() {

	 

	$.ajax({

		url : "${pageContext.request.contextPath}/question2/newwindow",

		type : "post",

		data : {

			

		}



	})



}  

</script>
<h1>문제푸는중</h1>

<form action="${pageContext.request.contextPath}/question/questype" onsubmit="appendResult()">

<input type="hidden"  name="tno" value="${tno}">
<input type="submit"  value="돌아가기">
</form>


