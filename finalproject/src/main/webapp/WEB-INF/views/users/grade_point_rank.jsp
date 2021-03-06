<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
<c:set var="login" value="${not empty id}"></c:set>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>
// $('#myModal').on('shown.bs.modal', function () {
// 	  $('#myInput').trigger('onmouseover')
// 	})
$(function () {
  $('[data-toggle="tooltip"]').tooltip()
})

</script>
<style>
.fa-star{
	color:yellow;
}
.footer-color {
    margin-top: 100px;
}
#btn{
	padding: 3px 10px;
}
</style>
<div class="container">
<h1 align="center">포인트 랭킹</h1>
<c:if test="${login}">
<jsp:include page="/WEB-INF/views/template/sidebar.jsp"></jsp:include>
</c:if>
<!-- Bye Modal -->
<div class="modal fade" id="bye" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="exampleModalLabel">QMaster</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h5>정말 탈퇴 하시겠습니까?</h5>
      </div>
      <div class="modal-footer">
        <a href="${context}/users/bye"><button type="button" class="btn btn-primary">네</button></a>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">아니요</button>
      </div>
    </div>
  </div>
</div>
<!-- Logout Modal -->
<div class="modal fade" id="logout" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="exampleModalLabel">QMaster</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <h5>정말 로그아웃 하시겠습니까?</h5>
      </div>
      <div class="modal-footer">
        <a href="${context}/users/logout"><button type="button" class="btn btn-primary">네</button></a>
        <button type="button" class="btn btn-secondary" data-dismiss="modal">아니요</button>
      </div>
    </div>
  </div>
</div>
<div  style="text-align:center">	
	<table class="table table-hover">
		
		<thead align="center" class="thead-dark">
			<tr>
				<th>랭킹</th>
				<th>
				<button type="button" id="btn" class="btn btn-secondary" 
				data-toggle="modal" data-target="#exampleModal" >Level</button>
				</th>
				<th>이름</th>
				<th>포인트</th>
			</tr>
		</thead>
		
		<tbody align="center">
		
			<c:forEach var="grade_point_rank" items="${grade_point_rank}">
				<tr>
					<td>
						<c:choose>
							<c:when test="${grade_point_rank.rank == 1}">
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								1등
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
							</c:when>
							<c:when test="${grade_point_rank.rank == 2}">
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
								2등
								<i class="fa fa-star"></i>
								<i class="fa fa-star"></i>
							</c:when>
							<c:when test="${grade_point_rank.rank == 3}">
								<i class="fa fa-star"></i>
								3등
								<i class="fa fa-star"></i>
							</c:when>
							<c:otherwise>${grade_point_rank.rank}등</c:otherwise>
						</c:choose>
					</td>
					<td>
						<c:choose>
							<c:when test="${grade_point_rank.grade_point <= 5}">Level 1</c:when>
							<c:when test="${ 5 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 20}">Level 2</c:when>
							<c:when test="${ 20 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 50}">Level 3</c:when>
							<c:when test="${ 50 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 100}">Level 4</c:when>
							<c:when test="${ 100 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 200}">Level 5</c:when>
							<c:when test="${ 200 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 400}">Level 6</c:when>
							<c:when test="${ 400 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 800}">Level 7</c:when>
							<c:when test="${ 800 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 1600}">Level 8</c:when>
							<c:when test="${ 1600 < grade_point_rank.grade_point && grade_point_rank.grade_point <= 3200}">Level 9</c:when>
							<c:otherwise>Level 10</c:otherwise>
						</c:choose>
					</td>
					<td>${grade_point_rank.name}</td>
					<td>${grade_point_rank.grade_point}</td>
				</tr>
			</c:forEach>
		</tbody>
		
	</table>
	
	<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h4 class="modal-title" id="exampleModalLabel">Level 기준표</h4>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body" style="text-align: left">
        <h5>Level 1 : 0 ~ 5 Point</h5>
        <h5>Level 2 : 6 ~ 20 Point</h5>
        <h5>Level 3 : 21 ~ 50 Point</h5>
        <h5>Level 4 : 51 ~ 100 Point</h5>
        <h5>Level 5 : 101 ~ 200 Point</h5>
        <h5>Level 6 : 201 ~ 400 Point</h5>
        <h5>Level 7 : 401 ~ 800 Point</h5>
        <h5>Level 8 : 801 ~ 1600 Point</h5>
        <h5>Level 9 : 1601 ~ 3200 Point</h5>
        <h5>Level 10 : 3201 ~ 6400 Point</h5>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
    </div>
  </div>
</div>

	<div class="row">
		<!-- 네비게이터(navigator) -->
		<jsp:include page="/WEB-INF/views/template/navigator.jsp">
			<jsp:param name="pno" value="${pno}"/>
			<jsp:param name="count" value="${count}"/>
			<jsp:param name="navsize" value="${navsize}"/>
			<jsp:param name="pagesize" value="${pagesize}"/>
		</jsp:include>
	</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>		


