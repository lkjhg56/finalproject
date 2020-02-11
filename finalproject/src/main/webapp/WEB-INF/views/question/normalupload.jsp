<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<h1>일반문제 업로드</h1>
<form action="normalupload" method="post" enctype="multipart/form-data">

	<input type="text" name="csname" placeholder="제목" required><br><br>
		<input type="text" name="test_category" placeholder="문제유형" required><br><br>
	<input type="text" name="category_no" placeholder="카테고리명" required><br><br>
	<textarea name="question" placeholder="문제명" rows="10" cols="50"></textarea><br><br>
	<img id="preview" src="http://placehold.it/200x200" width="120" height="120"><br>	
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br><br>
	<input type="text" name="dis1" placeholder="선택지 1 입력" required><br><br>
	<input type="text" name="dis2" placeholder="선택지 2 입력" required><br><br>
	<input type="text" name="dis3" placeholder="선택지 3 입력" required><br><br>
	<input type="text" name="dis4" placeholder="선택지 4 입력" required><br><br>
	<input type="text" name="dis5" placeholder="선택지 5 입력" required><br><br>
	<!-- 정답 칸은 숫자로 입력할 수 있도록 script에서 처리해야함. -->
	정답 설정<br>
	<input type="radio" name="answer" value="1" required>①
	<input type="radio" name="answer" value="2" required>②
	<input type="radio" name="answer" value="3" required>③
	<input type="radio" name="answer" value="4" required>④
	<input type="radio" name="answer" value="5" required>⑤<br><br>
	<textarea name="solution" placeholder="해설입력" rows="10" cols="50"></textarea><br><br>
	<input type="submit" value="등록하기">
	<input type="reset" value="초기화">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>