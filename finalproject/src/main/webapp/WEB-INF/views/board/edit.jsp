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
    
   *{
	     font-weight: 400;
	}
	
	.container table{
		 font-family: 'Noto Sans';
		 font-size: 13px;
	}
	
	.row-empty {
		height: 30px;
	}
	
    .btn{
		color : black;
	}
	
	.btn-primary:hover{
		color : black;
		background-color: #118FCB;
	}
	
	.btn23 {
      	 font-family: 'Noto Sans';
	     font-weight: 420;
	     font-size: 13px;
		  display: inline-block;
		  font-weight: 400;
		  color: #212529;
		  text-align: center;
		  vertical-align: middle;
		  background-color: white;
		  border: 1px solid black;
		  padding: 0.375rem 0.75rem;
		  line-height: 1.5;
		 }
		 
	 .category-btn:hover, .category-btn:active{
		background: none;
		color : #1295D3;
		outline : none;
	}
	
	.btn-primary a{
		color: black;
		text-decoration:none;
	}
	table tr th{
	background-color: whitesmoke;
	}
	
	#copyright{
		font-family: 'Noto Sans';
	    font-weight: 400;
	    font-size: 11px;
	    padding : 3px;
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
	            ['font', 'fontSize', 'fontColor','underline', 'italic','align']
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
	
	//윈도우 로딩 시 loadEditor를 실행하도록 설정(body에 onload 한 효과)
   		window.onload = loadEditor;
</script>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div class="row-empty"></div>
<div class="row-empty"></div>
<div class="container">    
<h3>게시글 수정</h3><span id="copyright">저작권 등 다른 사람의 권리를 침해하거나 명예를 훼손하는 게시글은 이용약관 및 관련법률에 의해 제재를 받으실 수 있습니다.</span><br><br>

<form action="edit" method="post" enctype="multipart/form-data">
	<input type="hidden" name="board_no" value="${boardDto.board_no}">
	<input type="hidden" name="board_no" value="${boardFileDto.board_origin_content_no}">
	<table class="table table-bordered">		
			<!-- 번호는 숨김 처리로 전송 -->
			
			<tr>
			<th>말머리</th>
				<td>
				<select name="board_category">
					<option value="자유" >자유</option>
					<option value="질문">질문</option>
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
					<input  class="btn23 category-btn "  type="submit" value="수정하기">
					<a href="list">
						<input  class="btn23 category-btn "  type="button" value="목록보기">
					</a>			
				</td>
			</tr>			
		</table>	
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>