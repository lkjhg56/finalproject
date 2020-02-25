<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

 <h1>한문제씩풀기</h1>
   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
   <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
   <script>

   
   function save(quesno, disno, answer,id){

	//   opener.location.reload();

       var test = quesno+disno
		console.log(test+"확인")
       var checked= document.getElementById(test);
       var checkbox2= document.getElementsByName(quesno);

       var checked3= document.getElementById(id);
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
        }
       else{
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
          
      
    /*   
      $(window).bind("beforeunload", function (e){

    	  window.opener.appendResult();

    	}); */



    
	  





	  
	  


	

	    
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
	    


        $(function () {
      	  $(".test").hide()
      	console.log("hide")
      	
      });
      
      
     function file(abc) {
      	console.log("show")
      	var checking = document.getElementsByName(abc);
      	console.log(checking)
    	$(checking).show();
  		
      	
  	}

     
     
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
     

/* console.log(clist.lim_min)
console.log(${clist.no}-1); */




	 
	

function deletesession() {
	//삭제 알림창
            console.log("세션빼기");
      /*       e.preventDefault() */
      
    
      
	  $.ajax({
              url:"${pageContext.request.contextPath}/question2/deletesession",
              type:"post",
            
             success:function(){
            	
            		 location.reload();
            		 
            	}
            });
      
	
	 
}





$(function(){
	 var a = $(".session_ques").text()
	 console.log(a);
	   $.ajax({
		  url : "${pageContext.request.contextPath}/question2/queup",
		  type : "get",
		  data : {
			  session_ques:a,
		  },success: function(){
			  console.log("success");
		  },erorr :  function(){
			  console.log("fail");
			  
		  }
		   
	   })
	   
  })
 
  
  $(document).ready(function() { 
	  

	  
	   var a = $(".asd").text();
	  var checkbox2=$(".ans");
		 $.ajax({
	     	url:"${pageContext.request.contextPath}/question2/delete2",
	    	 type:"post",
	    	 data:{test_no:a},
	    	 
	    	 
	    	   success:function(resp){
	          	
	    		   
	    		   
	    		   
	    		   
	     		console.log(resp+"check");
	     		
	     		
	     		var a = 3;

	     		if(resp!=0){

	     		

	     			
	     	       for(var i=0; i<checkbox2.length; i++){

	     	    

	   	            if(i+1==resp){
	   	            	 $(checkbox2[i]).prop('checked', true);
	   	            }

	   	            else{
	   	            	
	   	                    $(checkbox2[i]).hide();
	   	            	
	   	            }
	   	            
	   	             
	   	                  
	   	                
	   	              
	   	            }
	     			
	     			
	     		}
	     
	                
	            console.log("체크");
	     		
	     		
	     	}
		   })
	  
  
  });


   </script>
  
   
      <style>
  
    .timer-wrap .time{
        display: none;
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
      
.plural-wrap::after{
	 content:"";
    display:block;
    clear:both;
}
.plural-question{
	 float:left;
    width:60%;
}
.plural-pic{
	 float:left;
    width:100%;
  
}




</style>


   <main>
   
<header>
        <div class = "conatiner-fluid">
            <div class = "jumbotron">
                <h1>${csname}</h1>

                <h3 class = "session_ques">${session}</h3>
 						<div class="timer-wrap">

       							 <i class = "time">0:${clist.lim_min}:1</i>
      							  <div class = "timer">
           						 <span>0</span> : <span>0</span> : <span>0</span>
        				</div>
  					</div>   
            </div>

        </div>
    </header>

   



<section>
   	

   		<h2>${clist.question}</h2>
  <!-- 이미지 파일 조건 -->
  	
  			
<div class = "plural-pic" ><img id="preview" src="qimage?no=${clist.no}"  class="test" name = "${clist.no}+pic"  onload="file('${clist.no}+pic'); "  width="20%" height="200"></div><br><br>
 
   	
   		<h4><input class = "ans"  type="checkbox" name="${clist.no}" id="${clist.no}1" onclick="save('${clist.no}', '1', '${clist.answer}','preview');">${clist.dis1}</h4>
   		<h4><input class = "ans"  type="checkbox" name="${clist.no}" id="${clist.no}2" onclick="save('${clist.no}', '2', '${clist.answer}','preview');">${clist.dis2}</h4>
   		<h4><input class = "ans"  type="checkbox" name="${clist.no}" id="${clist.no}3" onclick="save('${clist.no}', '3', '${clist.answer}','preview');">${clist.dis3}</h4>
   		<h4><input class = "ans"  type="checkbox" name="${clist.no}" id="${clist.no}4" onclick="save('${clist.no}', '4', '${clist.answer}','preview');">${clist.dis4}</h4>
   		<h4><input class = "ans"  type="checkbox" name="${clist.no}" id="${clist.no}5" onclick="save('${clist.no}', '5', '${clist.answer}','preview');">${clist.dis5}</h4>
  


 <div class ="asd">${clist.no}</div>

   <form action = "${pageContext.request.contextPath}/question/questcategory"  method="get"> 
   
   
   	<input type = "hidden" name = "category_no" value = "${session}">
   	   	<input type = "hidden" name = "session" value = "${session}">
   	<input type="hidden" name="method" value="${method}">
  	 		<input type = "hidden" name = "categoryname" value = "${categoryname}">
  	 		
  	 		<input type="hidden" name="hour"  value="${hour}">
  	 		<input type="hidden" name="min" value="${min}">
  	 		 		<input type="hidden" name="tno" value="${tno}">
  	 		
  	 <%-- 		<input type = "hidden" name = "categoryname" value = "${csname}"> --%>
  	 <c:if test="${no>=1}">
  	 <button type="button" onclick="deletesession()">이전으로</button>
  	 
  	 </c:if>
  	 
  	 
   	<input type = "submit" value = "제출">
   	
   </form>
   
   </section>
      </main>
         <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>