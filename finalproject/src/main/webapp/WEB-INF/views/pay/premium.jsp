<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   	 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
   	 
   	 
   		<div class="container">
	   			 <div class="row">
	     			 <div class="col-lg-8 col-md-10 mx-auto">
	     			 <h6><a href = "${pageContext.request.contextPath}">home</a> > <a href = "#">프리미엄 결제</a>
	     			 	<div class = "for_aside">
	      					<div class="section_area">
	      						<h1>500 토큰</h1>
								<input type = "button" value = "전환" onclick="premium()">
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
		</div>
			
			
	<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>