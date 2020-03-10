<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 
 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>

<style>

*{

  font-family: sans-serif;
}
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

.aside_link{
	width:100%;
	min-height:500px;
	   border: 1px #FFFFFF;
	  padding-top :10px;
	    padding-bottom :10px;
	  padding-left :10px;
	  font-size:16px;
	   border-radius: 5%;
	  
}
 #slider .slide .text {
            width: 100%;
            height: 100%;
            color: black;
            text-transform: uppercase;
            font-size: 17px;
             border: 1px #FFFFFF;
              border-radius: 5%;
          
        }
.aaa{
	width: 207px;
    border: 2px solid #ddd;
    margin: 30px auto;
    height: 171px;
    overflow: inherit;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
.post-preview > a {
  color: #212529;
}

.post-preview > a:focus, .post-preview > a:hover {
  text-decoration: none;
  color: #0085A1;
}

.post-preview > a > .post-title {
  font-size: 30px;
  margin-top: 30px;
  margin-bottom: 10px;
}

.post-preview > a > .post-subtitle {
  font-weight: 300;
  margin: 0 0 10px;
  
}

.fa-list-alt{

 
}

.section_area{
	
margin-top: 30px;
}
</style>

   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
      
      <h6><a href = "${pageContext.request.contextPath}">home</a> > <a href = "${pageContext.request.contextPath }/question/choose">문제 종류 고르기</a>
      </h6>
        <div class = "for_aside">
      		<div class="section_area">
      <h1><i class="far fa-list-alt"  id="far fa-list-alt"></i>  Q	master LIST</h1>

    
      <br>
      <br>
    
	      			<c:forEach var = "test" items="${list}">
	        		
		 				 <div class="post-preview">
					          <a href="${pageContext.request.contextPath }/question/questype?tno=${test.tno}">
							            <h2 class="post-title">
							            
							            </h2>
							            <h3 class="post-subtitle">
							               ${test.test_category}
							            </h3>
					     		     </a>
					      		   
					            </p>
				        </div>
				        
				        
	       				 <div style = "width:80%"><hr></div>
	 				</c:forEach>
	 			</div>
	 		<div class ="aside_area" >
	 			<div class="aaa">
	 				<div id = "member_zone" class= "member_info1">
	 					
	 				</div>
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
	 					 <div style = "width:100%"><hr></div>
	 			</div>
   		</div>
      </div>
    </div>

 </body>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>