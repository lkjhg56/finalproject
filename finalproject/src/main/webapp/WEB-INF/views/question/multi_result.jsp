<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
	$(function(){
		$(".multi").hide();
		$(".result").attr("disable",false);
		$(".result").css("background-color","white");
		$(".result").css("color","black");
		$(".result").css("border-color","white");
		
		$(".detail").click(function(){
			$(".multi").show();
			$(".single").hide();
			
			$(".result").attr("disable",false);
			$(".result").css("background-color","#17a2b8");
			$(".result").css("color","white");
			$(".result").css("border-color","#17a2b8");
			
			$(".detail").attr("disable",true);
			$(".detail").css("background-color","white");
			$(".detail").css("color","black");
			$(".detail").css("border-color","white");
		});
		$(".result").click(function(){
			$(".multi").hide();
			$(".single").show();
			
			$(".result").attr("disable",true);
			$(".result").css("background-color","white");
			$(".result").css("color","black");
			$(".result").css("border-color","white");
			
			$(".detail").attr("disable",false);
			$(".detail").css("background-color","#17a2b8");
			$(".detail").css("color","white");
			$(".detail").css("border-color","#17a2b8");
		});
	});
</script>
<style>
	.single{
	padding : 8px 16px;
	}
	.result,.detail{
	background-color: #17a2b8;
    color: white;
    border-color: #17a2b8;

    padding: .5rem 1rem;
    font-size: 1.25rem;
    line-height: 1.5;
    border-radius: .3rem;
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    font-weight: 800;
    letter-spacing: 1px;
    text-transform: uppercase;
    font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    user-select: none;
    border: 1px solid transparent;
 
	}
</style>
<div class="container">
<!-- 결과, 상세 버튼 -->
<div class="jumbotron"><h3>수험 시간</h3><br>
<c:if test="${time.hour!=0}">${time.hour}시간</c:if> ${time.min}분 ${time.sec}초
</div>

<div class="btn-group-lg d-flex justify-content-start">
<button class="result"><h4>시험 결과</h4></button><button class="detail"><h4>상세 결과</h4></button>
</div>
<hr>
<!-- 결과 페이지 -->
<div class="container single">

<h4>총 문제수</h4>
${multi.total_question} 문제<br><br>
<h4>정답 개수</h4> 
${multi.correct_count} 개<br><br>
<h4>점수</h4>
${multi.sum_score}점<br><br>
<hr>
</div>

<!-- 상세 페이지 -->
<div class="container result-wrapper multi">
<c:forEach var="result" items="${list}" varStatus="status">
	<div class="resultList">
		<h4>문제 ${status.count}</h4>
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
		<br>
		<h4>- 문제 해설-</h4> 
		${result.question_solution}
<hr>
	</div>
</c:forEach>
<c:set var="count" value="${status.count}"></c:set>
</div>

<div>
<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}">처음 페이지로</a>&nbsp;&nbsp;
<a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/question/list">유저 문제 목록으로</a>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>