<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script>
   function resultcheck(quesno, disno, answer){
       var test = quesno+disno
		console.log(test+"확인")
       var checked= document.getElementById(test);
       var checkbox2= document.getElementsByName(quesno);


       if (checked.checked==true){
           console.log("체크됨")
           for(var i=0; i<checkbox2.length; i++){
           if(disno != i+1){
               $(checkbox2[i]).hide();
           }
       }
          if(disno===answer){
               var iscorrect = 1;
           }else{
               var iscorrect = 0;
            }
      
           $.ajax({
              url:"${pageContext.request.contextPath}/question2/insert",
              type:"post",
              data:{test_no:quesno, correct:disno, iscorrect:iscorrect},
          
            })
        }else{
           		for(var i=0; i<checkbox2.length; i++){
          
            	   $(checkbox2[i]).show();
           
     	 	 	}
           		
           	 	$.ajax({
                 	url:"${pageContext.request.contextPath}/question2/delete",
                	 type:"post",
                	 data:{test_no:quesno},
            	   })
           		
       }  
   
       
   
   }
   </script>
  
   
   
   <!-- 필요 정보: rno,  category no, tno-->
   
<h1>문제풀기에 성공하였습니다.</h1>




<form action = "${pageContext.request.contextPath}/question/questcategory" >


   	<input type = "hidden" name = "session" value = "${category_no}">
   	<input type="hidden" name="method" value="${method}">
  	 		<input type = "hidden" name = "categoryname" value = "${csname}">
  	 
   	<input type = "submit" value = "다음 문제로">
 	
 	   
</form>