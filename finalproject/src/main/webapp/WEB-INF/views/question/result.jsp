<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   
   <!-- 필요 정보: rno,  category no, tno-->
   
<h1>끝</h1>
<h1>점수 ${score}</h1>

<h4>
<c:forEach var="alist" items="${answerList}">
	${alist.answer} 
</c:forEach>
</h4>

<h2>입력한 답</h2>

<h4>
	<c:forEach var="rlist" items="${rCorrectDto}">
	${rlist.correct} 
	</c:forEach>
</h4>





<input type = "button" value = "결과 확인" onclick="resultcheck()">