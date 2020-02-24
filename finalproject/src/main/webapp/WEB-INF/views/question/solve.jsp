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
<script>
$(function(){
	//제출 확인 기능
	$(".result").hide();
	$(".submitExam").click(function(){
		var result = confirm("제출하시겠습니까?");
		if(result){
			$(this).form.submit();
		}else{
			return false;			
		}
	});
});
</script>
<div class="container">
	<h1>${questionDto.question_title}</h1>
	<hr>
	<div class="d-flex justify-content-between">
		<div>
			<div>문제 내용 : ${questionDto.question_content}</div><br>
			<c:if test="${image !=null}">
				<c:forEach var="image" items="${image}">
					<div><img src="image?question_file_no=${image.question_file_no}" width="500	" height="300"></div>
				</c:forEach>
			</c:if>
			<form action="solve" method="post">
				<input type="hidden" name="id" value="${id}">  
				<input type="hidden" id="hourResult" name="hour">  
				<input type="hidden" id="minResult" name="min">
				<input type="hidden" id="secResult" name="sec">
				<input type="hidden" id="milisecResult" name="milisec">
				<input type="hidden" name="question_no" value="${questionDto.question_no}"><br>
					<input id="result[0]" type="radio" name="question_answer" value="1">1. ${questionDto.answer1}<br>
					<input id="result[1]" type="radio" name="question_answer" value="2">2. ${questionDto.answer2}<br>
					<input id="result[2]" type="radio" name="question_answer" value="3">3. ${questionDto.answer3}<br>
					<input id="result[3]" type="radio" name="question_answer" value="4">4. ${questionDto.answer4}<br>
					<input id="result[4]" type="radio" name="question_answer" value="5">5. ${questionDto.answer5}<br><br>
					<input type="submit" class="submitExam btn btn-primary" value="제출하기">
			</form>
		</div>
		<div class="note">
			<textarea title="연습장" rows="20" cols="40">연습장</textarea>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>