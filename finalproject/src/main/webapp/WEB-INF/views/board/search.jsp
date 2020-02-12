<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>   

<div align="center">    

<h1>게시글 목록</h1>

	<form action="list" method="post">
		<input type="submit" name="board_category" value="전체">
		<input type="submit" name="board_category" value="공지">
		<input type="submit" name="board_category" value="자유">
		<input type="submit" name="board_category" value="질문">
		<input type="submit" name="board_category" value="업데이트"><br><br>
	</form>
    
	<c:forEach var="search" items="${search}">
		${search.board_no } 
		${search.board_category} 
		<a href=${pageContext.request.contextPath}/board/content?board_no=${search.board_no}>${search.board_title }</a> 
		[${search.board_replycount }] 
		${search.board_writer } 
		${search.board_wdate }
		<br>
	</c:forEach>
	
	<a href=${pageContext.request.contextPath}/board/regist>글쓰기</a>
	
	
	<div class="row">
    		<!-- 네비게이터(navigator) -->    		
    		<jsp:include page="/WEB-INF/views/template/board_navigator.jsp">
    			<jsp:param name="pno" value="${pno}" />
    			<jsp:param name="count" value="${count}" />
    			<jsp:param name="navsize" value="${navsize}" />
    			<jsp:param name="pagesize" value="${pagesize}" />
    		</jsp:include>
    	</div>
	
	
	<br><br>
	
	<div class="row">
	    	<form action="search" method="post">    	
		    		<select name="search_type">
		    			<option value="board_title">제목</option>
		    			<option value="board_writer">작성자</option>
		    			<option value="board_content">글내용</option>
		    		</select>    		
	    		<input type="search" name="search_keyword"  required>
				<input type="submit"  value="검색" >  
			</form>
	</div>
	
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>