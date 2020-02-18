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
<div>문제 번호 : ${questionDto.no}</div>
<div>문제 제목 : ${questionDto.question}</div>

<c:if test="${grade=='관리자'}">
<div>유료 여부    : ${questionDto.question_premium}</div>
</c:if>
<div><img id="preview" src="qimage?no=${questionDto.no}"  width="120" height="120"></div>

<div>문제 보기1 : ${questionDto.dis1}</div>
<div>문제 보기2 : ${questionDto.dis2}</div>
<div>문제 보기3 : ${questionDto.dis3}</div>
<div>문제 보기4 : ${questionDto.dis4}</div>
<div>문제 보기5 : ${questionDto.dis5}</div>
<%-- <c:if test="${grade=='관리자'}"> --%>
<div>문제 정답 : ${questionDto.answer}</div>
<div>문제 해설 : ${questionDto.solution}</div>
<%-- </c:if> --%>

<div>카테고리 : ${questionDto.csname}</div>
<br><br>
<div>
<%-- <c:if test="${grade=='관리자'}"> --%>
<a href="normalupdate?no=${questionDto.no}">문제 수정</a><br><br>
<a href="normaldelete?no=${questionDto.no}&csname=${questionDto.csname}&category_no=${questionDto.category_no}">문제 삭제</a><br><br>
<a href="normallist?no=${questionDto.no}&csname=${questionDto.csname}&category_no=${questionDto.category_no}">문제 리스트로</a><br><br>
<%-- </c:if> --%>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>