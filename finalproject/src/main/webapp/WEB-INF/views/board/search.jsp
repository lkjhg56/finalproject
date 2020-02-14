<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>   

<div align="center">    

<h1>게시글 목록</h1>

	<form action="list" method="get">
		<input type="submit" name="board_category" value="전체">
		<input type="submit" name="board_category" value="공지">
		<input type="submit" name="board_category" value="자유">
		<input type="submit" name="board_category" value="질문">
		<input type="submit" name="board_category" value="업데이트"><br><br>
	</form>
	
	<table border="1" width="70%" style="text-align: center">
			<tr>						
				<th>번호</th>						
				<th>카테고리</th>
				<th width="40%">글제목</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
    
	<c:forEach var="search" items="${search}">
	
		<tr>
				<td>
					${search.board_no } 
				</td>
				<td>
					${search.board_category} 
				</td>
				<td style="text-align: left">
					<a href=${pageContext.request.contextPath}/board/content?board_no=${search.board_no}>${search.board_title }</a> 
					[${search.board_replycount }] 
				</td>
				<td>
					${search.board_writer } 
				</td>
				<td>
					${search.board_wdate.substring(0,16) }
				</td>
			</tr>	
	</c:forEach>
	
	<!-- 글쓰기 버튼은 로그인시 표시됨 -->
			<c:if test="${id != null}">					
				<td colspan="5" style="text-align: right">
					<button><a href=${pageContext.request.contextPath}/board/regist>글쓰기</a></button>
				</td>						
			</c:if>	
	</table>
	
	
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
	
	<div class="center">
	    	<form action="search" method="get">    	
		    		<select name="type">
		    			<option value="board_title">제목</option>
		    			<option value="board_writer">작성자</option>
		    			<option value="board_content">글내용</option>
		    		</select>    		
	    		<input type="search" name="keyword"  required>
				<input type="submit"  value="검색" >  
			</form>
	</div>
	
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>