<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
 
 <%
	session.setAttribute("id", "test");
%>
 
<h2>게시글 보기</h2>

<div align="center">	
		<table border="1" width="70%">
			<tr>
				<td>${boardDto.board_title }</td>
			</tr>	
			<tr>
				<td>
					${boardDto.board_writer }
				</td>
			</tr>		
			<tr height="200">
				<td valign="top">
					${boardDto.board_content }
				</td>
			</tr>
			<tr>
				<td>
					댓글수 ${bdto.replycount}
				</td>
			</tr>
			<!-- 댓글 목록이 표시될 자리 -->				
			<tr>
				<td>				
					<table border="1" width = "100%">
					
					<c:forEach var="댓글dto" items="${list}">
						<tr>
							<th width ="100">
								<img src="http://placehold.it/100x100">
							</th>
							<td>
								${rdto.writer}
								
								<c:if test="${bdto.writer == rdto.writer}">		<!-- 게시글작성자와 댓글작성자가 같다면 -->
								<!-- 글 작성자의 댓글에만 작성자 표시 -->
								<font color="red">(작성자)</font>
								</c:if>
								
								${rdto.wdate}
								
								답글	<c:if test="${id == rdto.writer}">
								<!-- 수정/삭제 버튼은 본인의 댓글에만 표시 -->					
								| <a href="#">수정</a>
								| <a href="reply_delete.do?no=${rdto.no}&origin=${bdto.no}">삭제</a>
								<!-- 													댓글번호										게시글 번호 		헷갈리지말아요-->
								</c:if>
								<br><br>
								
								${rdto.content}
								
							</td>						
						</tr>	
					</c:forEach>
					
					</table>					
				</td>
			</tr>
			
			<!-- 댓글 작성칸이 표시될 자리 -->
			<tr>
				<td align="right">
				
					<form action="insert_reply" method="post">
					
						<input type="hidden" name="board_origin_content_no" value="${boardDto.board_no}"> <!-- board의 no를 board_origin_content_no이라는 이름으로 전송 -->
					
						<textarea name="reply_content" rows="4" cols="100" required></textarea>
						
						<input type="submit" value="등록">
						
					</form>
					
				</td>
			</tr>		
			
			<tr>
				<td align="right">
					<a href="regist"><input type="button" value="글쓰기"></a>
										
					<c:if test="${isMine or isAdmin}">								
					<!-- 수정/삭제 버튼은 관리자이거나 본인글에만 표시 -->
					<a href="edit?no=${boardDto.board_no}"><input type="button" value="수정"></a>
					<a href="delete?no=${boardDto.board_no}"><input type="button" value="삭제"></a>
					</c:if>
					
					<a href="list"><input type="button" value="목록"></a>					
				</td>
			</tr>	
		</table>
	</div>

