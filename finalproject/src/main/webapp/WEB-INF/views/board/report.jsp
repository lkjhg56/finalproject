<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<style>
		div *{
		 font-family: 'Noto Sans';
	     font-weight: 400;
		}

         /* flex-grow : 1만큼의 영역을 가지도록 커져라 
             flex-direction: row : 1줄안에서 배치함
             flex-direction: column : 칸으로 배치하는 명령
        */
        .flex-wrap{
            display: flex;
            flex-direction: row;           /* 줄 안에 배치 */
        }
        @media screen and (max-width:500px){
            .flex-wrap{
             flex-direction: column;   /* 칸 안에 배치 */    
             }
        }
        .item4{
            background-color: whitesmoke;
            flex-grow: 1;                   /* 줄에서 남은 공간을 차지하는 비율 */
            height: 200px;                 /* 1개만 높이가 있어도 나머지가 같은 높이로 설정된다. */
        }
   
        .popup{
            clear: both;
            padding: 18px 29px 18px;
            border: 2px solid #dbe0f3;
        }
        h2{
        	 background-color: whitesmoke;
		     font-weight: 510;
		     font-size: 18px;
		     padding:10px;
		     border-bottom:1px solid #dbe0f3;
        }
        .reason-container{
        	padding:5px;
        	font-weight: 550;
        }
        .report-item *{
        	padding:5px;
        }
        .button1{
        	text-align:center;
        	margin:10px;
        }
        .row-empty {
		height: 30px;
	}
</style>    
<script>
$(function(){
	$("input[name=report-btn]").click(function(e){
		//this==#selectDelete
		e.preventDefault();				
		var radioVal = $("input[name=report_reason]:checked").val();
		console.log(radioVal);
		
		if(radioVal==undefined){
			alert("신고사유를 선택하세요.");
		}
		else{
		document.querySelector(".report").submit()
			alert("신고가 성공적으로 접수되었습니다.");			
		}
		});	
	});
</script>

<h2>신고하기</h2>
<div class="container">
	<div class="reason-container">신고사유</div>
	
<form class="report" action="report" method="post">
<!-- 신고 등록을 위한 파라미터 전송 -->
<input type="hidden" name="report_board_no" value="${boardDto.board_no}">
<input type="hidden" name="report_reply_no" value="${boardReplyDto.board_reply_no}">

	<div class="report-item">
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_A" value="영리목적/홍보성">
           <label for="reason_A" id="label_A" >영리목적/홍보성</label> 
         </div>
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_B" value="욕설/인신공격">
           <label for="reason_B" id="label_B" >욕설/인신공격</label> 
         </div>
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_C" value="음란성/선정성">
           <label for="reason_C" id="label_C" >음란성/선정성</label> 
         </div>
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_D" value="같은 내용의 반복 게시(도배)">
           <label for="reason_D" id="label_D" >같은 내용의 반복 게시(도배)</label> 
         </div>
         
          <div class="row-empty"></div>
          <div class="button1">
            <input type="submit" name="report-btn" value="신고">
            <input type="button" value="닫기" onClick="window.close()">
          </div>
      </div>
   
</form>	
</div>