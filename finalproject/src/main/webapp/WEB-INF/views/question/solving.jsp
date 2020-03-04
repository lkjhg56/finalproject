<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<script>
function appendResult() {

	 

	$.ajax({

		url : "${pageContext.request.contextPath}/question2/newwindow",

		type : "post",

		data : {

			

		}



	})



}  

</script>



 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
 
 
 
   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
      
      			<br>
      			<div class = "for_aside">
      				<div class="section_area">
    
      			<br>
				<h1>문제푸는중</h1>

				<form action="${pageContext.request.contextPath}/question/questype" onsubmit="appendResult()">

					<input type="hidden"  name="tno" value="${tno}">
					<input type="submit"  class="btn btn-info" value="돌아가기">
				</form>
				</div>
	 			<div class ="aside_area">
	 			
	 				<div id = "member_zone" class= "member_info1">
	 			
	 				</div>

	 				<div	class = "aside_link">
	 						<div id = "slider">
	 						<div class="slides-container">
		 							<div class = "mRank slide">
				 					</div>
				 					<div class = "mNewList slide">
				 					</div>
				 					<div class = "mFQlist slide">
				 					</div>
			 					</div>
	 					</div>
	 					
	 				</div>
	 				
	 			</div>
				
		</div>
	</div>
</div>

  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
