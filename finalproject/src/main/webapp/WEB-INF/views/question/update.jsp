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
<script>
function deletefile(target) {
	  $.ajax({
	          url:"${pageContext.request.contextPath}/question/deleteFile",
	          type:"post",
	          data:{question_no:target},
	          success:function(){
	        		alert("파일이 삭제 됩니다");
	   	     		location.reload();
          	  }
      });
}
$(function(){
	//제출 확인 기능
	$(".submitExam").click(function(){
		var result = confirm("수정하시겠습니까?");
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
.btn-danger{
	color: white;
}
</style>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div class="container">
<h1>문제 수정</h1>
<hr>
<form action="update" method="post" enctype="multipart/form-data">
<div class="form-group row">
	<div class="col-9">
	<input type="hidden" name="question_no" value="${questionDto.question_no}">
	<input type="hidden" name="user_custom_question_no" value="${questionDto.user_custom_question_no}">
	<strong>제목</strong>
	<input type="text" class="form-control" name="question_title" value="${questionDto.question_title}" required><br>
	<strong>카테고리명</strong>
	<input type="text" class="form-control" name="category_name" value="${questionDto.category_name}" required><br>
	<strong>이미지 파일</strong><br>
	<c:if test="${image !=null}">
		<c:forEach var="image" items="${image}">
			<img id="preview" onchange="previewImage(this);" src="image?question_file_no=${image.question_file_no}" width="500" height="300">
		</c:forEach>
	</c:if>
	<br>
	<a type="button" class="btn btn-danger" onclick="deletefile('${questionDto.question_no}')">삭제하기</a>
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br><br>
	<strong>문제 내용</strong>
	<textarea class="form-control" name="question_content" rows="10" cols="50">${questionDto.question_content}</textarea><br>
	<strong>선택지 입력</strong><br>
	- 보기 1 <input type="text" class="form-control" name="answer1" value="${questionDto.answer1}" required>
	- 보기 2 <input type="text" class="form-control" name="answer2" value="${questionDto.answer2}" required>
	- 보기 3 <input type="text" class="form-control" name="answer3" value="${questionDto.answer3}" required>
	- 보기 4 <input type="text" class="form-control" name="answer4" value="${questionDto.answer4}" required>
	- 보기 5 <input type="text" class="form-control" name="answer5" value="${questionDto.answer5}" required><br>
	<strong>정답 설정</strong>
	<input type="text" class="form-control" name="question_answer" value="${questionDto.question_answer}" required><br>
	<strong>해설 입력</strong>
	<textarea class="form-control" name="question_solution" rows="10" cols="50">${questionDto.question_solution}</textarea>
	<c:if test="${grade=='관리자'}">
	<input type="text" class="form-control" name="question_premium" value="${questionDto.question_premium}" required><br><br>
	</c:if>
	</div>
</div>
	<hr>
	<input class="submitExam btn btn-primary" type="submit" value="수정하기">
	<input type="reset" class="btn btn-primary" value="초기화">
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>