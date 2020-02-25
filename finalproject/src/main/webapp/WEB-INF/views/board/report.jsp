<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<style>
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
    </style>

<h1>신고하기</h1>
<div class="container">
	<div>신고사유</div>
	
<form action="report" method="post">
<!-- 신고 등록을 위한 파라미터 전송 -->
<input type="hidden" name="report_board_no" value="${boardDto.board_no}">
<input type="hidden" name="report_reply_no" value="${boardReplyDto.board_reply_no}">
<input type="hidden" name="report_board_writer" value="${boardDto.board_writer}">
<input type="hidden" name="report_board_writer" value="${boardReplyDto.board_reply_writer}">


	<div class="report-item">
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_A" value="A">
           <label for="reason_A" id="label_A" data-reason="A">영리목적/홍보성</label> 
         </div>
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_B" value="B">
           <label for="reason_B" id="label_B" data-reason="B">욕설/인신공격</label> 
         </div>
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_C" value="C">
           <label for="reason_C" id="label_C" data-reason="C">음란성/선정성</label> 
         </div>
         <div class="item_reason">
           <input type="radio" name="report_reason" id="reason_D" value="D">
           <label for="reason_D" id="label_D" data-reason="D">같은 내용의 반복 게시(도배)</label> 
         </div>
          
          <div>
            <input type="submit" name="report-btn" value="신고">
            <input type="reset" value="취소">  
          </div>
      </div>
   
</form>	
</div>