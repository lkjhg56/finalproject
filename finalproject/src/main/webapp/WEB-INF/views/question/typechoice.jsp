<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

  <%@taglib	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
  <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  
  <script>
  
   function callCategory(csname){
	  var option = document.getElementById(csname)
	  
   $.ajax ({
       url:"${pageContext.request.contextPath}/question2/callcategory",
       type : "post",
       data : {csname:csname},
       datatype: "list",
       success:function(quesList){
			$(option).empty();
			$(quesList).each(function(){
				$("<option>").text(this).appendTo(option)
			})
			

       }
    });
   };
   
   

   
   function resultSave(csname, tno, userid){
	   
	   $.ajax({
		  url:"${pageContext.request.contextPath}/question2/resultin",
		  type : "post",
		  data :{csname:csname, tno:tno, id:userid}
		   
	   })
	   
   }
   var count = 1;
   </script>
  
  
   <c:forEach var = "list" items="${list}">
   
   <div>
 	<form action = "${pageContext.request.contextPath }/question/questcategory">
	<input type = "hidden" name = "hour" value = "${list.lim_hour}">
	<input type = "hidden" name = "min" value = "${list.lim_min}">
	<input type = "hidden" name ="categoryname" value= "${list.csname}" >
 	<div>${list.csname}</div>
	${list.lim_min}
 		<select class = "method" name="method" onclick="callCategory('${list.csname}')">

 			<option> 방법 선택하세요</option>
 			<option>한문제씩풀기</option>
 			<option>한번에풀기</option>
 		</select>
 	    <select name="session" id = '${list.csname}'>
        <option>회차를 선택하세요</option>
    	</select>
	<input type = "submit" value = "선택" onclick="resultSave('${list.csname}', '${tno}', '${id}')" >	
 	</form>
 
  	
 	
 	 </div>
   
   

 </c:forEach>
 