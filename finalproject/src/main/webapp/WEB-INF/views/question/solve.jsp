<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script>
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
<h1>문제 풀기</h1>
<div>문제 번호 : ${questionDto.question_no}</div>
<div>출제자 : ${questionDto.id}</div>
<div>카테고리 : ${questionDto.category_name}</div>
<div>문제 제목 : ${questionDto.question_title}</div>
<div>문제 내용 : ${questionDto.question_content}</div>
<img id="preview" src="image?question_no=${questionDto.question_no}" width="120" height="120"><br><br>
<form action="solve" method="post">
<input type="hidden" name="question_no" value="${questionDto.question_no}">
<input type="radio" name="question_answer" value="1">1. ${questionDto.answer1}
<input type="radio" name="question_answer" value="2">2. ${questionDto.answer2}
<input type="radio" name="question_answer" value="3">3. ${questionDto.answer3}
<input type="radio" name="question_answer" value="4">4. ${questionDto.answer4}
<input type="radio" name="question_answer" value="5">5. ${questionDto.answer5}<br>
<input type="submit" value="제출하기">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>