<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<style>
.question-float::after{
	 content:"";
    display:block;
    clear:both;
    flex-wrap:wrap-reverse;
}
.question-pic{
	 float:left;
    width:33%;
    height:100px;
}
.question-name{
	 float:left;
    width:47%;
    height:100px;
}
.question-submit{
	 float:left;
    width:20%;
    height:100px;
}


.for_aside{
	 content:"";
    display:block;
    clear:both;
    flex-wrap:wrap-reverse;
}

.section_area{
	 float:left;
    width:70%;
}
.aside_area{
	 float:left;
    width:30%;
    min-height:600px;
}


.member_info1{
	width : 100%;
	height: 120px;
	 background-color: #c3c7c4;
	  padding-left: 15px;
     padding-top: 15px;
	 
}
.aside_link{
	width:100%;
	height:120px;
	 background-color: #c3c7c4;
}

</style>

   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
      
      <h6><a href = "${pageContext.request.contextPath}">home</a> > <a href = "${pageContext.request.contextPath }/question/choose">문제 종류 고르기</a>
      </h6>
        <div class = "for_aside">
      		<div class="section_area">
      <h1>test</h1>

    
      <br>
      <br>
    
	      			<c:forEach var = "test" items="${list}">
	        			<form action = "${pageContext.request.contextPath }/question/questype">
	        				<div class="post-preview">
	        		
	        						<div class = "question-float ">
	 					   					<input type = "hidden" name ="tno" value= "${test.tno}">
	 					   					<div class= "question-name"> <h2> ${test.test_category} </h2></div>
	           								<div class= "question-submit"><input class="btn btn-primary"type = "submit" value = "선택"></div>	  
							 		</div>
	 						</div>
	 					</form>
	 				
	 				</c:forEach>
	 			</div>
	 		<div class ="aside_area">
	 			
	 				<div class= "member_info1">
	 						<h6>${id}님</h6>
	 						<h6>레벨 : 5   포인트 : 100</h6>
	 						<h6>토큰:100  <a href="${pageContext.request.contextPath }/pay/list">충전</a></h6>
	 				</div>
	 				<hr>
	 				<div	class = "aside_link">
	 					
	 				</div>
	 				
	 			</div>
   		</div>
      </div>
    </div>

 </body>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>