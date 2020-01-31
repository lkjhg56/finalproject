<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <%@taglib	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  
   <c:forEach var = "list" items="${list}">
   
   <div>
 	<form action = "${pageContext.request.contextPath }/question/questcategory">

	<input type = "hidden" name ="categoryname" value= "${list.csname}">
 	<div>${list.csname}</div>
	<input type = "submit" value = "선택">	
 	</form>
 	 </div>
   
   

 </c:forEach>
 