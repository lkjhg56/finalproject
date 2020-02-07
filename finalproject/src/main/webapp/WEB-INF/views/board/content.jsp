<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<script>
	$(function() {
	    // edit 클래스를 숨긴다
	    $(".edit").hide();
	    
	 	// 수정을 누르면 표시되도록 처리
        $(".view").find(".su").click(function() {
        	$(this)
            .parents(".view")
            .hide();
          $(this)
            .parents(".view")
            .next(".edit")
            .show();
        });	
	
    $(".edit")
      .find("form")
      .submit(function(e) {
        //this == form
        e.preventDefault(); //기본이벤트 방지하는 코드, form전송을 막는방법임
       
        //비동기통신을 이용하여 작성한 댓글을 수정페이지로 전달
        var method = $(this).attr("method");

        var data = $(this).serialize(); //form에만 있는 기능 전송할 정보

        //console.log(url, method, data);
        $.ajax({
          url: "${pageContext.request.contextPath}/board/content",
          type: method,
          data: data,
          success: function(resp) {
        	 
          }
        });

        $(this)
          .parents(".edit")
          .hide();
        $(this)
          .parents(".edit")
          .prev(".view")
          .show(); //나를 숨기고 내 앞줄을 보여주세요
      });
  });   
   
        //댓글쓰기 버튼 (버튼을 눌러서 id값이 넘어와서 실행되는 자바스크립트 구문)       
        $("#registBtn").click(function(){
        	
        	$(".regist")
            .find("form")
            .submit(function(e) {
              //this == form
              e.preventDefault(); //기본이벤트 방지하는 코드, form전송을 막는방법임
              
            //비동기통신을 이용하여 작성한 댓글을 전달
              var method = $(this).attr("method");
              var data = $(this).serialize(); //form에만 있는 기능 전송할 정보
             
        $.ajax({
            type: "post", //데이터를 보낼 방식
            url: "${pageContext.request.contextPath}/board/content", 
            data: data, //보낼 데이터        
            
            success: function(data){//데이터를 보내는 것이 성공했을 시 출력되는 메시지
//                 alert("댓글이 등록되었습니다.");
                    }
             });

    

</script>
 

<div align="center">	
<h2>게시글 보기</h2>
		<table border="1" width="70%">
			<thead>
				<tr>
					<td><input type="button" value="${boardDto.board_category}">  
							${boardDto.board_title }      
							${boardDto.board_wdate }</td>
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
						댓글수 ${boardDto.board_replycount}
					</td>
				</tr>
			</thead>
			
			<!-- 댓글 목록을 보여주는 칸 -->				
			<tbody>
				<tr>
					<td>				
						<table border="1" width = "100%">
						
							<tbody>
								<c:forEach var="boardReplyDto" items="${boardReplyDto}">
									<tr class="info">
										<td>
											${boardReplyDto.board_reply_writer}								
											${boardReplyDto.board_reply_wdate}
										</td>
									</tr>
									<tr class="view">
										<td height="52">
											${boardReplyDto.board_reply_content}											
										</td>						
										<td  width="160">
											<button>답글</button>												
											| <button class="su">수정</button>
											| <button>삭제</button>
										</td>
									</tr>	
									
									<!-- 댓글을 수정하는 칸 -->
									<tr class="edit">
							            <td>
								              <form action="content" method="post">
								                <input type="hidden" name="board_reply_no" value="${boardReplyDto.board_reply_no}" />
								                <textarea name="board_reply_content" rows="3" cols="161" >${boardReplyDto.board_reply_content}</textarea>
								                <td align="center" width="120">
									                <input type="submit" value="완료" />
									                | <button>취소</button>
								                </td>
								              </form>
							            </td>							
									</tr>	
								</c:forEach>
							</tbody>
						
						</table>					
					</td>
				</tr>
			</tbody>
			
			<!-- 댓글 작성칸이 표시될 자리 -->
<!-- 		세션에 아이디가 저장되었을 경우 (로그인한 경우)에만 댓글 작성 창이 출력되도록 코드를 작성함 -->
<!-- 			<tr> -->
<!-- 				<td> -->
<%-- 		    <c:if test = "${sessionScope.id != null}">		     --%>
<!-- 			<form action="content" method="post"> -->
<%-- 				<input type="hidden" id="board_origin_content_no"  name="board_origin_content_no" value="${boardDto.board_no}"> --%>
<!-- 			    <textarea rows = "5" cols = "80" id = "board_reply_content" name = "board_reply_content"></textarea> -->
<!-- 			    <br>    		     -->
		  	 <!-- 댓글쓰기 버튼을 클릭하면 위쪽에 있는 자바스크립트 구문이 실행되어서 컨트롤러로 맵핑됨 --><br><br>
		    
<!-- 		    <input type="submit" value="글쓰기"> -->
<!-- 			</form> -->
<%-- 		    </c:if> --%>
<!-- 				</td> -->
<!-- 		    </tr> -->
		    
			
			
			<tr class="regist">
				<td align="right">				
					<form action="content" method="post">					
						<input type="hidden" name="board_origin_content_no" value="${boardDto.board_no}"> <!-- board의 no를 board_origin_content_no이라는 이름으로 전송 -->					
						<textarea name="board_reply_content" rows="4" cols="155" required></textarea>						
						<input id="registBtn" type="submit" value="등록">						
					</form>					
				</td>
			</tr>		
			
			<tr>
				<td align="right">
					<a href="regist"><input type="button" value="글쓰기"></a>
					
					<!-- 	본인글인지 여부와 관리자인지 여부 확인 -->
					<c:set var="isMine" value="${id == boardDto.board_writer}"></c:set>
					<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
					
					<c:if test="${isMine or isAdmin}">								
					<!-- 수정/삭제 버튼은 관리자이거나 본인글에만 표시 -->
					<a href="edit?board_no=${boardDto.board_no}"><input type="button" value="수정"></a>
					<a href="delete?board_no=${boardDto.board_no}"><input type="button" value="삭제"></a>
					</c:if>
					
					<a href="list"><input type="button" value="목록"></a>					
				</td>
			</tr>	
		</table>
	</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
