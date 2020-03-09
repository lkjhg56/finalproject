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




$(function () {
	  $(".test").hide()
});


function file(abc) {
	console.warn("show")
	var checking = document.getElementsByName(abc);
$(checking).show();
	
	
}






function deleteq() {
	//삭제 알림창
            console.warn("문제삭제");
      /*       e.preventDefault() */
      
     alert("문제가 삭제됩니다")

      
	
	 
}
</script>
<style>

	th{
		white-space : nowrap;
	}
</style>
<div class="container">
<h4>문제 번호 : ${questionDto.no}</h4>

<table class="table">
<tbody>
<tr>
<th>문제 제목</th>
<td> ${questionDto.question}</td>
 
</tr>

<tr>
<th>이미지</th>
<td>

<img id="preview" class="test" name="${questionDto.no}+pic" src="qimage?no=${questionDto.no}" width="35%" height="300" onload="file('${questionDto.no}+pic');">
</td>

</tr>

<tr>
<th>문제 보기</th>
<td>

문제 보기1 : ${questionDto.dis1}<br>
문제 보기2 : ${questionDto.dis2}<br>
문제 보기3 : ${questionDto.dis3}<br>
문제 보기4 : ${questionDto.dis4}<br>
문제 보기5 : ${questionDto.dis5}

</td>


</tr>


<tr>
<th>문제 정답</th>
<td>
${questionDto.answer}

</td>

</tr>


<tr>
<th>문제 해설</th>
<td>
${questionDto.solution}
</td>

</tr>



<tr>
<th>카테고리넘버</th>
<td>
${questionDto.category_no}
</td>

</tr>



<tr>
<th>카테고리</th>
<td>
${questionDto.csname}
</td>

</tr>



	</tbody>
</table>
<div>


<button type="button" onclick="location.href='normalupdate?no=${questionDto.no}'">문제 수정하기</button><br><br>

<%-- <a href="normaldelete?no=${questionDto.no}&csname=${questionDto.csname}">문제 삭제</a><br><br> --%>
<button type="button" onclick="location.href='normaldelete?no=${questionDto.no}&csname=${questionDto.csname}';deleteq();">문제 삭제하기</button><br><br>

<%-- <a href="admin_normal_question?csname=${questionDto.csname}&category_no=${questionDto.category_no}">문제 리스트로</a><br><br> --%>
<button type="button" onclick="location.href='admin_normal_question?csname=${questionDto.csname}&category_no=${questionDto.category_no}'">리스트로 돌아가기</button>

</div>

</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>