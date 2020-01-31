<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>문제 수정</h1>
<form action="update" method="post">
	<input type="hidden" name="user_id" value="${id}" required>
	<input type="hidden" name="question_no" value="${question_no}" required>
	<input type="hidden" name="user_custom_question_no" value="${user_custom_question_no}" required>
	<input type="text" name="question_title" placeholder="제목" value="${question_title}" required><br><br>
	<textarea name="question_content" placeholder="${question_content}" maxlength="200" rows="10" cols="50"></textarea><br><br>
	<input type="text" name="question_answer"  placeholder="정답" required><br><br>
	<input type="text" name="question_solution" placeholder="해설입력" required><br><br>
	<input type="submit" value="등록하기">
	<input type="reset" value="초기화">
</form>