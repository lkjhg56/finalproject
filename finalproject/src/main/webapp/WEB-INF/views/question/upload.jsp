<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script>
//첨부파일 이미지 미리보기

function previewImage(target){
    
    //[1] target에 선택된 파일의 목록을 불러온다.
    // - 구조상 파일선택은 복수개가 가능하다(현재는 불가)
    // - 따라서 target.files는 배열의 형태를 띈다(0번 인덱스만 사용)
    //console.log(target.files);
    //[2] 선택된 파일이 있는지 확인한 뒤 읽기 작업 실행
    // - 목록이 있는지 검사하고 있다면 0번 인덱스에 파일이 있는지 검사
    // - 파일을 선택하지 않아도 이벤트가 발생하기 때문에 문제를 사전 차단
    
    if(target.files && target.files[0]){
        
        //[3] 읽기 도구 생성(자바스크립트 내장형태)
        var reader = new FileReader();
        //[4] 읽기 완료시 할 작업을 예약 설정(콜백이라 부름)
        reader.onload = function(data){//data는 읽은 파일의 정보
            //[6] 파일 읽기가 완료되면 내용을 img 태그의 src에 설정
            // - 상황에 맞게 본인이 원하는 대상으로 변경하여 사용
            var img = document.querySelector("#preview");
            img.src = data.target.result;    
        }
        //[5] 실제 읽기 명령을 수행
        reader.readAsDataURL(target.files[0]);
    }
}
</script>
<jsp:include page="/WEB-INF/views/template/header.jsp"></jsp:include>
<h1>문제 업로드</h1>
<form action="upload" method="post" enctype="multipart/form-data">
	<input type="hidden" name="user_id" value="${id}"><br><br>
	<input type="text" name="question_title" placeholder="제목" required><br><br>
	<input type="text" name="category_name" placeholder="카테고리명" required><br><br>
	<textarea name="question_content" placeholder="문제명" rows="10" cols="50"></textarea><br><br>
	<img id="preview" src="http://placehold.it/200x200" width="120" height="120"><br>	
	<input type="file" name="file" multiple accept="image/gif,image/jpg,image/jepg,image/png" onchange="previewImage(this);"><br><br>
	<input type="text" name="answer1" placeholder="선택지 1 입력" required><br><br>
	<input type="text" name="answer2" placeholder="선택지 2 입력" required><br><br>
	<input type="text" name="answer3" placeholder="선택지 3 입력" required><br><br>
	<input type="text" name="answer4" placeholder="선택지 4 입력" required><br><br>
	<input type="text" name="answer5" placeholder="선택지 5 입력" required><br><br>
	<input type="text" name="question_answer" placeholder="정답 입력" required><br><br>
	<input type="text" name="question_solution" placeholder="해설입력" required><br><br>
	<input type="submit" value="등록하기">
	<input type="reset" value="초기화">
</form>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>
