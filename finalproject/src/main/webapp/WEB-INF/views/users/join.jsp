<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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
	
	//아이디 중복 검사
	$(function(){
		//아이디 중복 검사를 누르면(click) 비동기통신으로 아이디 유무를 검사
		$(".id_check_btn").click(function(){
			var id = $("input[name=id]").val();
// 			console.log(id);

			if(id == ""){
				$(".id_check_btn").next(".id").text("아이디를 입력해주세요.");
				
				$("input[name=id]").click(function(){
					$(".id").empty();
				});
				
			}
			else{
				
				$.ajax({
					url:"${pageContext.request.contextPath}/users/id_check",
					type:"get",
					data:{ id : id },
					dataType:"text",
					success:function(resp){
	// 					console.log(resp);
						if(resp === "Y"){//사용중
							$(".id_check_btn").next(".id").text("사용중인 아이디입니다.");
						}
						else if(resp === "N"){//사용가능
							$(".id_check_btn").next(".id").text("사용가능한 아이디입니다.");
						}
					}
				});
			}
		});
	});
	
	//비밀번호 재확인
	$(function(){
		$("input[name=ppww]").blur(function(){
			var pw = $("input[name=pw]").val();
			var ppww = $(this).val();
			
			if(pw != ppww){
				$("input[name=ppww]").next(".pw").text("비밀번호가 맞지 않습니다.");				
				
				$("input[name=ppww]").click(function(){
				$(".pw").empty();
				});
				
			}
			
			
		});
	});
	
</script>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>회원가입</h1>
<form action="join" method="post">

	이름 : <input type="text" name="name" required><br><br>
	아이디 : <input type="text" name="id" required>
	<input type="button" class="id_check_btn" value="아이디 중복 검사">
	<div class="id"></div>
	<br>
	비밀번호 : <input type="text" name="pw" required><br><br>
	비밀번호 확인 : <input type="text" name="ppww" required>
	<div class="pw"></div>
	<br>
	이메일 : <input type="text" name="email" required><br><br>
	전화번호 : <input type="text" name="phone" required><br><br>
	<input type="text" id="postcode" name="postcode" placeholder="우편번호" required> 
	<input type="button" onclick="DaumPostcode()"value="우편번호 찾기"><br> 
	<input type="text" id="address" name="address" placeholder="주소" required><br> 
	<input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소" onfocus="loadMap()" required> 
	<input type="text" id="extraAddress" name="extraAddress" placeholder="참고항목"><br><br>
	<input type="submit" class="join" value="회원가입하기"> 
	
</form>