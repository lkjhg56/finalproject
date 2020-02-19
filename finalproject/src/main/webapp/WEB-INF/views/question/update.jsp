<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script>
//첨부파일 이미지 미리보기

function previewImage(target){
    if(target.files && target.files[0]){
        var reader = new FileReader();
        reader.onload = function(data){
            var img = document.querySelector("#preview");
            img.src = data.target.result;    
        }
        reader.readAsDataURL(target.files[0]);
    }
}
</script>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>문제 수정</h1>
<form action="update" method="post" enctype="multipart/form-data">
	<input type="hidden" name="question_no" value="${questionDto.question_no}">
	<input type="hidden" name="user_custom_question_no" value="${questionDto.user_custom_question_no}">
	<input type="text" name="question_title" value="${questionDto.question_title}" required><br><br>
	<input type="text" name="category_name" value="${questionDto.category_name}" required><br><br>
	<textarea name="question_content" rows="10" cols="50">${questionDto.question_content}</textarea><br><br>
	<c:if test="${image !=null}">
		<c:forEach var="image" items="${image}">
		<div><img src="image?question_file_no=${image.question_file_no}" width="120" height="120"></div>
		</c:forEach>
	</c:if>
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br><br>
	<input type="text" name="answer1" value="${questionDto.answer1}" required><br><br>
	<input type="text" name="answer2" value="${questionDto.answer2}" required><br><br>
	<input type="text" name="answer3" value="${questionDto.answer3}" required><br><br>
	<input type="text" name="answer4" value="${questionDto.answer4}" required><br><br>
	<input type="text" name="answer5" value="${questionDto.answer5}" required><br><br>
	<input type="text" name="question_answer" value="${questionDto.question_answer}" required><br><br>
	<input type="text" name="question_solution" value="${questionDto.question_solution}" required><br><br>
	<c:if test="${grade=='관리자'}">
	<input type="text" name="question_premium" value="${questionDto.question_premium}" required><br><br>
	</c:if>
	<input type="submit" value="수정하기">
	<input type="reset" value="초기화">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>