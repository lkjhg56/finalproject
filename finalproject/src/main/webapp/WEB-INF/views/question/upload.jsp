<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h1>문제 업로드</h1>
<form action="upload" method="post">
	<input type="hidden" name="user_no" value="${user_no}">
	<input type="hidden" name="user_no" value="${user_no}">
	<input type="text" name="question_title" placeholder="제목" required><br><br>
	<input type="text" name="category_name" placeholder="카테고리명" required><br><br>
	<textarea name="question_content" placeholder="문제명" rows="10" cols="50"></textarea><br><br>
	<input type="text" name="answer1" placeholder="선택지 1 입력" required><br><br>
	<input type="text" name="answer2" placeholder="선택지 2 입력" required><br><br>
	<input type="text" name="answer3" placeholder="선택지 3 입력" required><br><br>
	<input type="text" name="answer4" placeholder="선택지 4 입력" required><br><br>
	<input type="text" name="answer5" placeholder="선택지 5 입력" required><br><br>
	<input type="text" name="question_answer" placeholder="정답 입력" required><br><br>
	<input type="text" name="question_solution" placeholder="해설입력" required><br><br>
	<input type="submit" value="등록하기">
	<input type="reset" value="초기화">
</form>
