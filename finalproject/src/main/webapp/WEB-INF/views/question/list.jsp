<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>
<c:set var="isMine" value="${id == users.id}"></c:set>
<c:set var="isAdmin" value="${grade == '관리자'}"></c:set>
<script>
	$(function(){
		//Modal trigger button 숨기기
// 		$(".doHide").hide();
		//Modal 선택전 게시글 숨기기
// 		$(".showList").hide();
		//숨겨진 button을 클릭하여 Modal을 페이지 로딩과 함께 불러오기
// 		$(".doHide").trigger("click");
		//만일 '한문제 풀기' 클릭시 게시판 출력하기
// 		$(".oneQuestion").click(function(){
// 			$(".showList").show();
// 		});
		//문제
		//1. 2번째 페이지에서 모달창이 새로 생성됨
// 		var pno = "<c:out value='${pno}'/>";
// 		if(pno>1){
// 			$(".showList").show();
// 			$(".modal").hide();
// 		}
		//2. 검색버튼 누르면 모달창이 새로 생성됨.
		

		//랜덤버튼 누르면 문제 개수 고르기
		$(".list").hide();
		$(".multiQuestion").click(function(){
			$(".list").show();
		});
		
		
		$(".click").click(function(){
			$(".list").show();
		});
		var selected_val = $("#selectBox option:selected").val();
	});
</script>
<style>
th{
}
#page-wrapper {
    padding-left: 250px;
}
  
#sidebar-wrapper {
  position: absolute;
    width: 230px;
    height: 740px;
    margin-left: -485px;
    font-size: smaller;
    font-family: sans-serif;
    background: #343A40;
    overflow-x: hidden;
    overflow-y: auto;
}

#page-content-wrapper {
  width: 100%;
  padding: 20px;
}
/* 사이드바 스타일 */

.sidebar-nav {
  width: 250px;
  margin: 0;
  padding: 0;
  list-style: none;
}

.sidebar-nav li {
  text-indent: 1.5em;
  line-height: 2.8em;
}

.sidebar-nav li a {
  display: block;
  text-decoration: none;
  color: #999;
}

.sidebar-nav li a:hover {
  color: black;
  background: rgba(255, 255, 255, 0.2);
}

.sidebar-nav > .sidebar-brand {
  font-size: 1.3em;
  line-height: 3em;
}

</style>
<!-- 모달 영역 -->
<!-- <button type="button" class="btn doHide" data-toggle="modal" data-target="#myModal"></button> -->
<!-- <div class="modal fade" id="myModal"> -->
<!-- 	<div class="modal-dialog"> -->
<!-- 		<div class="modal-content"> -->
<!-- 모달 헤드 -->
<!-- 			<div class="modal-header"> -->
<!-- 				<h4 class="modal-title">문제 풀이 방법</h4> -->
<!-- 				<button type="button" class="close" data-dismiss="modal">&times;</button> -->
<!-- 			</div> -->
<!-- 모달 바디 -->
<!-- 			<div class="modal-body d-flex justify-content-between"> -->
<!-- 				<div class="oneQuestion"><a class="btn btn-primary" data-dismiss="modal">한문제씩 풀기</a></div> -->
<!-- 				<div><a class="btn btn-primary multiQuestion">랜덤문제 풀기</a></div> -->

<!-- 			</div> -->
<!-- 모달 풋터 -->
<!-- 			<div class="modal-footer d-flex justify-content-between"> -->
<!-- 				<button class="click">문제 수</button> -->
<!-- 				<div class="list">문제 개수 : -->
<!-- 				<select name = "number" id="selectBox" onchange="if(this.value) location.href=(this.value)"> -->
<%-- 					<c:forEach var="QuestionNumber" items="${list}" end="20" varStatus="status"> --%>
<%-- 						<option class="optionVal" value="${pageContext.request.contextPath}/question/multi?wantQuestion=${status.count}">${status.count}</option> --%>
<%-- 					</c:forEach> --%>
<!-- 				</select> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->
<div class="showList">
<div class="container">
<div><h1>유저 문제 목록</h1></div>
	<table class="table table-hover">
		<thead class="thead-light">
			<tr>
				<th>No.</th>
				<th>제목</th>
				<th>조회수</th>
				<th>정답비율</th>
				<th>출제자</th>
				<c:if test="${grade=='관리자'}">
				<th>문제 확인</th>
				</c:if>
			</tr>
		</thead>
		<tbody>	
			<c:forEach var="question" items="${list}">
				<tr>
					<td>${question.question_no}</td>
					<td><a href="solve?question_no=${question.question_no}">${question.question_title}</a></td>
					<td>${question.read_count}</td>
					<td>${question.correct_ratio}%</td>
					<td>${question.id}</td>
					<c:if test="${grade=='관리자'}">
						<td><a href="content?question_no=${question.question_no}">확인</a></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<!-- 네비게이션 -->
	<div class="container" style="text-align: center">
		<jsp:include page="/WEB-INF/views/template/navigator.jsp">
			<jsp:param name="pno" value="${pno}" />
			<jsp:param name="count" value="${count}" />
			<jsp:param name="navsize" value="${navsize}" />
			<jsp:param name="pagesize" value="${pagesize}" />
			<jsp:param name="board_category" value="${board_category}"/>
		</jsp:include>
	</div>
	<div class="d-flex justify-content-between">
		<div><a type="button" href="${pageContext.request.contextPath}/question/upload" type="button" class="btn btn-secondary">문제 업로드 <i class="material-icons">cloud_upload</i></a></div>
		<!-- 검색창 -->
		<div class="d-flex justify-content-center">
		<form action="list" method="post">		
			<select name="option">
				<option value="question_title">제목</option>
				<option value="id">출제자</option>
			</select>
			<input type="text" name="keyword" required>
			<input type="submit" class="searchKeyword btn btn-secondary" value="검색">
		</form>
		</div>
		<div>
		<a class="click btn btn-primary">랜덤문제 풀기</a>
		<span class="list">문제 개수 :
		<select name = "number" id="selectBox" onchange="if(this.value) location.href=(this.value)">
			<c:forEach var="QuestionNumber" items="${list}" varStatus="status">
				<option class="optionVal" value="${pageContext.request.contextPath}/question/multi?wantQuestion=${status.count}">${status.count}</option>
			</c:forEach>
		</select>
		</span>
		</div>
	</div>
</div>
</div>

<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>