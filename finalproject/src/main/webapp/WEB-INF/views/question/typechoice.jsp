<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>


<script>
	function callCategory(csname) {
		 var selection= document.getElementsByName(csname);
		 $(selection).show();
		
		
		var option = document.getElementById(csname)

		$.ajax({
			url : "${pageContext.request.contextPath}/question2/callcategory",
			type : "post",
			data : {
				csname : csname
			},
			datatype : "json",
			success : function(quesList) {
	
			
		
				$(option).empty();
				$(quesList).each(function() {
					$("<option>").text(this).appendTo(option)
					
				})

			}
		});
	};
	
	

	function resultSave(csname, tno, userid) {
		$.ajax({
			url : "${pageContext.request.contextPath}/question2/resultin",
			type : "post",
			data : {
				csname : csname,
				tno : tno,
				id : userid
			}

		})

	}
	var count = 1;

	function openWin() {

		var check = $("form[name=form1]");
		var data = check.serialize();
		var win = window.open(
				"${pageContext.request.contextPath}/question/questcategory?"
						+ data, "win", "width=450, height=500");

		// 		window.open("", 'POP');

		// 		check.action = "${pageContext.request.contextPath}/question/questcategory";
		// 		check.categoryname.value=csname;
		// 		check.target='POP';

		// 		check.submit();
	}
	
	$(function(){
	
		
		$(".btn").hide();
		
		$("form[name=form1]").submit(function(e){

		
		
	
			var data = $(this).serialize();//데이터를 전송 가능한 문자열로 변환

			
			var win = window.open(
					"${pageContext.request.contextPath}/question/questcategory?"+ data, "win", "width=1500, height=1000");
		});
	});	

	
	
	 $(document).ready(function() { 
		  
			$.ajax({

				url : "${pageContext.request.contextPath}/question2/newwindow",

				type : "post",

				data : {

					

				},

			   success:function(){
		          	
	    		   

	    		   
	    		
		     		
		     	}

			})
		  
		  
	  
	  });


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



.section_area{
	
margin-top: 30px;
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

</style>

 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>




   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
		<h6><a href = "${pageContext.request.contextPath}">HOME</a> > <a href = "${pageContext.request.contextPath }/question/choose">Q-Master LIST</a>
      				><a href = "#">TEST LIST</a>
      	</h6>
      	
			<div class = "for_aside">
      			<div class="section_area">
      			
      			
      	
					<h1><i class="far fa-list-alt"  id="far fa-list-alt"></i>  TEST LIST</h1>

							<c:forEach var="list" items="${list}">

								<div class="bbb">
								
									<form method="post" name="form1"  action="${pageContext.request.contextPath}/question/solving">
									<input type="hidden" name="tno" value="${tno}">
										<input type="hidden" name="hour" value="${list.lim_hour}"> 
										<input
											type="hidden" name="min" value="${list.lim_min}"> 
											<input
											type="hidden" name="categoryname" value="${list.csname}">
									
										<div><h5>${list.csname}</h5></div>
										<div class= "select_area">
												<div class = "method_area">
													<select class="method" name="method"
													onchange="callCategory('${list.csname}')" id="select1">
											
							
															<option>방법 선택하세요</option>
																<option>한문제씩풀기</option>
																<option>한번에풀기</option>
															</select>
															
															
												</div>
												
												
												<div class = "session_area">
														 <select name="session" id="${list.csname}" class="select1">
							
															<option>회차를 선택하세요</option>
														</select> 
													</div>
													
													
													<div class = "btn_area">
															<input class="btn btn-secondary" type="submit" name ="${list.csname}" value="선택" onclick="resultSave('${list.csname}', '${tno}', '${id}')">
													</div>
			
			
			</div>
		
		</form>



	</div>



</c:forEach>
					</div>
					
					
	 				<div class ="aside_area">
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
 </div>

  </body>
 
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
