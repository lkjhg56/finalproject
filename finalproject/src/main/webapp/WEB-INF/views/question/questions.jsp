<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
      
      <h1>test</h1>
      
      
      <br>
      <br>
      <br>
      
      		<c:forEach var = "test" items="${list}">
        	<form action = "${pageContext.request.contextPath }/question/questype">
        		<div class="post-preview">
 					   	<input type = "hidden" name ="tno" value= "${test.tno}">
           					 <h2>
          				  		  ${test.test_category}         	<input type = "submit" value = "선택">	  
         					   </h2>
         			
					
						
 						</form>
 					 </div>
 			</c:forEach>
 
   		</div>
      </div>
    </div>
  </div>
 </body>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>