<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<h1>회원 정보</h1>

<h3>이름 : ${users.name}</h3> 
<h3>아이디 : ${users.id}</h3>
<h3>비밀번호 : ********</h3>
<h3>이메일 : ${users.email}</h3> 
<h3>cash : ${users.cash}</h3> 
<h3>point : ${users.point}</h3> 
<h3>grade_point : ${users.grade_point}</h3> 
<h3>가입일 : ${users.join_date}</h3> 
<h3>전화번호 : ${users.phone}</h3> 
<h3>등급 : ${users.grade}</h3> 
<h3>주소 : ${users.postcode} ${users.address} ${users.detailAddress} ${users.extraAddress}</h3>
<a href="${context}/users/change">정보 수정하기</a><br>
<a href="${context}/users/test_result">내가 본 시험 내역</a><br>
<a href="${context}/users/grade_point_rank">등급 포인트 랭킹</a><br>
<a href="${context}/users/test_point">포인트 줘보기 test</a><br>
<a href="${context}/users/bye">탈퇴하기</a><br>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>