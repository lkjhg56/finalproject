<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> 
   <!-- 필요 정보: rno,  category no, tno-->
 
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/style.css?version=20200122">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700|Open+Sans:400,400i,700,700i|Source+Code+Pro&amp;subset=korean" rel="stylesheet">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/css/result.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/custom.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/theme-colors/blue.css?version=20200122">
   
 
<script>


function previewImage(target){    
    if(target.files && target.files[0]){       
        var reader = new FileReader();
        reader.onload = function(data){
            var img = document.querySelector("#preview");
            img.src = data.target.result;    
        }
        reader.readAsDataURL(target.files[0]);
    }
}


function selectcorrect(abc) {
	
            console.log(abc+"정답 가져오기");
    
      
    
   		 $.ajax({
 	     	url:"${pageContext.request.contextPath}/question2/delete2",
 	    	 type:"post",
 	    	 data:{test_no:abc},
 	    	 
 	    	 
 	    	   success:function(resp){
 	          	
 	    		   
 	    		   
 	    		   
 	    		   
 	     		console.log(resp+"check");
 	     		var checked= document.getElementById(resp);
 	     	    
 	              
 	     	$(checked).css("color","red");
 	            }
 	     		
 	     
 		   })
	
	 
}


</script>
   
   
   <style>

body {
    color: #333;
    font-size: 18px;
    line-height: 1.6;
}


main{
            display:flex;
            flex-wrap: wrap;
        }
        header{
            width:100%;
        }

        aside{
            width:300px;
        }
        section{
            flex-grow:1;
            min-height: 600px;
        }


	*{
            box-sizing:border-box;
        }
      

	th{
		white-space : nowrap;
	}
	
	
.table{

	

    width: 655px;

    margin: 15px 10px;

    margin-left: 10px;

    margin-top: 30px;

    height: 315px;

    overflow: hidden;

	

	}

	.aaa{

	width: 680px;

    border: 2px solid #ddd;

    margin: 30px auto;

    height: 330px;

    overflow: inherit;

    border-left-width: 2px;

    background-color: white;

    border-radius: 5%;

	}
	
	.jumbotron{
	margin-top: 10px;
	background-color: #17a2b8;
    color: white;
    border-color: #17a2b8;

    padding: .5rem 1rem;
    font-size: 3rem;
    line-height: 1.5;
    border-radius: .3rem;
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    font-weight: 800;
    letter-spacing: 1px;
    text-transform: uppercase;
    font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    user-select: none;
    border: 1px solid transparent;
 
	}
	
	
	
	.jumbotron1{
	
	background-color: 658384;
    color: white;
    border-color: 658384;

    padding: .5rem 1rem;
    font-size: 3rem;
    line-height: 1.5;
    border-radius: .3rem;
    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;
    font-weight: 800;
    letter-spacing: 1px;
    text-transform: uppercase;
    font-family: 'Open Sans', 'Helvetica Neue', Helvetica, Arial, sans-serif;
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    user-select: none;
    border: 1px solid transparent;
     margin-bottom: 10px;
 
	}
</style>
   
   <header>

 <div class="container" >
 
 



<div class="jumbotron">
<h2> ${csname} ${category_no}</h2>
<h3>점수 ${score}점</h3>


</div>

   
<%-- <h1>시험결과</h1>
<h5>점수</h5>
<h5>풀린 횟수 ${solved}</h5>
<h5>이시험 평균 점수 ${average}</h5>
<h5>순위 : ${rank} 백분위 : ${percentile}</h5>
<h5>정답수 : ${iscorrect}</h5>

<h5>오답수 : ${qcount}</h5>

<h5>상위 10% 평균 :${high10average}</h5>
<h5>상위 25% 평균 :${high25average}</h5>
<h5>상위 50% 평균 :${high50average}</h5>
<h5>상위 75% 평균 :${high75average}</h5> --%>


<!-- <hr> -->
<div class="aaa">
<table  class="table" >
  
    <tr align = "center" >
    
	<td>제목</td>
	<td colspan = "3"> ${csname}  ${categoryname} ${category_no}</td>
	
	
    </tr>
    <tr>
    
	<td>백분위</td>
	<td>${percentile}</td>
	<td>순위</td>
    <td>${rank}</td>
    </tr>
    
    <tr>
    
	<td>정답수</td>
	<td>${iscorrect}</td>
	<td>오답수</td>
     <td>${qcount}</td>
    </tr>
    <tr>
    
	<td>풀린횟수</td>
	<td>${solved}</td>
	<td>평균점수</td>
     <td>${average}</td>
    </tr>
   
   

</table>
</div>
<!-- <hr> -->


<div class="jumbotron1">

<h3>상세해설</h3>

</div>

<c:forEach var="alist" items="${vo}">





			<div class = "plural-question" >
	 	
	${alist.question} <br>
	 <div class = "plural-pic" ><img id="preview" src="qimage?no=${alist.no}"  class="test"  onload="selectcorrect('${alist.no}');"  width="20%" height="200"></div><br><br> 
<%-- <input type="image" name="image" onload="selectcorrect('${alist.no}');"> --%>

1. ${alist.dis1}<br>
	2. ${alist.dis2}<br>
	3. ${alist.dis3}<br>
	4. ${alist.dis4}<br>
	5. ${alist.dis5}<br><br>    
	 
	 
	 <div>해설</div>${alist.solution}<br><br>    
	 
	 입력한 정답 :
	 <c:choose>
			<c:when test="${alist.correct==0}">
				<font color="red">답을 적지 않으셨습니다.</font><br>
			</c:when>
			<c:otherwise>
				${alist.correct}<br>
			</c:otherwise>
		</c:choose>  
	 
	
	정답 여부 : 
		<c:choose>
			<c:when test="${alist.iscorrect==1}">
				정답<br>
			</c:when>
			<c:otherwise>
				<font color="red">오답</font><br>
			</c:otherwise>
		</c:choose>
	
	 정답여부  ${alist.iscorrect}<br><br>    
	</div>  
	
	
	
	  <hr> 	
	
	   					  
  
</c:forEach>



<%-- <h2>입력한 답</h2>

<h4>
	<c:forEach var="rlist" items="${rCorrectDto}">
	 <div class="${rlist.correct} ">${rlist.correct} </div> 
	</c:forEach>
</h4> --%>





</div>

</header>
<jsp:include page="/WEB-INF/views/template/footer.jsp"></jsp:include>