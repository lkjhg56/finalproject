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
    height:50px;
}
.question-name{
	 float:left;
    width:47%;
    height:50px;
}
.question-submit{
	 float:left;
    width:20%;
    height:50px;
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
	 			
	 				<div id = "member_zone" class= "member_info1">
	 					
	 				</div>
	 				<div	class = "aside_link">
	 					
	 				</div>
	 				
	 			</div>
   		</div>
      </div>
    </div>

 </body>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>