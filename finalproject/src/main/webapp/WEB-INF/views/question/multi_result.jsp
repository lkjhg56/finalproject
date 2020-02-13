<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/mheader.jsp"></jsp:include>
<%-- ${list} --%>
수험 시간 : <c:if test="${time.hour!=0}">${time.hour}시간</c:if> ${time.min}분 ${time.sec}초<br>
<c:forEach var="result" items="${list}" varStatus="status">
<p>문제 ${status.count}<br>
입력한 정답 :
<c:choose>
	<c:when test="${result.question_answer==0}">
		<font color="red">답을 적지 않으셨습니다.</font><br>
	</c:when>
	<c:otherwise>
		${result.question_answer}<br>
	</c:otherwise>
</c:choose>  
정답 여부 : 
<c:choose>
	<c:when test="${result.result==1}">
		정답<br>
	</c:when>
	<c:otherwise>
		<font color="red">오답</font><br>
	</c:otherwise>
</c:choose></p>
</c:forEach>
<jsp:include page="/WEB-INF/views/template/mfooter.jsp"></jsp:include>