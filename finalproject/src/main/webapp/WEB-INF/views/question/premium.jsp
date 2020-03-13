<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
     <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/style.css?version=20200122">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700|Open+Sans:400,400i,700,700i|Source+Code+Pro&amp;subset=korean" rel="stylesheet">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/css/result.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/custom.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/theme-colors/blue.css?version=20200122">
 <jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
    
    <style>
    
    .btn {
    font-size: 14px;
    font-weight: 800;
    padding: 10px 12px;
    letter-spacing: 1px;
    text-transform: uppercase;
    border-radius: 0;
    font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
}
    
    .container{
    width: 60%;
    
    }
    
    .h6, h6 {
    font-size: 3rem;
}
.member_info1{
	width : 36%;
    margin-left: 297px;
    height: 250px;
    overflow: inherit;
    border-left-width: 7px;
    background-color: #eee;
    padding: 2rem;
        margin-top: 59px;
}
    </style>
    
<div class="container">
		<div id = "member_zone" class= "member_info1">
			
${users.point}


<button type="button"  onclick=" location.href='${pageContext.request.contextPath}/pay/premium?point=' +${users.point}">프리미엄으로</button>

	 				</div>



</div>




<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>