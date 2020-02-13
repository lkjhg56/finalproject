<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<a href="${pageContext.request.contextPath }/question/questcategory2">한문제씩 풀기</a>

<a href="${pageContext.request.contextPath }/question/questcategory">여러개씩 풀기</a>

<c:forEach var = "list" items="${clist}">
   
   <div>
 	<form action = "${pageContext.request.contextPath }/question/questcategory">

	<input type = "hidden" name ="categoryname"  value="">
 	
	<input type = "submit" value = "여러개씩 풀기" >	
 	</form>
 	
 	 </div>
   
   

 </c:forEach>
 