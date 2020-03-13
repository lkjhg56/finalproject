<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
</script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script> 
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link href="${pageContext.request.contextPath}/res/static/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<script>
    	
$(function () {
	$(".afterconfirm").hide();

	$(".closeWindow").click(function(){
		window.close();
	});
});
        
function confirm(num, point, id){
	$.ajax({
		url : "${pageContext.request.contextPath}/paycheck/confirm",
		type : "post",
		data : {
		num : num,
		point : point,
		id : id,
		},
		success:function(resp){
			var htmls=""
			$(".beforeconfirm").hide();
			$(".afterconfirm").show();
			htmls+=	'<h4>결제가 완료 되었습니다.</h4>';
			$(".afterconfirm").html(htmls);
		}		
	})
}

function revoke(num){
	$.ajax({
		url : "${pageContext.request.contextPath}/paycheck/cancel",
		type : "post",
		data : {
			num : num,
		},
		success:function(resp){
			var htmls=""
			$(".beforeconfirm").hide();
			$(".afterconfirm").show();
			htmls+=	'<h4>결제가 취소 되었습니다.</h4>';
			$(".afterconfirm").html(htmls);
		}
	})
}
</script>
<style>
	.row{
		margin-top:30px;
		font-family : sans-serif;
	}
</style>   
<div class="container">
	<div class="row">
	<div class="col-lg-8 col-md-10 mx-auto">  
		<br>
		<div class = "for_aside">
			<div class="section_area">
				<div class = "beforeconfirm">   						
					<h4>${token} 만큼 충전 됨 현재 포인트는 ${point}
					</h4>
					<input type = button value ="구매확정" onclick="confirm('${payno}', '${point}', '${id}')">
					<input type = button value = "취소" onclick= "revoke('${payno}')">
				</div>
				<div class= "afterconfirm">
					<button class="btn btn-secondary closeWindow">닫기</button>
				</div>  
			</div>
		</div>
		</div>
	</div>
</div>


					