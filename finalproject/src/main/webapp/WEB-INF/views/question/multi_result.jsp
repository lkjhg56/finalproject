<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
// 	$(function(){
// 		$(".multi").hide();
// 		$(".result").hide();
		
// 		$(".detail").click(function(){
// 			$(".multi").show();
// 			$(".result").show();
// 			$(".detail").hide();
// 			$(".single").hide();
// 		});
		
// 		$(".result").click(function(){
// 			$(".multi").hide();
// 			$(".result").hide();
// 			$(".detail").show();
// 			$(".single").show();
// 		});
		
// 	});
	$(function(){
		$(".multi").hide();
		$(".result").attr("disable",false);
		$(".result").css("background-color","white");
		$(".result").css("color","black");
		$(".result").css("border-color","white");
		$(".detail").click(function(){
			$(".multi").show();
			$(".single").hide();
			$(".detail").attr("disable",true);
			$(".result").attr("disable",false);
			$(".result").css("background-color","white");
			$(".result").css("color","black");
			$(".result").css("border-color","white");
			$(".detail").css("background-color","#118FCB");
			$(".detail").css("color","white");
		});
		$(".result").click(function(){
			$(".multi").hide();
			$(".single").show();
			$(".result").attr("disable",true);
			$(".detail").attr("disable",false);
			$(".detail").css("background-color","white");
			$(".detail").css("color","black");
			$(".detail").css("border-color","white");
			$(".result").css("background-color","#118FCB");
			$(".result").css("color","white");
		});
		
	});
</script>
<div class="container">
<div>수험 시간 : <c:if test="${time.hour!=0}">${time.hour}시간</c:if> ${time.min}분 ${time.sec}초</div><br>
<!-- 결과, 상세 버튼 -->
<div class="btn-group-lg d-flex justify-content-start">
<button class="btn btn-primary result">시험 결과</button><button class="btn btn-primary detail">상세 결과</button>
</div>
<!-- 결과 페이지 -->
<div class="container single">

총 문제수 / 정답율 :&nbsp; ${multi.total_question}/${multi.correct_count}<br>
점수 : ${multi.sum_score}
</div>
<!-- 상세 페이지 -->
<div class="container result-wrapper multi">
<c:forEach var="result" items="${list}" varStatus="status">
	<div class="results">
		문제 ${status.count}<br>
		입력한 정답 :
		<c:choose>
			<c:when test="${result.question_answer==0}">
				<font color="red">답을 적지 않으셨습니다.</font><br>
			</c:when>
			<c:otherwise>
				${result.question_answer}<br>
			</c:otherwise>
		</c:choose>  
		정답 여부 : 
		<c:choose>
			<c:when test="${result.result==1}">
				정답<br>
			</c:when>
			<c:otherwise>
				<font color="red">오답</font><br>
			</c:otherwise>
		</c:choose>
	</div>
</c:forEach>
<c:set var="count" value="${status.count}"></c:set>
</div>
<div>
<a href="${pageContext.request.contextPath}">처음 페이지로</a>
<a href="${pageContext.request.contextPath}/question/list">유저 문제 목록으로</a>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>