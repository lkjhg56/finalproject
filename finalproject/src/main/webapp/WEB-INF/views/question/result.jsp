<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   
   <!-- 필요 정보: rno,  category no, tno-->
   
<h1>시험결과</h1>
<h5>점수 ${score}</h5>
<h5>풀린 횟수 ${solved}</h5>
<h5>이시험 평균 점수 ${average}</h5>
<h5>상위 10% 평균 :${high10average}</h5>
<h5>상위 25% 평균 :${high25average}</h5>
<h5>상위 50% 평균 :${high50average}</h5>
<h5>상위 75% 평균 :${high75average}</h5>
<h5>순위 : ${rank} 백분위 : ${percentile}</h5>

<h4>
<c:forEach var="alist" items="${answerList}">


<%-- 	${alist.question} <br>
	${alist.dis1}<br>
	${alist.dis2}<br>
	${alist.dis3}<br>
	${alist.dis4}<br>
	${alist.dis5}<br><br>    
	 --%>
	
	
	
	
			<div class = "plural-question">
	   	
	
	   							<h2>${alist.question}</h2><h6>${alist.rate}%</h6>
	
	 								<div class = "questionin_wrap">
			   							<div class = "questionin_box"><h4><input class = "ans" type="text" name="${alist.no}" id="${alist.no}1" ></h4></div><div class = "questionin_dis"><h4>${alist.dis1}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="text" name="${alist.no}" id="${alist.no}2" ></h4></div><div class = "questionin_dis"><h4>${alist.dis2}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="text" name="${alist.no}" id="${alist.no}3" ></h4></div><div class = "questionin_dis"><h4>${alist.dis3}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="text" name="${alist.no}" id="${alist.no}4" ></h4></div><div class = "questionin_dis"><h4>${alist.dis4}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="text" name="${alist.no}" id="${alist.no}5" ></h4></div><div class = "questionin_dis"><h4>${alist.dis5}</h4></div>
									</div>
									<br>
	   								<br>
	   								<br>
	   					</div>    
</c:forEach>
</h4>


<h2>입력한 답</h2>

<h4>
	<c:forEach var="rlist" items="${rCorrectDto}">
	${rlist.correct} 
	</c:forEach>
</h4>



