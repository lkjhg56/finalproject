<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<style>
.row{
	margin-top:30px;
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
							<input type = "submit" style="button" class="btn btn-primary" value = "${list.price} Token"> 
						</form>
						<br>
					</c:forEach>
				<h4>충전된 토큰은 환불되지 않습니다.</h4>
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

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
