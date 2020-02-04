<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   <script>
   function save(quesno, disno, answer){
       var test = quesno+disno
		console.log(test+"확인")
       var checked= document.getElementById(test);
       var checkbox2= document.getElementsByName(quesno);


       if (checked.checked==true){
           console.log("채크됨")
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
  
   <c:forEach var="qlist" items ="${clist}">
   

   <div>
   	

   		<h2>${qlist.question}</h2>
  	${qlist.no}
   		<h4><input type="checkbox" name="${qlist.no}" id="${qlist.no}1" onclick="save('${qlist.no}', '1', '${qlist.answer}');">${qlist.dis1}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" id="${qlist.no}2" onclick="save('${qlist.no}', '2', '${qlist.answer}');">${qlist.dis2}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" id="${qlist.no}3" onclick="save('${qlist.no}', '3', '${qlist.answer}');">${qlist.dis3}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" id="${qlist.no}4" onclick="save('${qlist.no}', '4', '${qlist.answer}');">${qlist.dis4}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" id="${qlist.no}5" onclick="save('${qlist.no}', '5', '${qlist.answer}');">${qlist.dis5}</h4>

   </div>
   </c:forEach>
   
   <form action = "${pageContext.request.contextPath}/question/result"> 
   	<input type = "submit" value = "제출">
   </form>