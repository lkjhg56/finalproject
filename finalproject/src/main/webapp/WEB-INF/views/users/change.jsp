<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<c:set var="context" value="${pageContext.request.contextPath}"></c:set>

<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>

	//카카오 주소 api
	function DaumPostcode() {
		new daum.Postcode({
			oncomplete : function(data) {
				// 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.
	
				// 각 주소의 노출 규칙에 따라 주소를 조합한다.
				// 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
				var addr = ''; // 주소 변수
				var extraAddr = ''; // 참고항목 변수
	
				//사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
				if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
					addr = data.roadAddress;
				} else { // 사용자가 지번 주소를 선택했을 경우(J)
					addr = data.jibunAddress;
				}
	
				// 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
				if (data.userSelectedType === 'R') {
					// 법정동명이 있을 경우 추가한다. (법정리는 제외)
					// 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
					if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
						extraAddr += data.bname;
					}
					// 건물명이 있고, 공동주택일 경우 추가한다.
					if (data.buildingName !== '' && data.apartment === 'Y') {
						extraAddr += (extraAddr !== '' ? ', '
								+ data.buildingName : data.buildingName);
					}
					// 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
					if (extraAddr !== '') {
						extraAddr = ' (' + extraAddr + ')';
					}
					// 조합된 참고항목을 해당 필드에 넣는다.
					document.getElementById("extraAddress").value = extraAddr;
	
				} else {
					document.getElementById("extraAddress").value = '';
				}
	
				// 우편번호와 주소 정보를 해당 필드에 넣는다.
				document.getElementById('postcode').value = data.zonecode;
				document.getElementById("address").value = addr;
				// 커서를 상세주소 필드로 이동한다.
				document.getElementById("detailAddress").focus();
			}
		}).open();
	}
</script>
<div class="container">
<h1>정보 수정</h1>

<form action="change" method="post">
<table class="table" border="1">
	<tr>
    <th>
    	프로필<br>
    </th>
    <td class="mom">
	<!--     	기본이미지 -->
    <c:choose>
    	<c:when test="${empty userFileDto}">
	    	<img src="userimg?user_no=101" id="img" width="120" height="120"><br>
    	</c:when>
    	<c:otherwise>
	    	<img src="userimg?user_no=${users.user_no}" id="img" width="120" height="120"><br>
    	</c:otherwise>
    </c:choose>
    </td>
  </tr>
  <tr>
    <th>이름</th>
    <td>${users.name}</td>
  </tr>
  <tr>
    <th>아이디</th>
    <td>${users.id}</td>
  </tr>
  <tr>
    <th>비밀번호</th>
    <td>
		<a href="pw/input"><input type="button" value="비밀번호 변경하기"></a>
	</td>
  </tr>
  <tr>
    <th>이메일</th>
    <td>${fn:replace(users.email, ",", "") }</td>
  </tr>
  <tr>
    <th>전화번호</th>
    <td>
		<select name="phone">
				<option>${users.phone.substring(0,3)}</option>
				<option>010</option>
				<option>011</option>
				<option>016</option>
				<option>017</option>
				<option>019</option>
			</select>-
			<input type="tel" name="phone" maxlength="4" value="${users.phone.substring(4,8)}" required>-
			<input type="tel" name="phone" maxlength="4" value="${users.phone.substring(9,13)}" required>
			<br><br>
    </td>
  </tr>
  <tr>
    <th>주소</th>
    <td>
		<input type="text" id="postcode" name="postcode" value="${users.postcode}" required> 
		<input type="button" onclick="DaumPostcode()" value="우편번호 찾기"><br> 
		<input type="text" id="address" name="address" value="${users.address}" required><br> 
		<input type="text" id="detailAddress" name="detailAddress" value="${users.detailAddress}" onfocus="loadMap()" required> 
		<input type="text" id="extraAddress" name="extraAddress" value="${users.extraAddress}" ><br>
	</td>
  </tr>
  <tr>
    <th>포인트</th>
    <td>${users.point}</td>
  </tr>
  <tr>
    <th>등급 포인트</th>
    <td>${users.grade_point}</td>
  </tr>
  <tr>
    <th>등급</th>
    <td>${users.grade}</td>
  </tr>
  <tr>
    <th>가입일</th>
    <td>${users.join_date.substring(0,10)}</td>
  </tr>
</table>
	<input type="submit" value="수정하기"> 
</form>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>