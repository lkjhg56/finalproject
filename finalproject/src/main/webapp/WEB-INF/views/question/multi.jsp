<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:include page="/WEB-INF/views/template/mheader.jsp"></jsp:include>
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
	var size = 4; //한 페이지에 출력될 문제 개수
	var questionCount = $('.inputDiv').length; //출제된 문제 개수
	var totalPage = Math.ceil((questionCount-1)/size); //총 페이지 개수 : 현재 3
	//div로 size별로 묶기.
	for(var i=0; i < totalPage; i++){
		$("<div>").addClass("paging-inner").attr("data-no", i).appendTo("#paging");
	}
	$("#paging").find(".inputDiv").each(function(index){
		var dest = parseInt(index/size);
		$("#paging").find(".paging-inner").eq(dest).append($(this));		
	});
		
	$("<div>").addClass("next").appendTo(".paging-inner");		
	$("<div>").addClass("prev").appendTo(".paging-inner");
	$("<input>").attr("type","submit").attr("value","제출하기").appendTo(".paging-inner");

	//버튼 모양 추가
	$(".next").html("<button type='button'>다음</button>");
	$(".prev").html("<button type='button'>이전</button>");
	
	//페이지 맨 처음 출력
	pageChange(0, totalPage);

	//next 기능	
	$(".next").click(function(){
		//index를 구해서 pageChanage 호출
		var div = $(this).parent();
		var no = div.data("no");
		pageChange(no + 1, totalPage);
	});		
	//prev 기능
	$(".prev").click(function(){
		var div = $(this).parent();
		var no = div.data("no");
		pageChange(no - 1, totalPage);
	});

});

//페이지 출력 기능	
function pageChange(index, totalPage){
	//index 위치만 표시하고 다 숨김
	$(".paging-inner").hide();
	$(".paging-inner").eq(index).show();

		if(totalPage==0){
			$(".next").hide();
			$(".prev").hide();
		}else if(index==0){
			$(".prev").hide();
		}else if(index==totalPage-1){
			$(".next").hide();
		}else{
			$(".next").show();
			$(".prev").show();
		}			
}

</script>
<script>

</script>
<h1>여러문제 풀기</h1>
<!-- 리스트로 받아온다. 리스트 내에는 Question_no을 포함한 문제의 DTO를 가진다. -->
<form action="multi" method="post">
	<input type="hidden" id="hourResult" name="hour">  
	<input type="hidden" id="minResult" name="min">
	<input type="hidden" id="secResult" name="sec">
	<input type="hidden" id="milisecResult" name="milisec">
	<input type="hidden" name = "countData" id="countData" value="${count}">
<div id="paging">
<c:forEach var="questionDto" items="${list}" varStatus="status">
<div class="inputDiv">
문제 ${status.count}<br>
<span>문제 : ${questionDto.question_title}</span><br>
<img class="preview" src="image?question_no=${questionDto.question_no}" width="120" height="120"><br><br>
<span>문제 내용 : ${questionDto.question_content}</span>
	<input type="hidden" name="question[${status.index}].id" value="${id}">  
	<input type="hidden" name="question[${status.index}].no" value="${questionDto.question_no}"><br><br>
		<input type="radio" name="question[${status.index}].answer" value="1">1. ${questionDto.answer1}<br>
		<input type="radio" name="question[${status.index}].answer" value="2">2. ${questionDto.answer2}<br>
		<input type="radio" name="question[${status.index}].answer" value="3">3. ${questionDto.answer3}<br>
		<input type="radio" name="question[${status.index}].answer" value="4">4. ${questionDto.answer4}<br>
		<input type="radio" name="question[${status.index}].answer" value="5">5. ${questionDto.answer5}<br><br>
</div>		
</c:forEach>
</div>
</form>
<jsp:include page="/WEB-INF/views/template/mfooter.jsp"></jsp:include>