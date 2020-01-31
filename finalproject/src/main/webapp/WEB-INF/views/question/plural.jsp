<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   
   <c:forEach var="qlist" items ="${clist}">
   <div>
   		<h2>${qlist.question}</h2>
   		
   		<h4>${qlist.dis1}</h4>
   		<h4>${qlist.dis2}</h4>
   		<h4>${qlist.dis3}</h4>
   		<h4>${qlist.dis4}</h4>
   		<h4>${qlist.dis5}</h4>
   		
   </div>
   
   </c:forEach>