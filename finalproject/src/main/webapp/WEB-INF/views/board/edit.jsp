<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
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
	$(function(){
		 loadEditor();
		//수정 시 원래 설정한 카테고리 값을 기본값으로 설정함//
		 $("select[name=board_category]").val("${boardDto.board_category}"); 
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

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div align="center">    
<h1>게시글 수정</h1>

<form action="edit" method="post" enctype="multipart/form-data">
	<input type="hidden" name="board_no" value="${boardDto.board_no}">
	<table border="1" width="70%">		
			<!-- 번호는 숨김 처리로 전송 -->
			
			<tr>
			<th>말머리</th>
				<td>
				<select name="board_category">
					<option value="공지">공지</option>
					<option value="자유" >자유</option>
					<option value="질문">질문</option>
					<option value="업데이트">업데이트</option>					
				</select>				
				</td>
			</tr>
			
			<tr>
			<th>제목</th>
				<td>
					<input type="text" name="board_title" value="${boardDto.board_title }" required>
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
					<textarea name="board_content">${boardDto.board_content }</textarea> 					
				</td>
			</tr>
			
			<tr>
				<td colspan="2" align="center">			
					<input type="submit" value="수정하기">
					<a href="list">
						<input type="button" value="목록보기">
					</a>			
				</td>
			</tr>			
		</table>	
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>