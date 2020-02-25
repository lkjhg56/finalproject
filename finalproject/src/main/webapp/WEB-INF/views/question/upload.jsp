<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link href="https://cdn.jsdelivr.net/npm/suneditor@latest/dist/css/suneditor.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/suneditor@latest/dist/suneditor.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/suneditor@latest/src/lang/ko.js"></script>
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
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="container">
<h1>문제 업로드</h1>
<hr>
<form action="upload" method="post" enctype="multipart/form-data">
<div class="form-group row">
	<div class="col-9">
	<input type="hidden" name="user_id" value="${id}">
	<strong>제목</strong>
	<input type="text" class="form-control" name="question_title" placeholder="제목" required><br>
	<strong>카테고리명</strong>
	<input type="text" class="form-control" name="category_name" placeholder="카테고리명" required><br>
	<strong>이미지 파일</strong><br>
	<img id="preview" class="rounded" src="http://placehold.it/200x200" width="500" height="300"><br>	
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br><br>
	<strong>문제 내용</strong>
	<textarea class="form-control"  name="question_content" placeholder="문제 내용" rows="5" cols="30"></textarea><br>
	<strong>선택지 입력</strong>
	<input type="text" class="form-control" name="answer1" placeholder="선택지 1 입력" required>
	<input type="text" class="form-control"  name="answer2" placeholder="선택지 2 입력" required>
	<input type="text" class="form-control"  name="answer3" placeholder="선택지 3 입력" required>
	<input type="text" class="form-control"  name="answer4" placeholder="선택지 4 입력" required>
	<input type="text" class="form-control"  name="answer5" placeholder="선택지 5 입력" required><br>
	<!-- 정답 칸은 숫자로 입력할 수 있도록 script에서 처리해야함. -->
	<strong>정답 설정</strong><br>
	<input type="radio" name="question_answer" value="1" required>&nbsp;①&nbsp;&nbsp;
	<input type="radio" name="question_answer" value="2" required>&nbsp;②&nbsp;&nbsp;
	<input type="radio" name="question_answer" value="3" required>&nbsp;③&nbsp;&nbsp;
	<input type="radio" name="question_answer" value="4" required>&nbsp;④&nbsp;&nbsp;
	<input type="radio" name="question_answer" value="5" required>&nbsp;⑤<br><br>
	<strong>해설 입력</strong>
	<textarea class="form-control"  name="question_solution" placeholder="해설입력" rows="5" cols="30"></textarea><br>
	<c:choose>
		<c:when test="${grade=='관리자'}">
			<h6>* 관리자가 올리는 경우 '프리미엄 문제'로 간주됩니다.</h6>
			<input type="hidden" name="question_premium" value="1">
		</c:when>
		<c:otherwise>
			<input type="hidden" name="question_premium" value="0">
		</c:otherwise>
	</c:choose>
	</div>
</div>
	<hr>
	<input class="submitExam btn btn-primary" type="submit" value="등록하기">
	<input class="btn btn-primary" type="reset" value="초기화">
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
