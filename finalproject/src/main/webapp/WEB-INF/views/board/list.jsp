<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
	*{
	     font-weight: 400;
	      font-size: 13px;
	}
	
	.container table{
		 font-family: 'Noto Sans';
	}
	
	.row-empty {
		height: 30px;
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
       
      .btn22 {
      	 font-family: 'Noto Sans';
	     font-weight: 420;
	     font-size: 13px;
	     border:none;
		  display: inline-block;
		  font-weight: 400;
		  color: #212529;
		  text-align: center;
		  vertical-align: middle;
		  background-color: white;
		  border: 1px solid transparent;
		  padding: 0.375rem 0.75rem;
		  font-size: 1rem;
		  line-height: 1.5;
		  border-radius: 0.25rem;
		 }
       
      .category-btn:hover, .category-btn:active{
		background: none;
		color : #1295D3;
		outline : none;
	}

	
	.btn-primary a{
		color: white;
		text-decoration:none;	
		font-size: 12px;	
	}
	
	#fo{
		margin-bottom: -45px;
		display: inline-block;
	}

	.btn23 {
      	 font-family: 'Noto Sans';
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
	
  

</script>
<div class="row-empty"></div>
<div class="row-empty"></div>
<div class="container">    

<form id="fo" action="list" method="get">
		<input class="btn22 category-btn" type="submit" name="board_category" value="전체">
		<input class="btn22 category-btn"  type="submit" name="board_category" value="자유">
		<input class="btn22 category-btn"  type="submit" name="board_category" value="질문">
		<input class="btn22 category-btn"  id="notice" type="submit" name="board_category" value="공지">
		<input class="btn22 category-btn"  id="update" type="submit" name="board_category" value="업데이트">
	</form>
	
		<div style="text-align: right" class="container">
	    	<form action="search" method="get">    	
		    		<select id="selectt" name="type">
		    			<option value="board_title">제목</option>
		    			<option value="board_writer">작성자</option>
		    			<option value="board_content">글내용</option>
		    		</select>    		
	    		<input type="search" name="keyword"  required>
				<input id="search-btn" type="submit"  value="　" >
			</form>
	</div>	
<hr>

	
	<form action="delete2" class="selectDelete">
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
						<td><input type="checkbox" class="check-item" name="board_no" value="${list.board_no }"></td>						
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
	</form>
	
	<div class="container" style="text-align: right">
		<!-- 관리자기능 게시글 다중삭제 -->
		<c:if test="${grade == '관리자'}">
			<button class="btn23 category-btn" id="selectDelete">선택삭제</button>
		</c:if>
		
		<!-- 글쓰기 버튼은 로그인시 표시됨 -->		
		<c:if test="${id != null}">				
			<button type="button" class="btn23 category-btn">
				<a href=${pageContext.request.contextPath}/board/regist><i class="fas fa-pencil-alt"></i>글쓰기</a>
			</button>						
		</c:if>	
	</div>
	
	  <div class="container nav" style="text-align: center;">
    		<!-- 네비게이터(navigator) -->    		
    		<jsp:include page="/WEB-INF/views/template/board_navigator.jsp">
    			<jsp:param name="pno" value="${pno}" />
    			<jsp:param name="count" value="${count}" />
    			<jsp:param name="navsize" value="${navsize}" />
    			<jsp:param name="pagesize" value="${pagesize}" />
    			<jsp:param name="board_category" value="${board_category}"/>
    		</jsp:include>
    	
	
	
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>