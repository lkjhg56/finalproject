<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
$(function() {
		
// 댓글 수정		
	    // edit 클래스를 숨긴다
	    $(".edit").hide();
	    
	    getCommentList();
	    
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
	 	
	 	
	
    $("#replyEdit").submit(function(e) {
    	e.preventDefault();
       
        //비동기통신을 이용하여 작성한 댓글을 수정페이지로 전달
        var data = $(this).serialize(); //form에만 있는 기능 (전송할 정보)
     
        $.ajax({
          url: "${pageContext.request.contextPath}/board2/edit2",
          type: "post",
          data: data,
          success: function(resp) {
          	location.reload();          	
          }          
        });

        $(this)
          .parents(".edit")
          .hide();
        $(this)
          .parents(".edit")
          .prev(".view")
          .show(); //나를 숨기고 내 앞줄을 보여줌
      });
    
    
    
    
    
 // 댓글 등록   
    $("#regist-btn").submit(function(e) {
    	e.preventDefault();
    	
        var data = $(this).serialize();
    	
    	$.ajax({
    		url: "${pageContext.request.contextPath}/board2/insert",
            type: "post",
            data: data,
            success: function(result) {
            	location.reload();            	
            }
    	});        
    });
    
 
 
    
// 댓글 삭제
    $(".sak").click(function(e){
    	e.preventDefault();
    	
        var data = $(this).serialize();
        	
        $.ajax({
        	url: "${pageContext.request.contextPath}/board2/delete",
            type: "post",
            data: data,
            success: function(resp) {   
            	location.reload();
            }        	
        });    	
    });
    
    
    /**
     * 댓글 불러오기(Ajax)
     */

});

function getCommentList(){
    
    $.ajax({
        type:'GET',
        url : "${pageContext.request.contextPath}/board2/replyList",
        dataType : "list",
        success : function(){
            console.log("성공")
     
        },
        error:function(){ 
        	console.log("실패");
       }            
    });



};
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
					<td id = "comment">			
<!-- 						<div class="container"> -->
<!-- 						    <form id="commentForm" name="commentForm" method="post"> -->
<!-- 						    <br><br> -->
<!-- 						        <div> -->
<!-- 						            <div> -->
<!-- 						                <span><strong>Comments</strong></span> <span id="cCnt"></span> -->
<!-- 						            </div> -->
<!-- 						            <div> -->
<!-- 						                <table class="table">                     -->
<!-- 						                    <tr> -->
<!-- 						                        <td> -->
<!-- 						                            <textarea style="width: 1100px" rows="3" cols="30" id="board_reply_content" name="board_reply_content" placeholder="댓글을 입력하세요"></textarea> -->
<!-- 						                            <br> -->
<!-- 						                            <div> -->
<%-- 						                                <a href='#' onClick="fn_comment('${result.code }')" class="btn pull-right btn-success">등록</a> --%>
<!-- 						                            </div> -->
<!-- 						                        </td> -->
<!-- 						                    </tr> -->
<!-- 						                </table> -->
<!-- 						            </div> -->
<!-- 						        </div> -->
<%-- 						        <input type="hidden" id="board_reply_origin" name="board_reply_origin" value="${result.code }" />         --%>
<!-- 						    </form> -->
<!-- 						</div> -->
						
<!-- 						<div class="container"> -->
<!-- 						    <form id="commentListForm" name="commentListForm" method="post"> -->
<!-- 						        <div id="commentList"> -->
<!-- 						        </div> -->
<!-- 						    </form> -->
<!-- 						</div>	 -->
						<table  width = "100%">
						
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
											| <button class="sak">삭제</button>
										</td>
									</tr>	
									
									댓글을 수정하는 칸
									<tr class="edit">
							            <td>
								              <form id="replyEdit" action="content" method="post">
								                <input type="hidden" name="board_reply_no" value="${boardReplyDto.board_reply_no}" />
								                <textarea name="board_reply_content" rows="3" cols="161" >${boardReplyDto.board_reply_content}</textarea>
								                <td align="center" width="160">
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
					    
			
			<!-- 	댓글 등록창 -->
			<tr>
				<td align="right">				
					<form id="regist-btn" action="${pageContext.request.contextPath}/board/content?board_no=${boardDto.board_no}" method="post">					
						<input type="hidden" name="board_reply_origin" value="${boardDto.board_no}"> <!-- board의 no를 전송 -->	
						<input type="hidden" name="board_reply_writer" value="${id}">					
						<textarea class="user-input" name="board_reply_content" rows="4" cols="155" required></textarea>						
						<input type="submit" value="등록">						
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
