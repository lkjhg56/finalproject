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
              data:{test_no:quesno, correct:disno, iscorrect:iscorrect, answer:answer},
          	error:function(){
          		alert("시험 시간이 종료됐습니다. 답을 입력해도 기록되지 않습니다")
          	}
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
   
   $(document).keydown(function (e) {
	     
	     if (e.which === 116) {
	    	 alert("새로고침을 할 수 없습니다.");
	         if (typeof event == "object") {
	             event.keyCode = 0;
	        }
	       		  return false;
	    } else if (e.which === 82 && e.ctrlKey) {
	    	  return false;
	  	 }
	}); 
	        history.pushState(null, null, location.href);
	        window.onpopstate = function () {
	            history.go(1);
	    };
	        
   
   </script>
   
   <script>
        $(document).ready(function(){
        	  
        	 
            var time = $(".time").text();
            var interval = setInterval(function(){
                var count = time.split(':');
                var hour = count[0];
                var min = count[1];
                var sec = count[2];
                var counter = $(".timer").find("span");
                counter.eq(0).text(hour);
                counter.eq(1).text(min);
                counter.eq(2).text(sec);
                if (sec==0){
                    if (min !=0){
                        counter.eq(1).text(min--);
                        sec = 60;
                    }
                
                    else if (min==0 && hour!=0){
                        counter.eq(0).text(hour--);
                        counter.eq(1).text(min=59);
                        sec = 60;
                    }
                    else if (hour ==0 && min ==0 && sec==0){
                        counter.eq(2).text(0);
                    }
                  
                }
                counter.eq(0).text(hour);
                counter.eq(1).text(min);
                counter.eq(2).text(--sec);
                time  = counter.eq(0).text()+":"+counter.eq(1).text()+":"+counter.eq(2).text();
                if (hour ==0 && min ==0 && sec ==0){
                    clearInterval(interval);
                    document.getElementsByClassName(".checkbox").disabled = true
                    alert("시험이 종료 되었습니다. 제출 버튼을 눌러주세요 누르지 않을시 0점으로 기록 됩니다")
                  
                }
            },1000);
           
        })
        
 $(document).keydown(function (e) {
     
     if (e.which === 116) {
         if (typeof event == "object") {
             event.keyCode = 0;
        }
       		  return false;
    } else if (e.which === 82 && e.ctrlKey) {
    	  return false;
  	 }
}); 
        history.pushState(null, null, location.href);
        window.onpopstate = function () {
            history.go(1);
    };
        
    </script>
   
   
   
   
   <style>
  
    .timer-wrap .time{
        display: none;
    } 
 

</style>
  
  <div class="timer-wrap">

        <i class = "time">${hour}:${min}:0</i>
        <div class = "timer">
            <span>0</span> : <span>0</span> : <span>0</span>
        </div>
  </div>   
  
  
   <c:forEach var="qlist" items ="${clist}">


   <div>
   	

   		<h2>${qlist.question}</h2>

 
   		<h4><input class = "ans" type="checkbox"  name="${qlist.no}" id="${qlist.no}1" onclick="save('${qlist.no}', '1', '${qlist.answer}');">${qlist.dis1}</h4>
   		<h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}2" onclick="save('${qlist.no}', '2', '${qlist.answer}');">${qlist.dis2}</h4>
   		<h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}3" onclick="save('${qlist.no}', '3', '${qlist.answer}');">${qlist.dis3}</h4>
   		<h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}4" onclick="save('${qlist.no}', '4', '${qlist.answer}');">${qlist.dis4}</h4>
   		<h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}5" onclick="save('${qlist.no}', '5', '${qlist.answer}');">${qlist.dis5}</h4>

   </div>
   </c:forEach>
  
   <form action = "${pageContext.request.contextPath}/question/result"> 
   
   			<input type = "hidden" name = "category_no" value = "${session}">
  	 		<input type = "hidden" name = "csname" value = "${csname}">
  	 
   	<input type = "submit" value = "제출">
   </form>