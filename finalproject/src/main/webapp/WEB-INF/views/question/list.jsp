<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:forEach var="question" items="${list}">
		<div>
			${question.question_no}
			${question.question_content}
		</div>	
</c:forEach>