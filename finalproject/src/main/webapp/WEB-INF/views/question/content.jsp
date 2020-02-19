<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>


<div>문제 번호 : ${questionDto.question_no}</div>
<div>문제 제목 : ${questionDto.question_title}</div>
<div>문제 내용 : ${questionDto.question_content}</div>
<c:if test="${grade=='관리자'}">
<div>유료 여부    : ${questionDto.question_premium}</div>
</c:if>
<c:if test="${image != null}">
	<c:forEach var="images" items="${image}">
		<div><img src="image?question_file_no=${images.question_file_no}"  width="120" height="120"></div>
	</c:forEach>
</c:if>
<div>문제 보기1 : ${questionDto.answer1}</div>
<div>문제 보기2 : ${questionDto.answer2}</div>
<div>문제 보기3 : ${questionDto.answer3}</div>
<div>문제 보기4 : ${questionDto.answer4}</div>
<div>문제 보기5 : ${questionDto.answer5}</div>
<c:if test="${id==questionDto.id || grade=='관리자'}">
<div>문제 정답 : ${questionDto.question_answer}</div>
<div>문제 해설 : ${questionDto.question_solution}</div>
</c:if>
<div>출제자 : ${questionDto.id}</div>
<div>카테고리 : ${questionDto.category_name}</div>
<br><br>
<div>
<c:if test="${id==questionDto.id || grade=='관리자'}">
<a href="update?question_no=${questionDto.question_no}">문제 수정</a>
<a href="delete?question_no=${questionDto.question_no}&user_custom_question_no=${questionDto.user_custom_question_no}">문제 삭제</a>
</c:if>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>