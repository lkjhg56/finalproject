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
<form action="normalupdate" method="post" enctype="multipart/form-data">

	<input type="hidden" name="no" value="${questionDto.no}">
	<input type="hidden" name="csname" value="${questionDto.csname} ">
문제 제목: <input type="text" name="question" value="${questionDto.question}" required><br><br>
문제 정답: <input type="text" name="answer" value="${questionDto.answer}" required><br><br>


	 <img id="preview" src="qimage?no=${questionDto.no}" width="120" height="120"><br><br>
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br><br>
문제 보기 1:	<input type="text" name="dis1" value="${questionDto.dis1}" required><br><br>
문제 보기 2:		<input type="text" name="dis2" value="${questionDto.dis2}" required><br><br>
문제 보기 3:		<input type="text" name="dis3" value="${questionDto.dis3}" required><br><br>
	문제 보기 4:	<input type="text" name="dis4" value="${questionDto.dis4}" required><br><br>
	문제 보기 5:	<input type="text" name="dis5" value="${questionDto.dis5}" required><br><br>
	
해설	  <input type="text" name="solution" value="${questionDto.solution}" required><br><br>  
	<c:if test="${grade=='관리자'}">
<%-- 	<input type="text" name="ispremium" value="${questionDto.ispremium}" required><br><br> --%>
	</c:if>
	<input type="submit" value="수정하기">
	<input type="reset" value="초기화">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>