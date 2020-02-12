<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<%-- ${list} --%>
수험 시간 : <c:if test="${time.hour!=0}">${time.hour}시간</c:if> ${time.min}분 ${time.sec}초<br>
<c:forEach var="result" items="${list}" varStatus="status">
문제 ${status.count}<br>
입력한 정답 : ${result.question_answer}<br>
정답 여부 : 
<c:choose>
	<c:when test="${result.result==1}">
		정답
	</c:when>
	<c:otherwise>
		<font>오답</font>
	</c:otherwise>
</c:choose>
</c:forEach>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>