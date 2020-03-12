<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>    
<script src="https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script>

	$(function () {
	  $('[data-toggle="tooltip"]').tooltip()
	})
	
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
		$("#id_check_btn").click(function(){
			var id = $("input[name=id]").val();
			console.log($("#ck").prop("checked"));
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
								$("input[name=email]").focus();
								$("#ck").removeAttr("checked");

								console.log($("#ck").prop("checked"));
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
	
	//기본이미지 선택
	$(document).ready(function(){
	    $("#basic").change(function(){
	        if($("#basic").is(":checked")){
	        	$("#file").hide();
	        }
	        else{
	        	$("#file").show();
	        }
	    });
	});
	
	//비밀번호 형식 검사
	$(function(){
		
		$("#join").click(function(){
			var pw = $("input[name=pw]").val();
			var regex = /^(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9])(?=.*[0-9]).{8,16}$/;
			
			if(!regex.test(pw)){
				window.alert("비밀번호 형식을 맞춰주세요.");
				$("input[name=pw]").focus().val("");
				$("input[name=ppww]").val("");
			}
			
			
		});
	});	
	
	//비밀번호 재확인 && 아이디 중복검사 안눌렀을때
	$(function(){
		
		$("#join").click(function(){
			var pw = $("input[name=pw]").val();
			var ppww = $("input[name=ppww]").val();
			
			console.log(pw);
			console.log(ppww);
			
			//비밀번호 x
			if(pw != ppww){
				console.log("비번x")
				//아이디 x
				if($("#ck").prop("checked")){
					console.log("비번x 아이디x")
					window.alert("아이디 중복검사를 해주세요.");
					$("#id_check_btn").focus();
					return false;
				}
				//아이디 o
				else if(!$("#ck").prop("checked")){
					window.alert("비밀번호가 맞지 않습니다.");
					$("input[name=ppww]").focus().val("");
				}
				
			}
			//비밀번호 o
			else if(pw == ppww){
				console.log("비번O")
				//아이디 x
				if($("#ck").prop("checked")){
					console.log("비번o 아이디x")
					window.alert("아이디 중복검사를 해주세요.");
					$("#id_check_btn").focus();
					return false;
				}
				//아이디 o
				else if(!$("#ck").prop("checked")){
					window.alert("회원가입을 완료하였습니다.");
				}
			}
		});
	});
	
	//이메일 인증
// 	$(function(){
		
// 	$(".validate").hide();
// 	$(".join_box").hide();
// 	$(".go_join").hide();
	
// 	$(".email").submit(function(e){
// 		e.preventDefault();
		
// 		//var url = $(this).attr("action"); 
// 		var method = $(this).attr("method");
// 		var data = $(this).serialize();
		
// 		var email = $("input[name=email]").val();
		
// 		if(email == ""){
// 			window.alert("이메일을 입력해주세요.");
// 			$("input[name=email]").focus();
// 			$(".email").find("input[type=submit]").val("인증번호 보내기");
// 		}
		
// 		else{
// 			$(this).find("input[type=submit]").prop("disabled", true);
// 			$(this).find("input[type=submit]").val("인증번호 발송중");
			
// 			$.ajax({
// 				url:"${pageContext.request.contextPath}/users/send",
// 				type:"get",
// 				data:data,
// 				success:function(resp){
// 					//console.log(resp);
// 					if(resp == "success"){
// 						$(".email").find("input[type=submit]").val("인증번호 발송완료");
// 						$(".validate").show();
// 					}
// 				}
// 			});
// 		}
// 	});
 
// 	$(".validate").submit(function(e){
// 		e.preventDefault();
// 		//var url = $(this).attr("action"); 
// 		var method = $(this).attr("method");
// 		var data = $(this).serialize();
		
// 		$.ajax({
// 			url:"${pageContext.request.contextPath}/users/validate",
// 			type:"get",
// 			data:data,
// 			success:function(resp){
// 				if(resp == "success"){
// 					window.alert("인증이 완료되었습니다.");
// 					$(".cert_box").hide();
// 					$(".join_box").show();
// 				}
// 				else{
// 					window.alert("인증 실패! 다시 시도해주세요");
// 					window.location.reload();
// 				}
// 			}
// 		});
// 	});
// });
	
</script>
<style>
* {
	font-family: binggrae;
	box-sizing: border-box;
}
.container {
    max-width: 1140px;
    margin: auto auto;
}
h1 {
	margin-top: 10px;
    margin-bottom: 30px;
    text-align: center;
    font-size: 62px;
    letter-spacing: -1px;
}
p{
	font-size: 18px;
	margin-top: 18px;
	margin-bottom: 6px;
}
.pp{
	font-size: 12px;
    margin-top: 5px;
    margin-bottom: -20px;
    margin-right: 10px;
    text-align: right;
}
.cert_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 25px;
    padding-bottom: 25px;
    padding-left: 20px;
    padding-right: 20px;
    margin: auto 232px;
    margin-top: 10px;
    height: 280px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
.cert_box input[name=email] {
	font-size: 20px;
    border: 0px;
    outline: #efefef;
    border-bottom: 1px solid #565960;
    width: 290px;
    height: 3rem;
    padding: 15px;
    margin-top: 20px;
}
.cert_box input[name=cert] {
	font-size: 20px;
    border: 0px;
    outline: #efefef;
    border-bottom: 1px solid #565960;
    width: 290px;
    height: 3rem;
    padding: 15px;
    margin-top: 15px;
    margin-left: 0px;
}
#but1 {
	font-size: 18px;
    padding: 5px 5px;
    height: 45px;
    width: 165px;
}
#but2 {
	font-size: 18px;
    padding: 5px 5px;
    height: 45px;
    width: 165px;
}
#but1:hover, #but2:hover{
	font-size: 19px;
    padding: 5px 5px;
    height: 45px;
    width: 165px;
}

.join_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 20px;
    padding-bottom: 10px;
    padding-left: 50px;
    padding-right: 10px;
    margin: auto 232px;
    margin-top: 10px;
    height: 900px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
.join_box input[type=text] {
	font-size: 18px;
	border: 0px;
	outline: #efefef;
	border-bottom: 1px solid #565960;
	width: 280px;
	height: 2rem;
	padding: 20px;

}

.join_box input[type=password] {
	font-size: 18px;
	border: 0px;
	outline: #efefef;
	border-bottom: 1px solid #565960 ;
	width: 280px;
	height: 2rem;
	padding: 20px;

}
.join_box input[type=tel] {
	font-size: 18px;
	border: 0px;
	outline: #efefef;
	border-bottom: 1px solid #565960;
	margin-left: 2px;
	width: 150px;
	height: 2rem;
	padding: 20px;

}
#select1 {
	border-bottom: 1px solid #565960 ;
    padding: 5px;
    padding-left: 0.5rem;
    padding-right: 2rem;
  	margin: 3PX;
	font-size: 20px;
	height: 43px;
	width: 200px;
	border: 0px;
	border-bottom: 1px solid  #565960; 
}
#select2 {
	border-bottom: 1px solid #565960 ;
    padding: 5px;
    padding-left: 0.5rem;
    padding-right: 2rem;
  	margin: 10PX;
  	margin-left: 0px;
	font-size: 20px;
	width: 100px;
	border: 0px;
	border-bottom: 1px solid  #565960;
}
.addr>#postcode {
	width: 280px;
	margin-bottom: 6px;
}
.addr>#address {
	width: 444px;
}
.addr>#detailAddress {
	width: 280px;
	margin: 6px 0px;
}
.addr>#extraAddress {
	width: 160px;
}
#join {
	font-size: 18px;
    margin: 25px 10px 0px 420px;
    padding: 5px 10px;
    height: 40px;
    width: 150px;
}
#join:hover {
	font-size: 19px;
    margin: 25px 10px 0px 420px;
    padding: 5px 10px;
    height: 40px;
    width: 150px;
}
@media screen and (max-width:1155px){
	
	.cert_box{
		display: inline-block;
	    width: 660px;
	    border: 1px solid #ddd;
	    padding-top: 25px;
	    padding-bottom: 25px;
	    padding-left: 20px;
	    padding-right: 20px;
	    margin: auto 200px;
	    margin-top: 10px;
	    height: 280px;
	    overflow: hidden;
	    border-left-width: 2px;
	    background-color: white;
	    border-radius: 5%;
	}
	
	.join_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 20px;
    padding-bottom: 10px;
    padding-left: 50px;
    padding-right: 10px;
    margin: auto 200px;
    margin-top: 10px;
    height: 900px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
}
@media screen and (max-width:1080px){
	
	.cert_box{
		display: inline-block;
	    width: 660px;
	    border: 1px solid #ddd;
	    padding-top: 25px;
	    padding-bottom: 25px;
	    padding-left: 20px;
	    padding-right: 20px;
	    margin: auto 160px;
	    margin-top: 10px;
	    height: 280px;
	    overflow: hidden;
	    border-left-width: 2px;
	    background-color: white;
	    border-radius: 5%;
	}
	
	.join_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 20px;
    padding-bottom: 10px;
    padding-left: 50px;
    padding-right: 10px;
    margin: auto 160px;
    margin-top: 10px;
    height: 900px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
}
@media screen and (max-width:960px){
	
	.cert_box{
		display: inline-block;
	    width: 660px;
	    border: 1px solid #ddd;
	    padding-top: 25px;
	    padding-bottom: 25px;
	    padding-left: 20px;
	    padding-right: 20px;
	    margin: auto 110px;
	    margin-top: 10px;
	    height: 280px;
	    overflow: hidden;
	    border-left-width: 2px;
	    background-color: white;
	    border-radius: 5%;
	}

	.join_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 20px;
    padding-bottom: 10px;
    padding-left: 50px;
    padding-right: 10px;
    margin: auto 110px;
    margin-top: 10px;
    height: 900px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
}
@media screen and (max-width:890px){

	.cert_box{
		display: inline-block;
	    width: 660px;
	    border: 1px solid #ddd;
	    padding-top: 25px;
	    padding-bottom: 25px;
	    padding-left: 20px;
	    padding-right: 20px;
	    margin: auto 90px;
	    margin-top: 10px;
	    height: 280px;
	    overflow: hidden;
	    border-left-width: 2px;
	    background-color: white;
	    border-radius: 5%;
	}
	
	.join_box{
	display: inline-block;
    width: 660px;
    border: 1px solid #ddd;
    padding-top: 20px;
    padding-bottom: 10px;
    padding-left: 50px;
    padding-right: 10px;
    margin: auto 90px;
    margin-top: 10px;
    height: 900px;
    overflow: hidden;
    border-left-width: 2px;
    background-color: white;
    border-radius: 5%;
}
}

</style>
<div class="container">

<h1>Qmaster</h1>

<!-- <div class="cert_box"> -->
<!-- 	<div style="text-align:center"> -->
<!-- 		<form class="email" method="get" > -->
<!-- 			<h3>본인 확인을 위한 이메일 인증을 해주세요!</h3> -->
<!-- 			<input type="text" name="email" placeholder="이메일 입력"> -->
<!-- 			<input type="submit" class="btn btn-primary" id="but1" value="인증번호 보내기"> -->
<!-- 		</form> -->
<!-- 	</div> -->
	
<!-- 	<div style="text-align:center"> -->
<!-- 		<form class="validate" action="validate" method="post"> -->
<!-- 			<input type="text" name="cert" placeholder="인증번호 입력"> -->
<!-- 			<input type="submit" class="btn btn-primary" id="but2" value="인증하기"> -->
<!-- 			<div class="finish_cert"></div> -->
<!-- 		</form> -->
<!-- 	</div> -->
<!-- </div> -->

<div class="join_box">
<form class="join-form" action="join" method="post" enctype="Multipart/form-data">
	<p class="pp">*은 필수 입력 항목입니다.<p>
	<p>NAME*</p> 
	<input type="text" name="name" maxlength="7" required>
	
	
	<p>ID*</p> 
	<input type="text" name="id" maxlength="20" data-toggle="tooltip" data-placement="top" title="8~20자 영소문자,숫자" required>
	<input type="button" class="btn btn-primary" id="id_check_btn" value="아이디 중복 검사" required>
	<input type="checkbox" id="ck" name="ck" hidden="hidden" checked="checked">
	
	<p>EMAIL*</p>
	<input type="text" name="email1" required>
		  <select name="email2" id="select1">
		  	<option>@naver.com</option>
		  	<option>@daum.net</option>
		  	<option>@nate.com</option>
		  	<option>@gmail.com</option>
		  	<option>@hanmail.com</option>
		  	<option>@icloud.com</option>
		  </select>
	
	<p>PASSWORD*</p> 
	<input type="password" name="pw" maxlength="16" data-toggle="tooltip" data-placement="top" title="8~16자 영문자,숫자,특수문자" required>
	<input type="password" name="ppww" placeholder="비밀번호 재입력" required>
	
	<p>PHONE*</p>
		<select name="phone" id="select2">
				<option>010</option>
				<option>011</option>
				<option>016</option>
				<option>017</option>
				<option>019</option>
			</select>-
			<input type="tel" name="phone" maxlength="4" required> -
			<input type="tel" name="phone" maxlength="4" required>
			
	<p>ADDRESS</p>
	<div class="addr">
	<input type="text" id="postcode" name="postcode" placeholder="우편번호" > 
	<input type="button" class="btn btn-primary" onclick="DaumPostcode()"value="우편번호 찾기"><br> 
	<input type="text" id="address" name="address" placeholder="주소" ><br> 
	<input type="text" id="detailAddress" name="detailAddress" placeholder="상세주소" onfocus="loadMap()" > 
	<input type="text" id="extraAddress" name="extraAddress" placeholder="참고항목">
	</div>
	
	<p>PROFILE</p>
	<input type="checkbox" id="basic">기본이미지 선택하기
	<input type="file" name="user_file" id="file"  accept="image/*"><br>
	
	<input type="submit" class="btn btn-primary" id="join" value="회원가입하기"> 
</form>
</div>
</div>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>