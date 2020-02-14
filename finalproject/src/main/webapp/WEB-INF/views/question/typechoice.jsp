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

			/* e.preventDefault();//기본전송 방지 */
		
	
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

.member_info{
	 border-spacing: 15px;
    background-color: #AAA5A4;
    width:100%;
   
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
										<input type="hidden" name="hour" value="${list.lim_hour}"> <input
											type="hidden" name="min" value="${list.lim_min}"> <input
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
