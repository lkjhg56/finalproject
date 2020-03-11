<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">

   <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/style.css?version=20200122">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700|Open+Sans:400,400i,700,700i|Source+Code+Pro&amp;subset=korean" rel="stylesheet">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/css/result.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/custom.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/theme-colors/blue.css?version=20200122">
 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
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



<style>

*{

  font-family: sans-serif;
}

.select_area::after{
	 content:"";
    display:block;
    clear:both;
    flex-wrap:wrap-reverse;
}
.method_area{
	display: block;
	 float:left;
    width:70%;

}
.session_area{
	display: inline-block;
    width:44%;

}
.btn_area{
		display: inline;
    width:100px;

}





.section_area {
    margin-top: 129px;
    padding-left: 112px;
}

.headline{
border-bottom: none;

}
#select1 {

	
  	border-bottom: 1px solid #565960 ;

    padding: 5px;

    padding-left: 0.5rem;

    padding-right: 2rem;

  	margin: 3PX;

	font-size: 15px;

	height: 43px;

	width: 200px;

	border: 0px;

	
    background-color: lightgrey;
	

}




.select1 {

	

   	border-bottom: 1px solid #565960 ;

    padding: 5px;

    padding-left: 0.5rem;

    padding-right: 2rem;

  	margin: 3PX;

	font-size: 15px;

	height: 43px;

	width: 200px;

	border: 0px;

    background-color: lightgrey;


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

.member_info1{
	width : 100%;
	height: 150px;
	
	  padding-left: 15px;
     padding-top: 20px;
     padding-right : 15px;
    
	  border-radius: 5%;
	   font-family: sans-serif;
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

    height: 164px;

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

	.bbb{

	width: 472px;

  

    margin: 25px auto;

    height: 137px;

    overflow: inherit;

    border-left-width: 2px;

    background-color: white;

    border-radius: 5%;

	}



.member_info1 {
    width: 100%;
    height: 121px;
    padding-left: 15px;
    padding-top: 23px;
    padding-right: 15px;
    border-radius: 5%;
    font-family: sans-serif;
    text-align: center;
}

.col-lg-8 {
    -ms-flex: 0 0 66.666667%;
    flex: 0 0 66.666667%;
    max-width: 66.666667%;
    padding-bottom: 11px;
}



a {
    color: 64676C;
    transition: all 0.2s;
}

.h6, h6 {
    font-size: 1.5rem;
}


.h3, h3 {
    font-size: 2.75rem;
}


.h5, h5 {
    font-size: 1.75rem;
}


.headline{
	 float:left;
    width:30%;
    min-height:600px;
    border-bottom: none;
}




.aside_link{
	width:100%;
	min-height:500px;
	   border: 1px #FFFFFF;
	  padding-top :10px;
	  padding-left :10px;
	  font-size:16px;
}

.init{
	padding-top : 7px;
	 font-family: sans-serif;
color: steelblue;
    font-weight: bold;
}

#slider .slide .text {
    width: 100%;
    height: 100%;
    color: black;
    text-transform: uppercase;
    font-size: 16px;
    border: 1px #FFFFFF;
    border-radius: 5%;
}


.btn-info {
    color: #fff;
    background-color: lightgrey;
    border-color: lightgrey;
}

.h1, h1 {
    font-size: 10.5rem;
    color: lightgrey;
    margin-bottom: 18px;
    margin-left: 35px;
}


.ccc {
  width: 219px;
    margin: 16px auto;
    height: 385px;
    overflow: inherit;
    border-left-width: 7px;
    background-color: #eee;
    
}



</style>



 
 
 
   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
      
      			<br>
      			<div class = "for_aside">
      				<div class="section_area">
    
      			<br>
				<h1><i class="far fa-edit"></i></h1>

				<form action="${pageContext.request.contextPath}/question/questype" onsubmit="appendResult()">

					<input type="hidden"  name="tno" value="${tno}">
					<input type="submit"  class="btn btn-info" value="다시풀기">
					
					
					<input type="button" onclick="location.href='${pageContext.request.contextPath}/question/choose'" value="목록으로" class="btn btn-info" >
				<%-- <a type="button" class="btn btn-primary" href="${pageContext.request.contextPath}/question/choose">목록으로</a>&nbsp;&nbsp; --%>
				</form>

				
				</div>
				
				
				<div class ="headline">
	 			<div class="aaa">
	 				<div id = "member_zone" class= "member_info1">
	 						
	 				</div>
	 					</div>
	 						<div class="ccc">
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
</div>

  <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>


<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
