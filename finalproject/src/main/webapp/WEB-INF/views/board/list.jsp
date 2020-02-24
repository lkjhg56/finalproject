<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	input[name="board_category"]{
		border:none;
	}
</style>
<script>
function kQuery(selector){
    //[1] 사용자가 집어넣은 string으로 태그 목록을 생성
    var tagList = document.querySelectorAll(selector);

    //[2] 사용자가 필요하리라고 예상되는 기능을 객체로 만들어 반환(클로저, closer)
    return {    //일회용 객체생성
        //이름 : function(){}
        //[1] 이벤트 설정 대행 기능 - on(종류->click, 코드->function)
        //on:function(종류, 코드){    //addEventListener대신 사용하게해줌
            on:function(type, code){
                for(var i=0; i < tagList.length; i++){
                    tagList[i].addEventListener(type, code);
                }
        },
        //[2] 전체 체크 설정/해제
        check:function(isCheck){
            for(var i=0; i < tagList.length; i++){
                tagList[i].checked = isCheck;
                
            }
        },
    }; 
}


//id가 check-all인 대상이 체크되면 check-item의 체크상태를 변화시켜라
$(function(){    
    //#check-all에 change 이벤트를 설정한다
        kQuery("#check-all").on("change", function(){
            kQuery(".check-item").check(this.checked);
            
                send_array = Array();
                var send_cnt = 0;
                var chkbox = $(".check-item");
                
                for(i=0;i<chkbox.length;i++) {
                    if (chkbox[i].checked == true){
                        send_array[send_cnt] = chkbox[i].value;
                        send_cnt++;
                    }
                }

                $("#array").val(send_array);
                console.log(send_array);          
	    });
	});  

$(function(){
	$("#selectDelete").click(function(e){
		e.preventDefault();
		//this==#selectDelete
		var data = send_array;
		console.log(send_array);
		
		 $.ajax({
	        	url: "${pageContext.request.contextPath}/board2/delete2",
	            type: "post",
	            dataType: "json",
	            data: JSON.stringify(send_array),
	            success: function(success) {
	          	  console.log("성공")
// 	          	  window.location.reload();
	  	  		},
	  	  	    error:function(){
	  	  		  console.log("실패")	  		
	            }
	        	
	        });
	
		});	
	});

//class가 check-item인 대상이 체크되면 값들을 하나씩 send_array[i]에 넣는다.
$(function(){               
         var send_array = Array();
         var send_cnt = 0;
         var chkbox = $(".check-item");
         
         for(i=0;i<chkbox.length;i++) {
             if (chkbox[i].checked == true){
                 send_array[send_cnt] = chkbox[i].value;
                 send_cnt++;
             }
         }

         $("#array").val(send_array);
         console.log(send_array);
    });
  

</script>

<div class="container">    

<h1>게시글 목록</h1>

	<form id="fo" action="list" method="get">
		<input type="submit" name="board_category" value="전체">
		<input type="submit" name="board_category" value="자유">
		<input type="submit" name="board_category" value="질문">
		<input id="notice" type="submit" name="board_category" value="공지">
		<input id="update" type="submit" name="board_category" value="업데이트"><br><br>
	</form>
	
	<table class="table table-hover" style="text-align: center">
		<thead>
			<tr>
				<c:if test="${grade == '관리자'}">
					<th><input type="checkbox" id="check-all">전체선택</th>						
				</c:if>
				<th>No.</th>						
				<th>카테고리</th>
				<th width="40%">글제목</th>
				<th>조회수</th>
				<th>작성자</th>
				<th>작성일</th>
			</tr>
		</thead>
			
		<c:forEach var="list" items="${list}">
			<tr>
				<c:if test="${grade == '관리자'}">
						<td><input type="checkbox" class="check-item" value="${list.board_no }"></td>						
				</c:if>
				<td>
					${list.board_no } 
				</td>
				<td>
					${list.board_category} 
				</td>
				<td style="text-align: left">
					<a href=${pageContext.request.contextPath}/board/content?board_no=${list.board_no}>${list.board_title }</a> 
					[${list.board_replycount }] 
				</td>
				<td>
					${list.board_readcount} 
				</td>
				<td>
					${list.board_writer } 
				</td>
				<td>
					${list.board_wdate.substring(0,16) }
				</td>
			</tr>				
		</c:forEach>
	</table>
	
	<div class="container" style="text-align: right">
		<!-- 관리자기능 게시글 다중삭제 -->
		<c:if test="${grade == '관리자'}">
			<button id="selectDelete">선택삭제</button>
		</c:if>
		
		<!-- 글쓰기 버튼은 로그인시 표시됨 -->		
		<c:if test="${id != null}">				
			<button type="button" class="btn btn-primary">
				<a href=${pageContext.request.contextPath}/board/regist>글쓰기</a>
			</button>						
		</c:if>	
	</div>
	
	  <div class="container" style="text-align: center">
    		<!-- 네비게이터(navigator) -->    		
    		<jsp:include page="/WEB-INF/views/template/board_navigator.jsp">
    			<jsp:param name="pno" value="${pno}" />
    			<jsp:param name="count" value="${count}" />
    			<jsp:param name="navsize" value="${navsize}" />
    			<jsp:param name="pagesize" value="${pagesize}" />
    			<jsp:param name="board_category" value="${board_category}"/>
    		</jsp:include>
    	</div>
	
	
	<br><br>
	
	<div style="text-align: center" class="container">
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