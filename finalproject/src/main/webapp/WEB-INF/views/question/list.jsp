<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<script>
	$(function(){
		//Modal trigger button 숨기기
		$(".doHide").hide();
		//pno 불러오기
		var pno = "<c:out value='${pno}'/>";
		//첫페이지에서만 모달창 불러오기
// 		var triggerClick =
		if(pno==1){
			 $(".doHide").trigger("click");
		}	
// 		var triggerSearch = $(".searchKeyword").click();
// 		if(triggerSearch){
// 			removeEventListener(triggerClick);
// 		}
		$(".list").hide();
		$(".multiQuestion").click(function(){
			$(".list").show();
			$(".oneQuestion").hide();
		});
		$(".click").click(function(){
			$(".list").show();
		});
		var selected_val = $("#selectBox option:selected").val();	
	});
</script>
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
		<div><a type="button" href="${pageContext.request.contextPath}/question/upload" type="button" class="btn btn-secondary">문제 업로드</a></div>
		<!-- 검색창 -->
		<div class="d-flex justify-content-center">
		<form action="list" method="post">		
			<select name="option">
				<option value="category_name">카테고리</option>
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

<!-- 모달 영역 -->
<button type="button" class="btn doHide" data-toggle="modal" data-target="#myModal"></button>
<div class="modal fade" id="myModal">
	<div class="modal-dialog">
		<div class="modal-content">
			<!-- 모달 헤드 -->
			<div class="modal-header">
				<h4 class="modal-title">문제 풀이 방법</h4>
				<button type="button" class="close" data-dismiss="modal">&times;</button>
			</div>
			<!-- 모달 바디 -->
			<div class="modal-body d-flex justify-content-between">
				<div class="oneQuestion"><a class="btn btn-primary" data-dismiss="modal">한문제씩 풀기</a></div>
				<div><a class="btn btn-primary multiQuestion">랜덤문제 풀기</a></div>

			</div>
			<!-- 모달 풋터 -->
			<div class="modal-footer d-flex justify-content-between">
				<!-- <button class="click">문제 수</button> -->
				<div class="list">문제 개수 :
				<select name = "number" id="selectBox" onchange="if(this.value) location.href=(this.value)">
					<c:forEach var="QuestionNumber" items="${list}" end="20" varStatus="status">
						<option class="optionVal" value="${pageContext.request.contextPath}/question/multi?wantQuestion=${status.count}">${status.count}</option>
					</c:forEach>
				</select>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>