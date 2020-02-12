<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
    
         <div class="container">
    		<div class="row">
      			<div class="col-lg-8 col-md-10 mx-auto">  
    
					  
					    <h1>완료</h1>
					${result}
					<h1>${token} 만큼 충전 됨 현재 포인트는 ${point}</h1>
				</div>
			</div>
		</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
					