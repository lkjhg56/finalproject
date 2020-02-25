<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script> 
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    	<script>
    	
    	$(function () {
      	  $(".afterconfirm").hide()

      	
      });
      
   
    function confirm(num, point, id){
    		$.ajax({
    			url : "${pageContext.request.contextPath}/paycheck/confirm",
    			type : "post",
    			data : {
    				num : num,
    				point : point,
    				id : id,
    			},success:function(resp){
    				var htmls=""

					$(".beforeconfirm").hide();
					$(".afterconfirm").show();
					htmls+=	'<h4>결제가 완료 되었습니다</h4>';
					$(".afterconfirm").html(htmls);
    			}
    			
    			
    		})
    	}
    function revoke(num){
		$.ajax({
			url : "${pageContext.request.contextPath}/paycheck/cancel",
			type : "post",
			data : {
				num : num,
			},success:function(resp){
				var htmls=""
		
				$(".beforeconfirm").hide();
				$(".afterconfirm").show();
				htmls+=	'<h4>결제가 취소 되었습니다</h4>';
				$(".afterconfirm").html(htmls);
			}
			
			
		})
	}
    	
    	
    	</script>
    
    
    
         <div class="container">
    		<div class="row">
      			<div class="col-lg-8 col-md-10 mx-auto">  
      				<br>
      				<div class = "for_aside">
      					<div class="section_area">
      					<h6><a href = "${pageContext.request.contextPath}">home</a>><a href = "#">결제</a></h6>
							<div class = "beforeconfirm">   						
    						<h4>${token} 만큼 충전 됨 현재 포인트는 ${point}
    						</h4>
    						<input type = button value ="구매확정" onclick="confirm('${payno}', '${point}', '${id}')">
    						<input type = button value = "취소" onclick= "revoke('${payno}')">
    						</div>
    						<div class= "afterconfirm">
    						</div>  
 
    	
    						<c:forEach var="paylist" items= "${paylist}">
    							<h5>결제 품목 : ${paylist.item_name} 결제 일시:${paylist.created_at} </h5>
    						</c:forEach>
    					</div>
    		 			<div class ="aside_area">
	 			
	 					<div id = "member_zone" class= "member_info1">
	 			
	 					</div>

	 					<div	class = "aside_link">
	 						<div class = "mRank">
		 					</div>
		 					<div class = "mNewList">
		 					</div>
		 					<div class = "mFQlist">
	 					</div>
	 					
	 					</div>
	 				
	 				</div>
				</div>
			</div>
		</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
					