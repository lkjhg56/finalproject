<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
회원 목록 :<br><br>

<a href="${pageContext.request.contextPath}/admin/admin_user_question">유저 문제 목록(完)</a><br><br>


<a href="${pageContext.request.contextPath}/admin/admin_normal_question">일반 문제 목록</a><br><br>

<a href="${pageContext.request.contextPath}/admin/admin_normal_upload">일반 문제 업로드</a><br><br>

<a href="${pageContext.request.contextPath}/admin/blacklist">게시판 관리</a><br><br>

결제관련 기능(추가해주세요.)<br><br>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>