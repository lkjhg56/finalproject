<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>    
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
	
	//아이디 중복&형식 검사 
	$(function(){
		$(".id_check_btn").click(function(){
			var id = $("input[name=id]").val();
// 			console.log(id);

			var regex = /^[a-z0-9]{8,20}$/;

			if(id == ""){
				window.alert("아이디를 입력해주세요.");
				$("input[name=id]").focus();
				
				
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
							
							 if(regex.test(id)){
								window.alert("이미 사용중인 아이디입니다.");
								$("input[name=id]").focus().val("");
							 }
							 else{
								 window.alert("아이디 형식을 맞춰주세요.");
					            $("input[name=id]").focus().val("");
							 }
						
						}
						
						else if(resp === "N"){//사용가능
							
							if(regex.test(id)){
								window.alert("사용 가능한 아이디입니다.");
								$("input[name=pw]").focus();
							}
							else{
								 window.alert("아이디 형식을 맞춰주세요.");
					            $("input[name=id]").focus().val("");
							 }
						
						}
					}
				});
			}
		});
	});
	
	//비밀번호 형식 검사
// 	$(function(){
		
// 		$("input[name=pw]").on("input",function(){
// 			var pw = $("input[name=pw]").val();
// 			var regex = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,16}$/;
			
// 			if(regex.test(pw)){
// 				console.log("굳굳!!!!!!!");
// 			}
// 			else{
// 				console.log("노노노!!!!!!");
// 			}
			
// 		});
// 	});	
	$(function(){
		
		$(".join").click(function(){
			var pw = $("input[name=pw]").val();
			var regex = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,16}$/;
			
			if(!regex.test(pw)){
				window.alert("비밀번호 형식을 맞춰주세요.");
				$("input[name=pw]").focus().val("");
				$("input[name=ppww]").val("");
			}
			
			
		});
	});	
	
	//비밀번호 재확인
	$(function(){
		
		$(".pw_check_btn").click(function(){
			var pw = $("input[name=pw]").val();
			var ppww = $("input[name=ppww]").val();
			
			
			if(pw != ppww){
				window.alert("비밀번호가 맞지 않습니다.");
				$("input[name=ppww]").focus().val("");
			}
			else{
				$("input[name=email]").focus();
			}
			
			
		});
	});
	
	//이메일 인증
	$(function(){
//		.validate-form은 처음에 숨기고 이메일 전송시만 표시
	$(".validate").hide();
	$(".join-form").hide();
	$(".go_join").hide();
	
//		.email-form이 전송되면 send 주소로 비동기 신호를 전송(ajax)
	$(".email").submit(function(e){
		e.preventDefault();
		
		//var url = $(this).attr("action"); 
		var method = $(this).attr("method");
		var data = $(this).serialize();
		
		var email = $("input[name=email]").val();
		
		if(email == ""){
			window.alert("이메일을 입력해주세요.");
			$("input[name=email]").focus();
			$(".email").find("input[type=submit]").val("인증번호 보내기");
		}
		
		else{
			$(this).find("input[type=submit]").prop("disabled", true);
			$(this).find("input[type=submit]").val("인증번호 발송중...");
			
			$.ajax({
				url:"${pageContext.request.contextPath}/users/send",
				type:"get",
				data:data,
				success:function(resp){
					//console.log(resp);
					if(resp == "success"){
						$(".email").find("input[type=submit]").val("인증번호 발송완료");
						$(".validate").show();
					}
				}
			});
		}
	});
//		validate-form이 전송되면 /validate로 비동기 요청을 전송
	$(".validate").submit(function(e){
		e.preventDefault();
		//var url = $(this).attr("action"); 
		var method = $(this).attr("method");
		var data = $(this).serialize();
		
		$.ajax({
			url:"${pageContext.request.contextPath}/users/validate",
			type:"get",
			data:data,
			success:function(resp){
				if(resp == "success"){
					window.alert("인증이 완료되었습니다.");
					$(".email").hide();
					$(".validate").hide();
					$(".join-form").show();
				}
				else{
					window.alert("인증 실패! 다시 시도해주세요");
					window.location.reload();
				}
			}
		});
	});
});
	
</script>

<h1>회원가입</h1>
<form class="email" action="send" method="post">
	<h2>본인 확인을 위한 이메일인증을 해주세요.</h2>
	<input type="text" name="email" placeholder="이메일 입력">
	<input type="submit" value="인증번호 보내기">
</form>

<form class="validate" action="validate" method="post">
	<input type="text" name="cert" placeholder="인증번호 입력">
	<input type="submit" value="인증하기">
	<div class="finish_cert"></div>
</form>

<form class="join-form" action="join" method="post">

	이름 : <input type="text" name="name" maxlength="7" required><br><br>
	아이디 : <input type="text" name="id" maxlength="20" required>
	<input type="button" class="id_check_btn" value="아이디 중복 검사">
	<div class="id">
		<h6>8~20자의 영문 소문자와 숫자만 사용 가능합니다.</h6>
	</div>
	<br>
	비밀번호 : <input type="text" name="pw" maxlength="16" required><br><br>
	<div class="pw">
		<h6>8~16자 영문자, 숫자, 특수문자 조합을 사용하세요.</h6>
	</div>
	비밀번호 확인 : <input type="text" name="ppww" required>
			   <input type="button" class="pw_check_btn" value="확인">
	<br><br>
	이메일 : <input type="text" name="email" required>
		  <select name="email">
		  	<option>@naver.com</option>
		  	<option>@daum.net</option>
		  	<option>@nate.com</option>
		  	<option>@gmail.com</option>
		  	<option>@hanmail.com</option>
		  	<option>@icloud.com</option>
		  </select>
			<br><br>
	전화번호 : <select name="phone">
				<option>010</option>
				<option>011</option>
				<option>016</option>
				<option>019</option>
			</select>-
			<input type="tel" name="phone" maxlength="4" required>-
			<input type="tel" name="phone" maxlength="4" required>
			<br><br>
	주소 : <br><br>
	<input type="text" id="postcode" name="postcode" placeholder="우편번호" required> 
	<input type="button" onclick="DaumPostcode()"value="우편번호 찾기"><br> 
	<input type="text" id="address" name="address" placeholder="주소" required><br> 
	<input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소" onfocus="loadMap()" required> 
	<input type="text" id="extraAddress" name="extraAddress" placeholder="참고항목"><br><br>
	<input type="submit" class="join" value="회원가입하기"> 
	
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>