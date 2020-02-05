<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<h1>list</h1>

<form action="list" method="post">
	<input type="submit" name="board_category" value="전체">
	<input type="submit" name="board_category" value="공지">
	<input type="submit" name="board_category" value="자유">
	<input type="submit" name="board_category" value="질문">
	<input type="submit" name="board_category" value="업데이트"><br><br>
</form>

<c:forEach var="list" items="${list}">
	${list.board_no } 
	${list.board_category} 
	<a href=${pageContext.request.contextPath}/board/content?board_no=${list.board_no}>${list.board_title }</a> 
	[${list.board_replycount }] 
	${list.board_writer } 
	${list.board_wdate }
	<br>
</c:forEach>

<a href=${pageContext.request.contextPath}/board/regist>글쓰기</a>