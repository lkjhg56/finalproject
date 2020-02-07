<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<div align="center">    
<h1>게시글 등록</h1>

	<form action="regist" method="post" enctype="multipart/form-data" required>
		<select name="board_category"> <br><br>
			<option>선택하세요</option>
			<option>공지</option>
			<option>자유</option>
			<option>질문</option>
			<option>업데이트</option>
		</select>
		<input type="text" name="board_title" placeholder="제목" required> <br><br>
		<textarea name="board_content" required></textarea> <br><br>
		<input type="file" name="board_file"  multiple accept="image/*"> <br><br>
		<input type="hidden" name="board_writer">
		<input type="submit" value="등록하기">
	</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>