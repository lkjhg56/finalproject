<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <h1>test</h1>
 <c:forEach var = "test" items="${list}">
 	<div>
 	<form action = "${pageContext.request.contextPath }/question/questype">
 		${test.test_category}
 	</form>
 	 </div>
 </c:forEach>
 