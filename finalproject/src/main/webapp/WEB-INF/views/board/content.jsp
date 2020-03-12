<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
<style>
	*{
	     font-weight: 400;
	}
	
	.container table{
		 font-family: 'Noto Sans';
		 font-size: 13px;
	}
	
	span{
		padding-left:10px;
		 padding-right:10px;
	}
	
	#a{
		 padding-right:10px;
	}
	.far{
		color : #118FCB;
		font-size : 20px;
	}
	
	.btn23 {
	     font-weight: 420;
	     font-size: 13px;
		  display: inline-block;
		  font-weight: 400;
		  color: #212529;
		  text-align: center;
		  vertical-align: middle;
		  background-color: white;
		  border: 1px solid black;
		  padding: 0.375rem 0.75rem;
		  line-height: 1.5;
		 }
		 
		 .btn24 {
	     font-weight: 420;
	     font-size: 13px;
		  display: inline-block;
		  font-weight: 400;
		  color: #212529;
		  text-align: center;
		  vertical-align: middle;
		  background-color: whitesmoke;
		  border: 1px solid #dee2e6;
		  padding: 0.375rem 0.75rem;
		  line-height: 1.5;
		 }
	
	a{
		color: black;
		text-decoration:none;
	}
	
	 .category-btn:hover, .category-btn:active{
		color : #1295D3;
		outline : none;
	}
	.category-btn2:hover, .category-btn2:active{
		color : #1295D3;
		outline : none;
	}
	
	.btn-primary:active{
		outline : none;
	}	
	
	.flex-wrap{
            display: flex;
            flex-direction: row;           /* 줄 안에 배치 */
        }
        @media screen and (max-width:640px){
            .flex-wrap{
             flex-direction: column;   /* 칸 안에 배치 */    
             }
        }
        .item1{
            flex-grow: 9;                   /* 줄에서 남은 공간을 차지하는 비율 */
            height: 100px;                 /* 1개만 높이가 있어도 나머지가 같은 높이로 설정된다. */
        }
        .item2{
            flex-grow: 1;
        }
         .item3{
            flex-grow: 9;                   /* 줄에서 남은 공간을 차지하는 비율 */
            height: 20px;                 /* 1개만 높이가 있어도 나머지가 같은 높이로 설정된다. */
        }
        .item4{
            flex-grow: 1;
            text-align : right;
        }
	
</style>
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


// 글 & 댓글 신고기능

$(function(){
	$("#report").click(function(){
		var id = $("#sessionId").val();
		console.log(id);
		if(id==undefined){
			alert("로그인 후 신고해 주세요.");
		}
		else{
			var data = $(".boardReport").serialize();
			console.log(data);	
			var win = window.open(
					"${pageContext.request.contextPath}/board/report?"
					+ data, "win", "width=450, height=315");
			
			document.querySelector(".boardReport").submit(e)
			
			e.preventDefault();	
		}	
	});
	
});	

$(function(){
	$(".rereport").click(function(e){
		var id = $("#sessionId").val();
		console.log(id);
		if(id==undefined){
			alert("로그인 후 신고해 주세요.");
			e.preventDefault();
		}
		else{		
		e.preventDefault();		
		var data = $(this).attr("href"); // href에 입력된 값을 가져옴
		console.log(data);
		
		var win = window.open(
				"${pageContext.request.contextPath}/board/report?report_reply_no="
				+ data, "win", "width=450, height=315");
		}
	});
	
});	

$(function(){
	$(".delete-ct").click(function(e){
		e.preventDefault();		
		var data = $(".delete_no").val();
		var result = confirm("정말 삭제하시겠습니까?");
		if(result) {
			location.replace("delete?board_no="+data);
		}
		else{
			
		}
	});
	
});
    
</script>
 

<div class="container"  style="border: 1px solid #dee2e6 !important; padding-top: 15px;">	
<!-- <h2>게시글 보기</h2> -->
		<table class="table table-bordered">
				<tr>
					<td>
						<div class="flex-wrap">
							<span class="item3">[${boardDto.board_category}]&nbsp;${boardDto.board_title }</span>
	
							<span class="item4" style="text-align: right">${boardDto.board_wdate.substring(0,16) }</span>
						</div>
					</td>
				</tr>	
				<tr>
					<td>
						<span>${boardDto.board_writer }</span>
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
						<div class="flex-wrap">
							<span class="item3">댓글수 ${boardDto.board_replycount}&nbsp; | &nbsp;조회수 ${boardDto.board_readcount}</span>
							<span class="item4" ><a href="#" id="report">신고</a></span>
						</div>
					</td>
				</tr>
		</table>			
		<form action="report" class="boardReport" method="get">
			<input type="hidden" name="report_board_no" value="${boardDto.board_no}">	
		</form>
			
			<!-- 댓글 목록을 보여주는 칸 -->
				<c:forEach var="boardReplyDto" items="${boardReplyDto}">
					<div class="replystart">
						<table  class="table table-bordered">
						
									<tr class="info">
										<td>
											<span>${boardReplyDto.board_reply_writer}</span>

											<span>${boardReplyDto.board_reply_wdate.substring(0,16)}</span>	
											<c:if test="${boardReplyDto.depth < 1 && id != null}">
												<a href="#" id="re">답글  |</a>
											</c:if>											
												<a href="${boardReplyDto.board_reply_no}" class="rereport" >신고</a>
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
														&nbsp;&nbsp;&nbsp;&nbsp;
														<i class="far fa-hand-point-right"></i>
														&nbsp;&nbsp;&nbsp;${boardReplyDto.board_reply_content}
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
											<div class="flex-wrap">			
												<input type="hidden" name="groupno" value="${boardReplyDto.groupno}">											
												<input type="hidden" name="board_reply_origin" value="${boardDto.board_no}">													
												<input type="hidden" name="superno" value="${boardReplyDto.board_reply_no}">
												<textarea class="re_Text item1" name="board_reply_content" rows="4"  required></textarea>					
												<div class="item2"  id="a" style="text-align: center; line-height:100px;">
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
			<table  class="table table-bordered">
				<tr>
					<td align="right">				
						<form id="registbtn" method="post">
							<div class="flex-wrap">
								<input type="hidden" name="board_reply_origin" value="${boardDto.board_no}"> <!-- board의 no를 전송 -->	
								<input type="hidden" id="sessionId" name="board_reply_writer" value="${id}">												
								<textarea class="user-input item1" name="board_reply_content" rows="4" required></textarea>						
								<input class="btn24 category-btn2 item2"   type="submit" value="등록">								
							</div>					
							
							<c:if test="${!empty boardReplyDto}">
								 <div class="container navv" style="text-align: center">
						    		<!-- 네비게이터(navigator) 댓글이 있으면 표시 -->    		
						    		<jsp:include page="/WEB-INF/views/template/board_navigator.jsp">
						    			<jsp:param name="pno" value="${pno}" />
						    			<jsp:param name="count" value="${count}" />
						    			<jsp:param name="navsize" value="${navsize}" />
						    			<jsp:param name="pagesize" value="${pagesize}" />
						    		</jsp:include>
						    	</div>					
							</c:if>
						</form>					
					</td>
				</tr>
			</table>	
			</c:if>
			
				
		<table class="table table-bordered">
			<tr>
				<td align="right">
					<!-- 글쓰기 버튼은 로그인시 표시됨 -->
					<c:if test="${id != null}">							
						<button type="button" class="btn23 category-btn">
								<a href=${pageContext.request.contextPath}/board/regist><i class="fas fa-pencil-alt"></i>글쓰기</a>
							</button>		
					</c:if>	

				
					<!-- 	본인글인지 여부와 관리자인지 여부 확인 -->
					<c:set var="isMine" value="${id == boardDto.board_writer}"></c:set>
					<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
					
					<c:if test="${isMine or isAdmin}">								
					<!-- 수정/삭제 버튼은 관리자이거나 본인글에만 표시 -->
					<input class="delete_no" type="hidden" name="board_no" value="${boardDto.board_no }">
					<a href="edit?board_no=${boardDto.board_no}"><input class="btn23 category-btn" type="button" value="수정"></a>
					<a href="delete?board_no=${boardDto.board_no}"><input class="btn23 category-btn delete-ct"  type="button" value="삭제"></a>
					</c:if>
					
					<a href="list"><input class="btn23 category-btn" type="button" value="목록"></a>					
				</td>
			</tr>	
		</table>
	</div>	
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>