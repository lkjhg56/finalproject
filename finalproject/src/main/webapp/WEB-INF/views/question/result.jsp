<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   
   <!-- 필요 정보: rno,  category no, tno-->
   
<h1>끝</h1>
<h5>점수 ${score}</h5>
<h5>이시험 평균 점수 ${average}</h5>
<h5>상위 10% 점수 :${high10average}</h5>
<h5>상위 25% 점수 :${high25average}</h5>
<h5>상위 50% 점수 :${high50average}</h5>
<h5>순위 : ${rank} 백분위 : ${percentile}</h5>

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