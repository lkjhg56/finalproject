<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
문제 채점 결과 :  
<c:choose>
	<c:when test="${result.result==1}">
		정답입니다.
	</c:when>
	<c:otherwise>
		오답입니다.
	</c:otherwise>
</c:choose>
<br>
선택하신 정답 : ${result.user_conclusion}<br>
문제 정답 : ${result.question_answer}<br>
문제 풀이 시간 : ${result.min}분 ${result.sec}초<br>
이 문제를 맟춘 누적 사용자 수 : ${result.question_true}<br><!-- sql에서 question_no를 이용하여 select distinct count(tried_user) from question_result -->
이 문제를 틀린 누적 사용자 수 : ${result.question_false}<br><!-- sql에서 question_no를 이용하여 select distinct count(tried_user) from question_result -->
현재 사용자 풀이 시간 순위 : <br><!-- top n 을 이용 -->
전체 수험자 중 ${result.user_priority}번째로 맞추셨습니다.<br>
??포인트가 적립됬습니다.<br>
풀이 날짜 : ${result.solveDate}
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>