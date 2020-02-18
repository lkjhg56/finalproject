<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
         <div class="container">
    		<div class="row">
      			<div class="col-lg-8 col-md-10 mx-auto">  
      				<br>
      				<div class = "for_aside">
      					<div class="section_area">
      					<h6><a href = "${pageContext.request.contextPath}">home</a>><a href = "#">결제</a></h6>
    						<h4>${token} 만큼 충전 됨 현재 포인트는 ${point}</h4>
    	
    						<c:forEach var="paylist" items= "${paylist}">
    							<h5>결제 품목 : ${paylist.item_name} 결제 일시:${paylist.created_at} </h5>
    						</c:forEach>
    					</div>
    		 			<div class ="aside_area">
	 			
	 					<div id = "member_zone" class= "member_info1">
	 			
	 					</div>

	 					<div	class = "aside_link">
	 					
	 					</div>
	 				
	 				</div>
				</div>
			</div>
		</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
					