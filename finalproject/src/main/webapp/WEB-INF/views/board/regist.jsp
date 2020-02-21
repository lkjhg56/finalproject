<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/suneditor@latest/dist/css/suneditor.min.css" rel="stylesheet">


<style>
    textarea[name=board_content]{
        width:100%;
        height:250px;
    }
</style>

<script src="https://cdn.jsdelivr.net/npm/suneditor@latest/dist/suneditor.min.js"></script>
<!-- languages (Basic Language: English/en) -->
<script src="https://cdn.jsdelivr.net/npm/suneditor@latest/src/lang/ko.js"></script>

<script>
//비회원 게시글 등록 방지 
$(function() {
	 loadEditor();
	
    $(".view").find("#boardInsert").click(function(e) {
    	//this = #boardInsert
    	
    	var writer = $("#writer").val();
//     	console.log(writer);
    	
    	if(!writer){
    		e.preventDefault();
	    	alert("로그인이 필요합니다.");
	    	//페이지 이동명령(주소창 값 변경하기)
	        location.href = "http://localhost:8080/finalproject/users/login";	    		
    	}
    	
    });
});

function loadEditor(){
    var editor = SUNEDITOR.create((document.querySelector('textarea[name=board_content]')),{
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
    	var content = document.querySelector("textarea[name=board_content]");
    	content.value = editor.getContents();
    }
}

</script>

<div align="center">    
	<h1>게시글 등록</h1>

	<form action="regist" method="post" enctype="multipart/form-data">
		<input id="writer" type="hidden" name="board_writer" value="${id}">
	
	<table border="1" width="70%">
		<tr>
			<th>카테고리</th>
			<td>
				<select name="board_category"> <br><br>
					<option>선택하세요</option>
					<option>자유</option>
					<option>질문</option>
				<c:if test="${grade == '관리자'}">	
					<option>공지</option>
					<option>업데이트</option>
				</c:if>
				</select>
			</td>
		</tr>
			
		<tr>
			<th>제목</th>
			<td>
				<input type="text" name="board_title" placeholder="제목" required>
			</td>
		</tr>
				
		<tr>
			<th>첨부파일</th>
			<td>
				<input type="file" name="board_file"  multiple accept="image/*">
			</td>
		</tr>
		
		<tr>
			<td colspan="2">
				<textarea name="board_content" required></textarea>
			</td>
		</tr>
		
		<tr class="view">
			<td colspan="2" align="center">
				<input id="boardInsert" type="submit" value="등록하기">
				<a href="${pageContext.request.contextPath}/board/list">
					<input type="button" value="목록보기">
				</a>
			</td>
		</tr>
	</table>
			
	</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>