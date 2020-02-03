<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<h1>regist</h1>

<form action="regist" method="post" enctype="multipart/form-data">
	<select name="board_category">
		<option>선택하세요</option>
		<option>공지</option>
		<option>자유</option>
		<option>질문</option>
		<option>업데이트</option>
	</select>
	<input type="text" name="board_title" placeholder="제목">
	<textarea name="board_content" required></textarea>
	<input type="file" name="board_file"  multiple accept="image/*"> <br><br>
	<input type="submit" value="등록하기">
</form>