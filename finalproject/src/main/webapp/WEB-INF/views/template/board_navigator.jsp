<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>
.pagination {
  display: -ms-flexbox;
  display: inline-flex;
  padding-left: 0;
  list-style: none;
   border: none;
    font-family: 'Noto Sans';
   font-weight: 400;
   font-size: 12px;
}

.page-link {
  color: black;
  float: left;
  padding: 10px 16px;
  text-decoration: none;
  border: none;
  margin: 0px;
}

.page-link:hover {
  color: black;
  text-decoration: none;
  background-color: white;
  border:none;
}

.page-item.active .page-link {
  background-color: #118FCB;
  color: white;
  border-color: none;
   border: none;
}

.page-item:first-child .page-link{
    border-top-left-radius: .0rem;
    border-bottom-left-radius: .0rem;
    margine : 0px, 0px, 0px, -1px;
      border: none;
}

.page-item:last-child .page-link {
  border-top-right-radius: .0rem;
  border-bottom-right-radius: .0rem;
    border: none;
}

.page-link:focus {
  outline: 0;
  box-shadow: none;
}

	.container nav{
    width: 100%;
    padding-right: 15px;
    padding-left: 15px;
    margin-right: auto;
    margin-left: auto;
    padding-top: 5px; 
    margin-bottom: -35px;
}

.pagination a:hover:not(.page-item active) {background-color: none;}
</style>

<!-- 네비게이터(navigator) -->
<!-- 
	반드시 받아야 하는 데이터 : type, keyword, pno, count, navsize, pagesize, board_category, board_no
 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="board_no" value="${param.board_no}"></c:set> 
<c:set var="board_category" value="${param.board_category}"></c:set>
<c:set var="type" value="${param.type}"></c:set>
<c:set var="keyword" value="${param.keyword}"></c:set> 
<c:set var="isSearch" value="${not empty type and not empty keyword}"></c:set> 
<c:set var="isCategory" value="${not empty board_category}"></c:set>
<c:set var="isReply" value="${not empty board_no}"></c:set>
<c:set var="pno" value="${param.pno}"></c:set> 
<c:set var="count" value="${param.count}"></c:set> 
<c:set var="navsize" value="${param.navsize}"></c:set> 
<c:set var="pagesize" value="${param.pagesize}"></c:set> 

<!-- EL은 parseInt가 없기 때문에 fmt의 formatNumber 태그를 통해 처리 -->
<fmt:parseNumber var="startBlock" integerOnly="true" value="${(pno - 1) / navsize}"></fmt:parseNumber>
<c:set var="startBlock" value="${startBlock * navsize + 1}"></c:set> 

<c:set var="finishBlock" value="${startBlock + (navsize - 1)}"></c:set> 
<fmt:parseNumber var="pageCount" value="${(count + pagesize) / pagesize}"></fmt:parseNumber>

<c:if test="${finishBlock > pageCount}">
	<c:set var="finishBlock" value="${pageCount}"></c:set>
</c:if>

<!-- 포워딩 구조에서는 주소 확인 명령이 달라짐 -->
<c:set var="uri" value="${requestScope['javax.servlet.forward.request_uri']}"></c:set>
<div class="container">
<ul class="pagination">

	<!-- 이전 버튼 -->
	<c:if test="${startBlock > 1}">
		<c:choose>
			<c:when test="${isSearch}">
				<li class="page-item"><a class="page-link" href="${uri}?type=${type}&keyword=${keyword}&pno=${startBlock-1}">이전</a></li>
			</c:when>
			<c:when test="${isCategory}">
				<li class="page-item"><a class="page-link" href="${uri}?board_category=${board_category}&pno=${startBlock-1}">이전</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="${uri}?pno=${startBlock-1}">&laquo;</a></li>
			</c:otherwise>
		</c:choose>
	</c:if>
    
    <c:forEach var="i" begin="${startBlock}" end="${finishBlock}">
    	<c:choose>
    		<c:when test="${i == pno}">
				<li class="page-item active"><a class="page-link" href="#">${i}</a></li>
			</c:when>
			<c:otherwise>
				<c:choose>
					<c:when test="${isSearch}">
						<li class="page-item"><a class="page-link" href="${uri}?type=${type}&keyword=${keyword}&pno=${i}">${i}</a></li>
					</c:when>
					<c:when test="${isCategory}">
						<li class="page-item"><a class="page-link" href="${uri}?board_category=${board_category}&pno=${i}">${i}</a></li>
					</c:when>
					<c:when test="${isReply}">
						<li class="page-item"><a class="page-link" href="${uri}?board_no=${board_no}&pno=${i}">${i}</a></li>
					</c:when>
					<c:otherwise>
						<li class="page-item"><a class="page-link" href="${uri}?pno=${i}">${i}</a></li>
					</c:otherwise>
				</c:choose>
			</c:otherwise>
		</c:choose>
	</c:forEach>
    
    <!-- 다음 버튼 -->
    <c:if test="${finishBlock < pageCount}">
    	<c:choose>
			<c:when test="${isSearch}">
				<li class="page-item"><a class="page-link" href="${uri}?type=${type}&keyword=${keyword}&pno=${finishBlock+1}">다음</a></li>
			</c:when>
			<c:when test="${isCategory}">
				<li class="page-item"><a class="page-link" href="${uri}?board_category=${board_category}&pno=${finishBlock+1}">다음</a></li>
			</c:when>
			<c:when test="${isReply}">
				<li class="page-item"><a class="page-link" href="${uri}?board_no=${board_no}&pno=${finishBlock+1}">다음</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="${uri}?pno=${finishBlock+1}">&raquo;</a></li>	
			</c:otherwise>
		</c:choose>
	</c:if>
    
</ul>
</div>