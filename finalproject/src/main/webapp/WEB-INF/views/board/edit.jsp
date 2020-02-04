<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	$(function(){
		$("select[name=board_category]").val("${boardDto.board_category}"); 
	});
</script>
    
<div align="center">    
<h1>게시글 수정</h1>

<form action="edit" method="post">
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
				<td colspan="2">			
					<textarea name="board_content" rows="15" cols="155" 
					style="resize:vertical;">${boardDto.board_content }</textarea> <!-- textarea 시작과 끝 사이에는 아무것도 적으면 안됨 -->									
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
