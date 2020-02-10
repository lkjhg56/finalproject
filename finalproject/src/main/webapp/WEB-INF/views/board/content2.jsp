<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script> 

<script>
$(function() {
    //edit 클래스를 가진 요소를 숨김
    $(".edit").hide();
	//수정을 누르면 수정화면이 표시되도록 처리
	$(".info")
          .find("button")
          .click(function() {
        	//info클래스 안의 버튼을 누르면
              // this == button
	   $(this)
          .next(".view")
          .hide(); //버튼의 부모(td)의 뷰를 숨김
	    $(this)
	      .next(".view")
	      .next(".edit")
	      .show();
	  });//완료를 누르면(form이 전송된다는 뜻) 글자화면이 표시되도록 처리
    $(".edit")
    .find("form")
    .submit(function(e) {
      //this == form
      e.preventDefault(); //기본이벤트 방지하는 코드, form전송을 막는방법임

      //비동기통신을 이용하여 작성한 댓글을 수정페이지로 전달
      //[1] 보내는 주소를 어떻게 구할 것인가?
      // - form에 작성된 action을 불러올 수 있는가?
      // - form에 작성된 method를 불러올 수 있는가?
      //[2] 비동기통신으로 데이터를 어떻게 보내는가?
      var url = $(this).attr("action");
      var method = $(this).attr("method");

      var data = $(this).serialize(); //form에만 있는 기능

      //console.log(url, method, data);
      $.ajax({
        url: url,
        type: method,
        data: data,
        success: function(resp) {
          console.log("성공");
          //resp에 들어온 정보를 토대로 화면을 갱신
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
</script>
 
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<div align="center">	
<h2>게시글 보기</h2>
		<table border="1" width="70%">
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
			<!-- 댓글 목록이 표시될 자리 -->				
			<tr>
				<td>				
					<table class="table">
				      <tbody>
				        <tr class="view">
				          <td>
				          	<c:forEach var="boardReplyDto" items="${boardReplyDto}">
					            <button>수정</button>
					            ${boardReplyDto.board_reply_writer}<br>
					            ${boardReplyDto.board_reply_wdate}<br>
					            ${boardReplyDto.board_reply_content}				          	
				          	</c:forEach>
				          </td>
				        </tr>
				        <tr class="edit">
				          <td>
				            <form action="./reply_deit.do" method="post">
				              <input type="hidden" name="writer" value="a" />
				              <input type="hidden" name="origin" value="1" />
				              <textarea name="content">테스트 댓글1</textarea>
				              <input type="submit" value="완료" />
				            </form>
				          </td>
				        </tr>
				      </tbody>
				    </table>
				</td>
			</tr>
			
			<!-- 댓글 작성칸이 표시될 자리 -->
			<tr>
				<td align="right">
				
					<form action="insert_reply" method="post">
					
						<input type="hidden" name="board_origin_content_no" value="${boardDto.board_no}"> <!-- board의 no를 board_origin_content_no이라는 이름으로 전송 -->
					
						<textarea name="board_reply_content" rows="4" cols="155" required></textarea>
						
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
