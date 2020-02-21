<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<jsp:include page="/WEB-INF/views/template/exheader.jsp"></jsp:include>
<script>
$(function() {
		
// 댓글 수정		
	    // edit 클래스를 숨긴다
	    $(".edit").hide();
	    
	 	// 수정을 누르면 표시되도록 처리
        $(".view").find("#su").click(function() {
        	//this = #su
        	$(this)
            .parents(".view")
            .hide();
          $(this)
            .parents(".view")
            .siblings(".edit")
            .show();
        });	
	
   		$(".edit").find("#finish").click(function(e){
        //this == #finish
        e.preventDefault(); //기본이벤트 방지하는 코드, form전송을 막는방법임
       
        //비동기통신을 이용하여 작성한 댓글을 수정페이지로 전달        
        var data = $(this).parents(".edit").find("#editform").serialize(); //form에만 있는 기능 (전송할 정보)
        console.log(data);
        
        $.ajax({
          url: "${pageContext.request.contextPath}/board2/edit2",
          type: "post",
          data: data,
          success: function(success) {
        	  console.log("성공")
        	  window.location.reload();
	  		},
	  	  error:function(){
	  		  console.log("실패")	  		
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
   		
   		
//수정 중 취소버튼 누르면 수정을 취소한다
   		$(".edit").find("#cancel").click(function(){
   			//this = #cancel
   			
   		$(this)
          .parents(".edit")
          .hide();
        $(this)
          .parents(".edit")
          .siblings(".view")
          .show(); //나를 숨기고 내 앞줄을 보여줌   			
   		});
        	
});   

	

    
 // 댓글 등록   
 $(function() {
    $("#registbtn").submit(function(e) {
    	e.preventDefault();
    	
        var data = $(this).serialize();
        console.log(data);
    	
    	$.ajax({
    		url: "${pageContext.request.contextPath}/board2/insert",
            type: "post",
            data: data,
            success: function(resp) {
            	if(resp == "success"){
            		 console.log("댓글등록 성공")
               	  window.location.reload();
            	}
            	else{
            		alert("로그인이 필요합니다.");
            		 window.location.reload();
            	}          	
            	 
    	  		},
    	  	    error:function(){
    	  		  console.log("댓글등록 실패")	  		
              }
    	});        
    });
 });    
    
// 댓글 삭제
$(function() {
    $(".view").find("#sak").click(function() {
    	//this = #sak
    	
		//비동기통신을 이용하여 수정할 댓글의 no를 삭제페이지로 전달        
        var data = $(this).parents(".view").find("#deleteform").serialize(); //form에만 있는 기능 (전송할 정보)
        console.log(data);
        	
        $.ajax({
        	url: "${pageContext.request.contextPath}/board2/delete",
            type: "post",
            data: data,
            success: function(success) {
          	  console.log("성공")
          	  window.location.reload();
  	  		},
  	  	    error:function(){
  	  		  console.log("실패")	  		
            }
        	
        });
    	
    });
});   

//댓글 답글달기
$(function(){
	// 대댓글 입력창을 숨긴다
    $(".reInsert").hide();
	
		//답글 a태그를 누르면 원본댓글 하단에 대댓글 입력창을 보여준다.
		$(".info").find("#re").click(function() {
	    	//this = #re
	
	       	  $(this)
	            .parents(".info")
	            .siblings(".reInsert")
	            .show();    	
		 });
		
		$(".reInsert").find("#re_finish").click(function(){
	    //this == #re_finish
	        
	    //등록 버튼을 누르면 대댓글 입력창을 숨기고 입력창의 내용과 히든으로 작성자 정보를 비동기 통신으로 보내어 대댓글 insert한다	    	
		//비동기통신을 이용하여 수정할 댓글의 no를 삭제페이지로 전달        
        var text = $(this).parents(".reInsert").find(".re_Text").val();
	    var superno = $(this).parents(".reInsert").find("input[name=superno]").val();
	    var groupno = $(this).parents(".reInsert").find("input[name=groupno]").val();
	    var origin = $(this).parents(".reInsert").find("input[name=board_reply_origin]").val();
	   
        console.log(superno);
        	
        $.ajax({
        	url: "${pageContext.request.contextPath}/board2/insert",
            type: "post",
            data: {
            	superno: superno, 
            	groupno : groupno,
            	board_reply_content: text, 
				board_reply_origin : origin
			},
            success: function(success) {
          	  console.log("대댓글 데이터 전송 성공")
          	  window.location.reload();
  	  		},
  	  	    error:function(){
  	  		  console.log("실패")	  		
            }
        	
	        });  			
		});
		
		 $(this)
         .parents(".reInsert")
         .hide();
        
});

    
</script>
 

<div align="center">	
<h2>게시글 보기</h2>
			

				
		<table border="1" width="70%">
				<tr>
					<td>
						<div>
							<span><input type="button" value="${boardDto.board_category}">&nbsp;</span>
							<span>${boardDto.board_title }&nbsp;&nbsp;</span>
							<span style="text-align: right">${boardDto.board_wdate.substring(0,16) }</span>
						</div>
					</td>
				</tr>	
				<tr>
					<td>
						${boardDto.board_writer }
					</td>
				</tr>		
				<tr height="200">
					
						<td valign="top">
					<c:forEach var="filelist"  items="${filelist}">
						<div><img src="boardimg?board_file_no=${filelist.board_file_no}"  width="120" height="120"></div>	
					</c:forEach>
							${boardDto.board_content }
						</td>
				</tr>
				<tr>
					<td>
						댓글수 ${boardDto.board_replycount}
					</td>
				</tr>
		</table>
			
			
			<!-- 댓글 목록을 보여주는 칸 -->
				<c:forEach var="boardReplyDto" items="${boardReplyDto}">
					<div class="replystart">
						<table  border="1"   width = "70%">
						
									<tr class="info">
										<td>
											<span>${boardReplyDto.board_reply_writer}</span>
											<span>　</span>		
											<span>${boardReplyDto.board_reply_wdate.substring(0,16)}</span>	
											<c:if test="${boardReplyDto.depth < 1}">
												<span><a href="#" id="re">답글</a></span>
											</c:if>							
										</td>
									</tr>
									
									<!-- 	본인글인지 여부와 관리자인지 여부 확인 -->
									<c:set var="isMine" value="${id == boardReplyDto.board_reply_writer}"></c:set>
									<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
									
									
									<tr class="view">
										<td height="83">
											<form id="deleteform" method="post">
												<input type="hidden" name="board_reply_no" value="${boardReplyDto.board_reply_no}">
												<input type="hidden" name="board_reply_origin" value="${boardDto.board_no}">

												<c:choose>
													<c:when test="${boardReplyDto.depth > 0}">
														<div>
														&nbsp;&nbsp;&nbsp;&nbsp;${boardReplyDto.board_reply_content}
														</div>
													</c:when>
													<c:otherwise>
														<div>${boardReplyDto.board_reply_content}</div>
													</c:otherwise>
												</c:choose>
												
												<c:if test="${isMine or isAdmin}">	
													<div id="a" style="text-align: right">
														<a href="#" id="su">수정</a>
														| <a href="#" id="sak">삭제</a>										
													</div>	
												</c:if>
												
											</form>
										</td>						
									</tr>	
									
									<!-- 대댓글 입력창 -->
									<tr class="reInsert">
										<td>
											<div>			
												<input type="hidden" name="groupno" value="${boardReplyDto.groupno}">											
												<input type="hidden" name="board_reply_origin" value="${boardDto.board_no}">													
												<input type="hidden" name="superno" value="${boardReplyDto.board_reply_no}">
												<textarea class="re_Text" name="board_reply_content" rows="4" style="width:96.2%; text-align:left;" required></textarea>					
												<div id="a" style="text-align: right">
													<a href="#" id="re_finish">완료</a>
													| <a href="#" id="re_cancel">취소</a>										
												</div>	
											</div>
										</td>
									</tr>
									
									<!-- 댓글을 수정하는 칸 -->
							
									<tr class="edit">
							            <td>
								              <form id="editform" method="post">
									                <input type="hidden" name="board_reply_no" value="${boardReplyDto.board_reply_no}" />
									                <textarea name="board_reply_content" rows="2" cols="150" >${boardReplyDto.board_reply_content}</textarea>
									                <div id="a" style="text-align: right">
														<a href="#" id="finish">완료</a>
														| <a href="#" id="cancel">취소</a>										
													</div>	
								              </form>
							            </td>							
									</tr>	
								
						</table>
					</div>					
				</c:forEach>
					
			<!-- 	댓글 등록창 -->
			<c:if test="${id != null}">			
			<table  border="1"   width = "70%">
				<tr>
					<td align="right">				
						<form id="registbtn" method="post">					
							<input type="hidden" name="board_reply_origin" value="${boardDto.board_no}"> <!-- board의 no를 전송 -->	
							<input type="hidden" name="board_reply_writer" value="${id}">					
							<textarea class="user-input" name="board_reply_content" rows="4" style="width:96.2%; text-align:left;" required></textarea>						
							<input type="submit" value="등록">						
						</form>					
					</td>
				</tr>
			</table>	
			</c:if>
			
			 <div class="row">
	    		<!-- 네비게이터(navigator) -->    		
	    		<jsp:include page="/WEB-INF/views/template/board_navigator.jsp">
	    			<jsp:param name="pno" value="${pno}" />
	    			<jsp:param name="count" value="${count}" />
	    			<jsp:param name="navsize" value="${navsize}" />
	    			<jsp:param name="pagesize" value="${pagesize}" />
	    		</jsp:include>
	    	</div>
				
		<table>
			<tr>
				<td align="right">
					<!-- 글쓰기 버튼은 로그인시 표시됨 -->
					<c:if test="${id != null}">							
						<a href="regist"><input type="button" value="글쓰기"></a>
					</c:if>	
				
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
	
<jsp:include page="/WEB-INF/views/template/exfooter.jsp"></jsp:include>