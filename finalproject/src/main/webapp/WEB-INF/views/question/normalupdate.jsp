<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
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



$(function () {
	  $("#preview2").hide()
	console.warn("hide")
	
});

function deletefile(abc) {
	//삭제 알림창
            console.warn("파일삭제");
      /*       e.preventDefault() */
      
      var check = $("form[name=form1]");
      
	  $.ajax({
              url:"${pageContext.request.contextPath}/question2/deletefile",
              type:"post",
              data:{no:abc},
             success:function(){
            		alert("파일이 삭제 됩니다");
            		
            	
            	/* 	 $("#preview").load(window.location.href + "#preview"); */
            		 location.reload();
            		 
            	}
            });
	
}
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

<div class="container" >
<h1>문제 수정</h1>
<hr>
<form action="normalupdate" method="post" enctype="multipart/form-data" name="form1">

	<input type="hidden" name="no" value="${questionDto.no}">
	<input type="hidden" name="csname" value="${questionDto.csname} ">
문제 제목: <input type="text" name="question" value="${questionDto.question}" required><br><br>
문제 정답: <input type="text" name="answer" value="${questionDto.answer}" required><br><br>

 <img id="preview2" src="http://placehold.it/200x200" width="500" height="300"><br>	
	<img id="preview" src="qimage?no=${questionDto.no}" width="500" height="300"><br><br>
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br>
		<button type="button" onclick="deletefile('${questionDto.no}')">파일 삭제하기</button><br><br>
<a href="#"  onclick="deletefile('${questionDto.no}')">삭제하기</a>
문제 보기 1:	<input type="text" name="dis1" value="${questionDto.dis1}" required><br><br>
문제 보기 2:		<input type="text" name="dis2" value="${questionDto.dis2}" required><br><br>
문제 보기 3:		<input type="text" name="dis3" value="${questionDto.dis3}" required><br><br>
	문제 보기 4:	<input type="text" name="dis4" value="${questionDto.dis4}" required><br><br>
	문제 보기 5:	<input type="text" name="dis5" value="${questionDto.dis5}" required><br><br>
	
해설	  <input type="text" name="solution" value="${questionDto.solution}" required><br><br>  
	<c:if test="${grade=='관리자'}">
<%-- 	<input type="text" name="ispremium" value="${questionDto.ispremium}" required><br><br> --%>
	</c:if>
	<hr>
	<input class="submitExam btn btn-primary"  type="submit" value="수정하기">
	<input class="btn btn-primary" type="reset" value="초기화">
	
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>


        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>