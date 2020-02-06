<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
function previewImage(target){    
    if(target.files && target.files[0]){       
        var reader = new FileReader();
        reader.onload = function(data){
            var img = document.querySelector("#preview");
            img.src = data.target.result;    
        }
        reader.readAsDataURL(target.files[0]);
    }
}
</script>
<script>
	//페이지 시작시 timer 함수를 trigging 한다.
    $(function(){
        var x;
        x = setInterval(timer, 10);
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
    	    
    //스톱워치
    function timer() {        
        miliSecOut = checkTime(milisec);
        secOut = checkTime(sec);
        minOut = checkTime(min);
        hourOut = checkTime(hour);        
        milisec = ++milisec;//밀리초부터 시작하며 100이되면 0으로 변환되고 초에 1을 더한다.
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
    function checkTime(i) {
        if (i < 10) {
            i = "0" + i;
        }
        return i;
    }
</script>
<h1>문제 풀기</h1>    
<div>문제 번호 : ${questionDto.question_no}</div>
<div>출제자 : ${questionDto.id}</div>
<div>카테고리 : ${questionDto.category_name}</div>
<div>문제 제목 : ${questionDto.question_title}</div>
<div>문제 내용 : ${questionDto.question_content}</div>
<img id="preview" src="image?question_no=${questionDto.question_no}" width="120" height="120"><br><br>
<form action="solve" method="post">
	<input type="hidden" name="id" value="${id}">  
	<input type="hidden" id="hourResult" name="hour">  
	<input type="hidden" id="minResult" name="min">
	<input type="hidden" id="secResult" name="sec">
	<input type="hidden" id="milisecResult" name="milisec">
	<input type="hidden" name="question_no" value="${questionDto.question_no}">
		<input id="result[0]" type="radio" name="question_answer" value="1">1. ${questionDto.answer1}
		<input id="result[1]" type="radio" name="question_answer" value="2">2. ${questionDto.answer2}
		<input id="result[2]" type="radio" name="question_answer" value="3">3. ${questionDto.answer3}
		<input id="result[3]" type="radio" name="question_answer" value="4">4. ${questionDto.answer4}
		<input id="result[4]" type="radio" name="question_answer" value="5">5. ${questionDto.answer5}<br>
<input type="submit" value="제출하기">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>