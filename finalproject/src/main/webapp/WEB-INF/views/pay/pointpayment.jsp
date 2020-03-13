<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
</script>
<link href="${pageContext.request.contextPath}/res/static/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<style>
.row{
	margin-top:30px;
	font-family : sans-serif;
}
</style>   
<div class="container">
	<div class="row">
 		<div class="col-lg-8 col-md-10 mx-auto">   	
  			<div class = "for_aside">
    			<div class="section_area">
  					<h1>토큰 충전</h1>

					 <c:forEach var="list" items = "${list}">
						<form action = "${pageContext.request.contextPath }/pay/kakao/payment" method = "post">
							<input type = "hidden" name = "partner_user_id" value ="${id}">
							<input type = "hidden" name = "item_name" value = "${list.product}">
							<input type = "hidden" name = "total_amount" value = "${list.price}">
							<input type = "hidden" name = "quantity" value = "1">
							<input type = "hidden" name = "tax_free_amount" value = "${list.tax_free_amount}">
							<input type = "hidden" name = "token" value = "${list.token}">
							<strong><fmt:formatNumber type="number" value="${list.price}"/>원</strong><br>
							<input type = "submit" style="button" class="btn btn-primary" value = "<fmt:formatNumber type="number" value="${list.price/10}"/> Token"> 
						</form>
						<br>
					</c:forEach>
				<h5>충전된 토큰은 환불되지 않습니다.</h5>
				</div>
			</div>
			<div class ="aside_area">
				<div class="aaa">
					<div id = "member_zone" class= "member_info1">
					</div>
				</div>
				<div class = "aside_link">
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
