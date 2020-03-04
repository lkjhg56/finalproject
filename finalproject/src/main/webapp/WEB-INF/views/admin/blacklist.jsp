<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	*{
		 font-family: 'Noto Sans';
	     font-weight: 400;
	      font-size: 13px;
	}
	.container .menu  a{
		  font-size: 20px;
		   font-weight: 550;
	}
	p{
		margin:0px;
		margin-top:10px;
		margin-bottom: 15px;
	}
	
	input[type="search"]{
	    width: 238px;
	    height: 36px;
	    padding: 0 50px 0 20px;
	    border: 1px solid #f7f7f6;
	    border-radius: 18px;
	    background-color: #f7f7f7;
	    font-family: 'Noto Sans';
	    font-weight: 400;
	    font-size: 12px;
	    color: #666;
	    line-height: 16px;
	    outline: none;
	}
	
	#search-btn{
		 width: 38px;
	    height: 36px;    
	    border: none;
	    background-color: #f7f7f7;
	    background: url(//img.icons8.com/material-outlined/24/000000/search.png) no-repeat 5px;
		margin:auto;
	    font-family: 'Noto Sans';
	    font-weight: 400;
	    font-size: 12px;
	    color: #666;
	    line-height: 16px;
	    outline: none;
	}
	
	#selectt{
	    font-family: 'Noto Sans';
	    font-weight: 400;
	    font-size: 12px;
        padding-bottom: 3px;
        border: none;
        border-bottom:  1px solid black;
       }
       
      .categoryblacklist{
		margin-bottom: -10px;
		display: inline-block;
	}
	.blaklist{
		margin-bottom: -10px;
		display: inline-block;
	}
	
	.row-empty {
		height: 30px;
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
		 .btn25{
		 	 color: #212529;
		 	 border: 1px solid black;
		 	 padding: 0.2rem 0.5rem;
		 }
		 .menu{
		 	margin-bottom: 8px;
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

//id가 check-all인 대상이 체크되면 check-item의 체크상태를 변화시키고 send_array 배열에 글번호를 추가
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
	
//id가 check-item인 대상이 체크되면 send_array에 글 번호를 추가
$(function(){    
    //#check-all에 change 이벤트를 설정한다
        kQuery(".check-item").on("change", function(){            
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
		//this==#selectDelete
		e.preventDefault();		
		var data = $(".selectDelete").serialize();
		console.log(data);
		
		if(!data.length){
			alert("삭제할 게시글을 선택하세요.");
		}
		else{
		document.querySelector(".selectDelete").submit()
			
		}
		});	
	});
	
$(function(){
	$("#email").click(function(e){
		//this==#email
		e.preventDefault();		
		var data = $(".selectDelete").serialize();
		console.log(data);
		
		if(!data.length){
			alert("메일을 받을 글 작성자를 선택하세요.");
		}
		else{
			$.ajax({
				 url: "${pageContext.request.contextPath}/admin/send",
				type:"get",
				data:data,
				success:function(resp){
					//console.log(resp);
					if(resp == "success"){
						alert("메일전송이 완료되었습니다.");
					}
				}
			});
		 }
			
	});	
});
	
$(function(){
	$(".category").change(function(e){
		//this==input[name=board_category]
		e.preventDefault();		
		var data = $("select[name=board_category]").val();
		console.log(data);
		
		if(data == "전체"){
			window.location.href = "${pageContext.request.contextPath}/admin/blacklist";
		}
		else{
		document.querySelector(".categoryblacklist").submit()			
		}
	});	
});

$(function(){
	$("#search-btn").click(function(e){
		//this==#search-btn
		e.preventDefault();		
		var data = $(".category").val();
		console.log(data);
		
		document.querySelector(".categoryblacklist").submit()		
	});	
});
</script>
<div class="row-empty"></div>
<div class="row-empty"></div>
<div class="container">
	<div class="container menu ff">
		<a href="${pageContext.request.contextPath}/admin/blacklist">게시글 관리</a> | 
		<a href="${pageContext.request.contextPath}/admin/replyblacklist">댓글 관리</a>
		<p>게시판관리 > 게시글 관리</p>
	</div>
	<form action="blacklist" class="category22" method="get">
    	<input class="category23"  type="hidden" name="board_cetegory" value="${board_category}"> 
	</form>
	
	<div class="container" style="text-align: left">
	<form action="blacklist" class="categoryblacklist" method="post">
		<select class="category"  name="board_category"> 
			<option>카테고리 선택</option>
			<option value="전체"<c:if test="${board_category=='전체'}">selected="selected"</c:if>>전체</option>
			<option value="자유"<c:if test="${board_category=='자유'}">selected="selected"</c:if>>자유</option>
			<option value="질문"<c:if test="${board_category=='질문'}">selected="selected"</c:if>>질문</option>
		</select>		
	</form>
	<div style="text-align: right" class="container">
	    	<form class="blaklist" action="blacklist" method="get">   
		    		<select id="selectt" name="type">
		    			<option value="board_title">제목</option>
		    			<option value="board_writer">작성자</option>
		    			<option value="board_content">글내용</option>
		    		</select>    		
	    		<input type="search" name="keyword"  required>
				<input id="search-btn" type="submit"  value="　" >
			</form>
	</div>	
		<button class="btn23 menu" id="selectDelete">선택삭제</button>
		<button class="btn23 menu" id="email">메일 보내기</button>
	</div>
	
	<form action="delete2" class="selectDelete">
	<table class="table table-hover" style="text-align: center">
		<thead>
			<tr>
				<th><input type="checkbox" id="check-all">전체</th>						
				<th>No.</th>						
				<th>카테고리</th>
				<th width="12%">글제목</th>
				<th>조회수</th>
				<th>작성자</th>
				<th>작성일</th>
				<th>신고사유</th>
				<th>신고일</th>
				<th>신고누적횟수</th>
				<th></th>
			</tr>
		</thead>
			
		<c:forEach var="list" items="${list}">
			<c:if test="${list.report_board_no!=0}">
				<tr>
					<td><input type="checkbox" class="check-item" name="board_no" value="${list.board_no}"></td>						
					<td>
						${list.report_no} 
					</td>
					<td>
						${list.board_category}						
					</td>
					<td style="text-align: left">
						<a href=${pageContext.request.contextPath}/board/content?board_no=${list.board_no}>${list.board_title }</a> 
					</td>
					<td>
						${list.board_readcount} 
					</td>
					<td>
						${list.report_board_writer } 
					</td>
					<td>
						${list.board_wdate.substring(0,16) }
					</td>
					<td>
						${list.report_reason}
					</td>
					<td>
						${list.report_date.substring(0,16)}
					</td>
					<td>
						${list.report_count}
					</td>
				<c:choose>
				<c:when test="${list.report_count >= 3}">
					<td>
						<button class="btn25">강제탈퇴</button>
					</td>
				</c:when>
				<c:otherwise>
					<td>
					</td>
				</c:otherwise>
				</c:choose>	
				
				</tr>	
				</c:if>	
		</c:forEach>		
	</table>
	</form>

	<div class="container nav" style="text-align: center;">
	<!--  네비게이터(navigator) -->
   		<jsp:include page="/WEB-INF/views/template/board_navigator.jsp">
   			<jsp:param name="pno" value="${pno}" />
   			<jsp:param name="count" value="${count}" />
   			<jsp:param name="navsize" value="${navsize}" />
   			<jsp:param name="pagesize" value="${pagesize}" />
   			<jsp:param name="board_category" value="${board_category}"/>
   		</jsp:include>
   	</div>
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>