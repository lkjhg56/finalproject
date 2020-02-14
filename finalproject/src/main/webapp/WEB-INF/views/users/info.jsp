<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
	//탈퇴
	$(function(){
		
		$("#bye").click(function(e){
			window.alert("정말 탈퇴하시겠습니까?");
		});
		
	});
	
</script>
<h1>회원 정보</h1>

<table class="info" border="1">
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
    <th>주소</th>
    <td>${users.postcode} ${users.address} ${users.detailAddress} ${users.extraAddress}</td>
  </tr>
  <tr>
    <th>전화번호</th>
    <td>${users.phone}</td>
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
    <td>${users.join_date}</td>
  </tr>
</table>
<a href="${context}/users/change">정보 수정하기</a><br>
<a href="${context}/users/test_result">내가 본 시험 내역</a><br>
<a href="${context}/users/grade_point_rank">등급 포인트 랭킹</a><br>
<a href="${context}/users/test_point">포인트 줘보기 test</a><br>
<a id="bye" href="${context}/users/bye">탈퇴하기</a><br>
<a href="${context}/users/logout">로그아웃</a><br>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>