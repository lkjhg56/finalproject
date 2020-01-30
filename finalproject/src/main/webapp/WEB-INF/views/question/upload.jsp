<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<h1>문제 업로드</h1>
<form action="upload" method="post">
	<input type="text" name="question_title" placeholder="제목" required><br><br>
	<textarea name="question_content" placeholder="문제명" rows="10" cols="50"></textarea><br><br>
	<input type="text" name="question_answer" placeholder="정답" required><br><br>
	<input type="text" name="question_solution" placeholder="해설입력" required><br><br>
	<input type="hidden" name="user_custom_question_no" value="1" required>
	<input type="submit" value="등록하기">
	<input type="reset" value="초기화">
</form>
