<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/suneditor@latest/dist/css/suneditor.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/suneditor@latest/dist/suneditor.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/suneditor@latest/src/lang/ko.js"></script>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>    
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


$(function(){
	//제출 확인 기능
	$(".submitExam").click(function(){
		var result = confirm("등록하시겠습니까?");
		if(result){
			return true;
		}else
			return false;
	});	
});
</script>

<script>
function loadEditor(){
    var editor = SUNEDITOR.create((document.querySelector('textarea[name=question_solution]')),{
        //언어 설정
        lang: SUNEDITOR_LANG['ko'],
        
        //버튼 목록
        buttonList:[
            ['font', 'fontSize', 'fontColor'], 
            ['underline', 'italic', 'paragraphStyle', 'formatBlock'],
            ['align', 'table', 'image']
        ],
        //글꼴 설정
        font:[
            '굴림', '궁서', 'Verdana', 'Arial'
        ],
        //크기 설정
        fontSize:[
            10, 16, 32
        ],
        
    });

  	//중요 : 키입력시마다 값을 원래위치(textarea)에 복사
    editor.onKeyUp = function(e){
    	var content = document.querySelector("textarea[name=question_solution]");
    	content.value = editor.getContents();
    }
}
</script>
<style>

</style>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="container" >
<h1>일반문제 업로드</h1>
<hr>
<jsp:include page="/WEB-INF/views/template/sidebar.jsp"></jsp:include>
<form action="normalupload" method="post" enctype="multipart/form-data">

		<input type="text" name="test_category" placeholder="카테고리" required><br><br>
	<input type="text" name="csname" placeholder="문제유형" required><br><br>
	<input type="text" name="category_no" placeholder="회차" required><br><br>
	<textarea name="question" placeholder="문제명" rows="10" cols="50"></textarea><br><br>
	<img id="preview" src="http://placehold.it/200x200" width="500" height="300"><br>	
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
	<input type="radio" name="answer" value="5" >⑤<br><br>
	<textarea name="solution" placeholder="해설입력" rows="10" cols="50"></textarea><br><br>
	<input type="text" name="lim_min" placeholder="제한시간" required><br><br>
	
	<hr>
	<input class="submitExam btn btn-primary"  type="submit" value="등록하기">
	<input class="btn btn-primary"  type="reset" value="초기화">
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>