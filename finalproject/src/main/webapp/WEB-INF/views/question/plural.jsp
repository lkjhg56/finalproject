<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
   <script>
  function save(quesno, disno){
	  $.ajax({
		  	url:"${pageContext.request.contextPath}/question2/insert",
		  	type:"post",
		  	data:{test_no:quesno, correct:disno, result_no:"1"},
		   success:function(resp){
			   console.log("성공");
		   }
		  
	  })
   		
   
   
   }
   </script>
   
   <c:forEach var="qlist" items ="${clist}">
   <div>
   		<h2>${qlist.question}</h2>
   		
   		<h4><input type="checkbox" name="${qlist.no}" value="1" onclick="save('${qlist.no}', '1');">${qlist.dis1}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" value="2" onclick="save('${qlist.no}', '1');">${qlist.dis2}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" value="3" onclick="save('${qlist.no}', '1');">${qlist.dis3}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" value="4" onclick="save('${qlist.no}', '1');">${qlist.dis4}</h4>
   		<h4><input type="checkbox" name="${qlist.no}" value="5" onclick="save('${qlist.no}', '1');">${qlist.dis5}</h4>
   		
   </div>
   
   </c:forEach>