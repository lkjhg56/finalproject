<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/mheader.jsp"></jsp:include>
<script>
	$(function(){
		$(".list").hide();
		$(".click").click(function(){
			$(".list").show();
		});
		var selected_val = $("#selectBox option:selected").val();	
	});
</script>
<h1>문제 목록</h1>
<div class="container">
<table border="1">
	<tr>
		<th>No.</th>
		<th>제목</th>
		<th>조회수</th>
		<th>정답비율</th>
		<c:if test="${grade=='관리자'}">
		<th>문제 확인</th>
		</c:if>
		<th>문제 풀기</th>
		<th>출제자</th>
	</tr>
<c:forEach var="question" items="${list}">
	<tr>
		<td>${question.question_no}</td>
		<td>${question.question_title}</td>
		<td>${question.read_count}</td>
		<td>${question.correct_ratio}%</td>
		<c:if test="${grade=='관리자'}">
		<td><a href="content?question_no=${question.question_no}">확인</a></td>
		</c:if>
		<td><a href="solve?question_no=${question.question_no}">풀기</a>
		<c:if test="${id==question.id}">
		/ <a href="content?question_no=${question.question_no}">확인</a>
		</c:if>
		</td>
		<td>${question.id}</td>
	</tr>
</c:forEach>
</table>
<a href="${pageContext.request.contextPath}/question/upload">문제 업로드</a>
<a class="click">랜덤문제 풀기</a>
<!-- <button class="click">문제 수</button> -->
<span class="list">문제 개수 :
<select name = "number" id="selectBox" onchange="if(this.value) location.href=(this.value)">
	<c:forEach var="QuestionNumber" items="${list}" varStatus="status">
		<option class="optionVal" value="${pageContext.request.contextPath}/question/multi?wantQuestion=${status.count}">${status.count}</option>
	</c:forEach>
</select>
<a type="button" href="${pageContext.request.contextPath}/question/multi?wantQuestion=">풀기</a>
</span>
</div>
<jsp:include page="/WEB-INF/views/template/mfooter.jsp"></jsp:include>