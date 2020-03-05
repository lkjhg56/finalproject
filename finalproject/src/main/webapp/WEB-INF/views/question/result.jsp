<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> 
   <!-- 필요 정보: rno,  category no, tno-->
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
   
   
   
   
   
 <div class="container" >
   
<h1>시험결과</h1>
<h5>점수 ${score}</h5>
<h5>풀린 횟수 ${solved}</h5>
<h5>이시험 평균 점수 ${average}</h5>
<h5>순위 : ${rank} 백분위 : ${percentile}</h5>
<h5>정답수 : ${iscorrect}</h5>

<h5>오답수 : ${qcount}</h5>

<h5>상위 10% 평균 :${high10average}</h5>
<h5>상위 25% 평균 :${high25average}</h5>
<h5>상위 50% 평균 :${high50average}</h5>
<h5>상위 75% 평균 :${high75average}</h5>




<table border="1"  width ="80%" height="300" align = "center" >
  
    <tr align = "center" bgcolor="#54a17a">
    
	<td>제목</td>
	<td colspan = "3"> ${csname} ${category_no}</td>
	
	
    </tr>
    <tr>
    
	<td>점수</td>
	<td>${score}</td>
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


<h1>상세해설</h1>

<c:forEach var="alist" items="${normalupdateQuestionVO}">


<div>${alist.iscorrect }</div>
<div>${alist.correct }</div>


			<div class = "plural-question" >
	 	
	${alist.question} <br>
	 <div class = "plural-pic" ><img id="preview" src="qimage?no=${alist.no}"  class="test"  onload="selectcorrect('${alist.no}');"  width="20%" height="200"></div><br><br> 
<%-- <input type="image" name="image" onload="selectcorrect('${alist.no}');"> --%>

<div id="1" class="1">1.</div>${alist.dis1}<br>
	<div id="2" class="2">2.</div>${alist.dis2}<br>
	<div id="3" class="3">3.</div>${alist.dis3}<br>
	<div id="4" class="4">4.</div>${alist.dis4}<br>
	<div id="5" class="5">5.</div>${alist.dis5}<br><br>    
	 
	</div>  
	
	
	
	   	
	
	   					  
  
</c:forEach>



<h2>입력한 답</h2>

<h4>
	<c:forEach var="rlist" items="${rCorrectDto}">
	 <div class="${rlist.correct} ">${rlist.correct} </div> 
	</c:forEach>
</h4>


</div>