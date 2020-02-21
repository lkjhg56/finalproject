<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	//탈퇴
	$(function(){
		
		$("#bye").click(function(e){
			window.alert("정말 탈퇴하시겠습니까?");
		});
		
	});
	
	$(function(){
		
		$("#file").hide();
		$("#clear").hide();
		
		$("#profile_edit").click(function(){
			
			$("#file").show();
			$("#clear").show();
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
	
	
</script>
<h1>회원 정보</h1>

<table class="table" border="1">
  <tr>
    <th>
    	프로필<br>
    </th>
    <td class="mom">
	    <img src="userimg?user_no=${users.user_no}" id="img" width="120" height="120"><br>
	    <c:if test="${isMine}">
	    <button type="button" id="profile_edit">수정하기</button>
	    </c:if>
	    <form class="editfile" action="profile_edit" method="post" enctype="Multipart/form-data">
	    <input id="file" type="file" name="user_file" accept="image/*" required>
    	<input type="submit" id="clear" value="수정 완료">	
	    </form>
    </td>
  </tr>
  <tr>
    <th>이름</th>
    <td>${users.name}</td>
  </tr>
  <tr>
    <th>아이디</th>
    <td>${users.id}</td>
  </tr>
  <tr>
    <th>비밀번호</th>
    <td>********</td>
  </tr>
  <tr>
    <th>이메일</th>
    <td>${users.email}</td>
  </tr>
  <tr>
    <th>전화번호</th>
<%--     <td>${users.phone.substring(0,3)}-${users.phone.substring(4,8)}-${users.phone.substring(9,13)}</td> --%>
    <td>${users.phone}</td>
  </tr>
  <tr>
    <th>주소</th>
    <td>${users.postcode} ${users.address} ${users.detailAddress} ${users.extraAddress}</td>
  </tr>
  <tr>
    <th>포인트</th>
    <td>${users.point}</td>
  </tr>
  <tr>
    <th>등급 포인트</th>
    <td>${users.grade_point}</td>
  </tr>
  <tr>
    <th>등급</th>
    <td>${users.grade}</td>
  </tr>
  <tr>
    <th>가입일</th>
    <td>${users.join_date.substring(0,10)}</td>
  </tr>
</table>
<a href="${context}/users/change">정보 수정하기</a><br>
<c:choose>
<c:when test="${isAdmin}">
<a href="${context}/users/user_list">회원 목록</a><br>
<a href="${pageContext.request.contextPath}/question/normalupload">일반문제 업로드</a><br>
<a href="${pageContext.request.contextPath}/question/normallist">일반문제 List</a><br>
<a href="${pageContext.request.contextPath}/admin/main">admin page</a><br>
</c:when>
<c:otherwise>
<a href="${pageContext.request.contextPath}/question/upload">문제 업로드</a><br>
<a href="${context}/question/my_upload_list">내가 업로드한 문제</a><br>
</c:otherwise>
</c:choose>

<a href="${context}/users/test_result">내가 본 시험 내역</a><br>
<a href="${context}/users/my_grade_point">나의 포인트 내역</a><br>
<a href="${context}/users/grade_point_rank">등급 포인트 랭킹</a><br>
<a href="${context}/users/test_point">포인트 줘보기 test</a><br>
<a id="bye" href="${context}/users/bye">탈퇴하기</a><br>
<a href="${context}/users/logout">로그아웃</a><br>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>