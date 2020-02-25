<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
function previewImg(target){
	if(target.files && target.files[0]){
		var reader = new FileReader();
		reader.onload = function(data){
			var img = document.querySelector(".preview");
			img.src = data.target.result;
		}
		reader.readAsDataURL(target.files[0])
	}
}
</script>
<script>
	$(function(){
		var x = setInterval(timer,10);
	});
    //초기값
	var milisec = 0;
	var sec = 0; 
	var min = 0;
	var hour = 0;
	//저장될 변수(실시간으로 저장됨.)
	var miliSecOut = 0;
	var secOut = 0;
	var minOut = 0;
	var hourOut = 0;
	function timer(){
		miliSecOut = checkTime(milisec);
        secOut = checkTime(sec);
        minOut = checkTime(min);
        hourOut = checkTime(hour);  
        milisec = ++milisec;
        if (milisec === 100) {
            milisec = 0;
            sec = ++sec;
        }
        if (sec == 60) {
            min = ++min;
            sec = 0;
        }
        if (min == 60) {
            min = 0;
            hour = ++hour;
        }
        document.getElementById("milisecResult").value = miliSecOut;
        document.getElementById("secResult").value = secOut;
        document.getElementById("minResult").value = minOut;
        document.getElementById("hourResult").value = hourOut;
	}
	function checkTime(i){
		if(i<10){
			i = "0" + i;
		}
		return i;
	}
</script>
<script>
$(function(){
	var size = 1; //한 페이지에 출력될 문제 개수
	var questionCount = $('.inputDiv').length; //출제된 문제 개수
	var totalPage = Math.floor((questionCount-1)/size);
// 	console.log("questionCount : "+questionCount);
// 	console.log("totalpage : "+totalPage);
	for(var i=0; i <=totalPage; i++){
		$("<div>").addClass("paging-inner").attr("data-no", i).appendTo("#paging");
	}
	$("#paging").find(".inputDiv").each(function(index){
		var dest = parseInt(index/size);
		$("#paging").find(".paging-inner").eq(dest).append($(this));		
	});
	$(".paging-inner").each(function(i){
		var buttonWrapper = $("<div>").addClass("btn-wrapper d-flex justify-content-between");
		var inputWrapper = $("<div>").addClass("btn-group btn-group-lg input-wrapper d-flex justify-content-center");
		$(this).append(buttonWrapper);
		$(this).append(inputWrapper);
	});
	$(".btn-wrapper").each(function(i){
		var len = $(".paging-inner").length;
		var nextButton = $("<div>").addClass("next btn-group btn-group-lg");
		var prevButton = $("<div>").addClass("prev btn-group btn-group-lg");	
		if(len == 1){
	//			console.log("한페이지니까 추가 안함");
			return false;
		}
		else if(i == 0){
	//			console.log("처음 : 다음만 추가")
			$(this).append(nextButton);
		}
		else if(i == $(".paging-inner").length - 1){
	//			console.log("마지막 : 이전만 추가")
			$(this).append(prevButton);
		}
		else{
	//			console.log("이전 다음 둘다 추가");
			$(this).append(prevButton);
			$(this).append(nextButton);
		}	
	});
	
	$("<input>").attr("type","submit").attr("value","시험 종료").addClass("submitExam btn btn-secondary").appendTo(".input-wrapper");

	//버튼 모양 추가
	$(".next").html("<button type='button' class='btn btn-primary'>다음</button>");
	$(".prev").html("<button type='button' class='btn btn-primary'>이전</button>");
	
	//페이지 맨 처음 출력
	pageChange(0, totalPage);

	//next 기능	
	$(".next").click(function(){
		//index를 구해서 pageChanage 호출
		var div = $(this).parent().parent();
		var no = div.data("no");
		pageChange(no + 1, totalPage);
		$('html,body').scrollTop(0);
	});		
	//prev 기능
	$(".prev").click(function(){
		var div = $(this).parent().parent();
		var no = div.data("no");
		pageChange(no - 1, totalPage);
		$('html,body').scrollTop(0);
	});
	//제출 확인 기능
	$(".submitExam").click(function(){
		var result = confirm("제출하시겠습니까?");
		if(result){
			$(this).form.submit();
		}else
			return false;
	});
	
	
});

//페이지 출력 기능	
function pageChange(index, totalPage){
	//index 위치만 표시하고 다 숨김
	$(".paging-inner").hide();
	$(".paging-inner").eq(index).show();
}
</script>
<style>
	.input-wrapper{
		margin-top : 30px;
	}
 	.btn-wrapper{
 		font-size : 20px;
 		margin-top : 30px;
 		padding-right : 180px;
 		padding-left : 180px;
 	}
</style>
<div class="container">
	<div><h1>여러문제 풀기</h1></div><hr>
	<!-- 디자인하기 -->
		<!-- 리스트로 받아온다. 리스트 내에는 Question_no을 포함한 문제의 DTO를 가진다. -->
		<form action="multi" method="post">
			<input type="hidden" id="hourResult" name="hour">  
			<input type="hidden" id="minResult" name="min">
			<input type="hidden" id="secResult" name="sec">
			<input type="hidden" id="milisecResult" name="milisec">
			<input type="hidden" name = "countData" id="countData" value="${count}">
				<div id="paging">
				<c:forEach var="questionDto" items="${list}" varStatus="status">				
					<div class="inputDiv d-flex justify-content-between">
					<div><textarea title="연습장" rows="22" cols="45">연습장</textarea></div>
					<div>
					<h4>문제 ${status.count}</h4>
					<c:choose>
						<c:when test="${questionDto.correct_ratio<40}">
							<font color="red">정답률 : ${questionDto.correct_ratio}% (어려움)</font>
						</c:when>
						<c:when test="${questionDto.correct_ratio>=40 && questionDto.correct_ratio<=60}">
							<font color="#FFBB00">정답률 : ${questionDto.correct_ratio}% (보통)</font>
						</c:when>
						<c:otherwise>
							<font color="blue">정답률 : ${questionDto.correct_ratio}% (쉬움)</font>
						</c:otherwise>
					</c:choose>
					<br>
					<span>${questionDto.question_title}</span><br>
					
					<c:if test="${questionDto.files !=null}">
					<c:forEach var="image" items="${questionDto.files}">
						<img class="preview" src="image?question_file_no=${image.question_file_no}" width="500" height="300"><br><br>
					</c:forEach>
					</c:if>
					
					<span>${questionDto.question_content}</span>
					<input type="hidden" name="question[${status.index}].id" value="${id}">  
					<input type="hidden" name="question[${status.index}].no" value="${questionDto.question_no}"><br><br>
					<input type="radio" name="question[${status.index}].answer" value="1">1. ${questionDto.answer1}<br>
					<input type="radio" name="question[${status.index}].answer" value="2">2. ${questionDto.answer2}<br>
					<input type="radio" name="question[${status.index}].answer" value="3">3. ${questionDto.answer3}<br>
					<input type="radio" name="question[${status.index}].answer" value="4">4. ${questionDto.answer4}<br>
					<input type="radio" name="question[${status.index}].answer" value="5">5. ${questionDto.answer5}<br><br>
					</div>
					</div>		
				</c:forEach>
				</div>
		</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>