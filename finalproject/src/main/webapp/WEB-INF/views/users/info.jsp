<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	
	$(function(){
		
		$("#file").hide();
		$("#clear").hide();
		$("#delete").hide();
		$("#basic").hide();
		
		$("#profile_edit").click(function(){
			
			$("#file").show();
			$("#clear").show();
			$("#delete").show();
			$("#basic").show();
			$("#profile_edit").hide();
			
			var data = $(this).parent().find(".editfile").serialize(); 
			console.log("data = ", data);
			
			$("#clear").click(function(e){
				$.ajax({
					url:"${pageContext.request.contextPath}/users/profile_edit",
					type:"post",
					data:data,
					dataType:"text",
					success:function(resp){
					}
				});
			});
		});
	});
	
	$('#myModal').on('shown.bs.modal', function () {
		  $('#myInput').trigger('focus')
		})
	
</script>
<style>
.footer-color {
    margin-top: 140px;
}

</style>
<div class="container">
<h1>회원 정보</h1>
<jsp:include page="/WEB-INF/views/template/sidebar.jsp"></jsp:include>
<table class="table table-hover">
  <tr>
    <th>
    	프로필<br>
    </th>
    <td class="mom">
<!--     	기본이미지 -->
    <c:choose>
    	<c:when test="${empty userFileDto}">
	    	<img src="userimg?user_no=266" id="img" width="120" height="120"><br>
    	</c:when>
    	<c:otherwise>
	    	<img src="userimg?user_no=${users.user_no}" id="img" width="120" height="120"><br>
    	</c:otherwise>
    </c:choose>
	    
	    <c:if test="${isMine && !isAdmin}">
	    	<button type="button" class="btn btn-secondary" id="profile_edit"><i class="fa fa-edit"></i>수정하기</button>
	    </c:if>
	    <c:if test="${not empty userFileDto}">
		    <form class="editfile" action="profile_delete" method="post">
	    		<input type="submit" class="btn btn-primary" id="basic" value="기본이미지로 변경">	
		    </form>
	    </c:if>
	    <form class="editfile" action="profile_edit" method="post" enctype="Multipart/form-data">
	    	<input id="file" type="file" name="user_file" accept="image/*" required>
    		<input type="submit" class="btn btn-primary" id="clear" value="완료">	
    		<a href="${context}/users/info"><button type="button" class="btn btn-primary" id="delete">취소</button></a>
	    </form>
    </td>
  </tr>
  <tr>
    <th>이름</th>
    <td>${users.n
    
    ame}</td>
  </tr>
  <tr>
    <th>아이디</th>
    <td>${users.id}</td>
  </tr>
  <tr>
    <th>이메일</th>
    <td>${users.email1}${users.email2}</td>
  </tr>
  <tr>
    <th>전화번호</th>
    <td>${fn:replace(users.phone, ",", "-") }</td>
  </tr>
  <tr>
    <th>주소</th>
    <td>${users.postcode} ${users.address} ${users.detailAddress} ${users.extraAddress}</td>
  </tr>
  <tr>
    <th>토큰</th>
    <td>
    	${users.point}
    	<c:if test="${isMine && !isAdmin}">
    		<a href="${context}/pay/list"><button class="btn btn-secondary"><i class="fas fa-charging-station"></i>충전하기</button></a>
    	</c:if>
    </td>
  </tr>
  <tr>
    <th>포인트</th>
    <td>${users.grade_point}</td>
  </tr>
  <tr>
    <th>등급</th>
    <td>${users.grade}
    	<c:if test="${users.is_premium == 1}">
    		(프리미엄)
    	</c:if>
    </td>
  </tr>
  <tr>
    <th>가입일</th>
    <td>${users.join_date.substring(0,10)}</td>
  </tr>
</table>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>