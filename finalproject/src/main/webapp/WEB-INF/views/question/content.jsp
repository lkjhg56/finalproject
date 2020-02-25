<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
$(function(){
	$(".delete").click(function(){
		var result = confirm("삭제하시겠습니까?");
		if(result){
			return true;
		}else{
			return false;
		}
	});
});
</script>
<style>

	th{
		white-space : nowrap;
	}
</style>
<div class="container">
<h4>문제 번호 : ${questionDto.question_no}</h4>	
<table class="table">
	<tbody>
	<tr>
		<th>문제 제목 </th>
		<td>${questionDto.question_title}</td>
	</tr>
	<tr>
		<th>문제 내용 </th>
		<td>${questionDto.question_content}</td>
	</tr>
	<tr>
		<th>이미지 </th>
		<td>
		<c:if test="${image != null}">
			<c:forEach var="images" items="${image}">
				<img src="image?question_file_no=${images.question_file_no}" width="500" height="300">
			</c:forEach>
		</c:if>
		</td>
	</tr>
	<tr>
		<th>문제 보기</th>
		<td>
			보기 1 : ${questionDto.answer1}<br>
			보기 2 : ${questionDto.answer2}<br>
			보기 3 : ${questionDto.answer3}<br>
			보기 4 : ${questionDto.answer4}<br>
			보기 5 : ${questionDto.answer5}
		</td>
	</tr>
	<c:if test="${grade=='관리자'}">
		<tr>
			<th>유료 여부 </th>
			<td>${questionDto.question_premium}</td>
		</tr>
	</c:if>
	<tr>
		<th>문제 정답 </th>
		<td>${questionDto.question_answer}</td>
	</tr>
	<tr>
		<th>문제 해설  </th>
		<td>${questionDto.question_solution}</td>
	</tr>
		<tr>
		<th>출제자  </th>
		<td>${questionDto.id}</td>
	</tr>
		<tr>
		<th>카테고리</th>
		<td>${questionDto.category_name}</td>
	</tr>
	</tbody>
</table>
<div>
	<c:if test="${id==questionDto.id || grade=='관리자'}">
		<a type="button" class="btn btn-primary" href="update?question_no=${questionDto.question_no}">문제 수정</a>
		<a type="button" class="delete btn btn-primary" href="delete?question_no=${questionDto.question_no}&user_custom_question_no=${questionDto.user_custom_question_no}">문제 삭제</a>
	</c:if>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>