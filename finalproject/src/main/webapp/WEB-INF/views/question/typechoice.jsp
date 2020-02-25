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
		console.log("checkcheck1")
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
			console.log(data);
			
			var win = window.open(
					"${pageContext.request.contextPath}/question/questcategory?"+ data, "win", "width=1500, height=1000");
		});
	});	



</script>

<style>
.select_area::after{
	 content:"";
    display:block;
    clear:both;
    flex-wrap:wrap-reverse;
}
.method_area{
	 float:left;
    width:35%;

}
.session_area{
	 float:left;
    width:35%;

}
.btn_area{
	 float:left;
    width:30%;

}


</style>

 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>




   <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
		<h6><a href = "${pageContext.request.contextPath}">home</a> > <a href = "${pageContext.request.contextPath }/question/choose">문제 종류 고르기</a>
      				><a href = "#">문제 고르기</a>
      	</h6>
      	
			<div class = "for_aside">
      			<div class="section_area">
      	
					<h1>시험 목록</h1>

							<c:forEach var="list" items="${list}">

								<div>
									<form method="post" name="form1"  action="${pageContext.request.contextPath}/question/solving">
									<input type="hidden" name="tno" value="${tno}">
										<input type="hidden" name="hour" value="${list.lim_hour}"> 
										<input
											type="hidden" name="min" value="${list.lim_min}"> 
											<input
											type="hidden" name="categoryname" value="${list.csname}">
									
										<div>${list.csname}</div>
										<div class= "select_area">
												<div class = "method_area">
													<select class="method" name="method"
													onchange="callCategory('${list.csname}')">
											
							
															<option>방법 선택하세요</option>
																<option>한문제씩풀기</option>
																<option>한번에풀기</option>
															</select>
												</div>
												<div class = "session_area">
														 <select name="session" id="${list.csname}">
							
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
 

  </body>
 
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
