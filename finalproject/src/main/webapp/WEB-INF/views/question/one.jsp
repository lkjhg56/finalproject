<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <h1>한문제씩풀기</h1>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   <script>
   function save(quesno, disno, answer){
	
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
              data:{test_no:quesno, correct:disno, iscorrect:iscorrect,answer:answer},
          
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
  
   
   

   <div>
   	

   		<h2>${clist.question}</h2>
  	${clist.no}
   		<h4><input type="checkbox" name="${clist.no}" id="${clist.no}1" onclick="save('${clist.no}', '1', '${clist.answer}');">${clist.dis1}</h4>
   		<h4><input type="checkbox" name="${clist.no}" id="${clist.no}2" onclick="save('${clist.no}', '2', '${clist.answer}');">${clist.dis2}</h4>
   		<h4><input type="checkbox" name="${clist.no}" id="${clist.no}3" onclick="save('${clist.no}', '3', '${clist.answer}');">${clist.dis3}</h4>
   		<h4><input type="checkbox" name="${clist.no}" id="${clist.no}4" onclick="save('${clist.no}', '4', '${clist.answer}');">${clist.dis4}</h4>
   		<h4><input type="checkbox" name="${clist.no}" id="${clist.no}5" onclick="save('${clist.no}', '5', '${clist.answer}');">${clist.dis5}</h4>

   </div>
 
   
   <form action = "${pageContext.request.contextPath}/question/oneresult" > 
   	<input type = "hidden" name = "category_no" value = "${session}">
   	<input type="hidden" name="method" value="${method}">
  	 		<input type = "hidden" name = "csname" value = "${csname}">
  	 
   	<input type = "submit" value = "제출">
   	
   </form>