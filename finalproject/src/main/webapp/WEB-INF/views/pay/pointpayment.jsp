<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
  <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
  <c:forEach var="list" items = "${list}">
<form action = "${pageContext.request.contextPath }/pay/kakao/payment" method = "post">
	<h1>${list.token}토큰충전</h1>
	<input type = "hidden" name = "partner_user_id" value ="${id}">
	<input type = "hidden" name = "item_name" value = "${list.product}">
	<input type = "hidden" name = "total_amount" value = "${list.price}">
	<input type = "hidden" name = "quantity" value = "1">
	<input type = "hidden" name = "tax_free_amount" value = "${list.tax_free_amount}">
	<input type = "hidden" name = "token" value = "${list.token}">
	<input type = "submit" value = "${list.price}원결제"> 
</form>

</c:forEach>