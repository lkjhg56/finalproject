<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

   <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script> 
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
   
   <script>
   
   
   
   function save(quesno, disno, answer,id){
       var test = quesno+disno

       var checked= document.getElementById(test);
       var checkbox2= document.getElementsByName(quesno);
       var checked3= document.getElementById(id);

       if (checked.checked==true){

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
    $(function () {
    	  $(".test").hide()

    	
    });
    
    
   function file(abc) {

    	var checking = document.getElementsByName(abc);

  	$(checking).show();
		
    	
	}
   
   $(function(){

	 var a = $(".session_ques").text()

	   $.ajax({
		  url : "${pageContext.request.contextPath}/question2/queup",
		  type : "get",
		  data : {
			  session_ques:a,
		  },success: function(){

		  },erorr :  function(){
	
			  
		  }
		   
	   })
	   
   })

   $(window).on('beforeunload', function(){
	   var isNoSaved = true; // 각 사이트마다 저장하지 않고 나가는 상황을 조건검
	   if(isNoSaved)
	     return '저장하지 않은게 있어..'; // 나갈꺼냐 말꺼냐는 브라우저가 붙여줌.
	 });
   
   
</script>
   
   
   
   
   <style>
  
    .timer-wrap .time{
        display: none;
    } 
 
textarea {
  resize: none;
   overflow: auto;
   width:300px;
   height:200px;
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
    width:40%;
}
.plural-pic{
	 float:left;
   width:25%;
   
}
.plural-textarea{
	float:left;
	width: 35%;
}

.questionin_wrap::after{
		 content:"";
    display:block;
    clear:both;
}
.questionin_box{
	 float:left;
   width:8%;
    margin-top: 10px;
   
}
.questionin_dis{
float:left;
   width:92%;
}

</style>


<main>
    <header>
        <div class = "conatiner-fluid">
            <div class = "jumbotron">
                <h1>${csname}</h1>

                <h3 class = "session_ques">${session}</h3>
 						<div class="timer-wrap">

       							 <i class = "time">${hour}:${min}:0</i>
      							  <div class = "timer">
           						 <span>0</span> : <span>0</span> : <span>0</span>
        				</div>
  					</div>   
            </div>

        </div>
    </header>

 
  
<section >
   
  
 
  		<div class = "container">
   			<c:forEach var="qlist" items ="${clist}"> 

 				<div class = "plural-wrap">
					<div class = "plural-textarea">
							
								<textarea  rows="10">
								
								</textarea>
					</div>
	   				<div class = "plural-question">
	   	
	
	   							<h2>${qlist.question}</h2><h6>${qlist.rate}%</h6>
	
	 								<div class = "questionin_wrap">
			   							<div class = "questionin_box"><h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}1" onclick="save('${qlist.no}', '1', '${qlist.answer}','preview');"></h4></div><div class = "questionin_dis"><h4>${qlist.dis1}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}2" onclick="save('${qlist.no}', '2', '${qlist.answer}','preview');"></h4></div><div class = "questionin_dis"><h4>${qlist.dis2}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}3" onclick="save('${qlist.no}', '3', '${qlist.answer}','preview');"></h4></div><div class = "questionin_dis"><h4>${qlist.dis3}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}4" onclick="save('${qlist.no}', '4', '${qlist.answer}','preview');"></h4></div><div class = "questionin_dis"><h4>${qlist.dis4}</h4></div>
			   							<div class = "questionin_box"><h4><input class = "ans" type="checkbox" name="${qlist.no}" id="${qlist.no}5" onclick="save('${qlist.no}', '5', '${qlist.answer}','preview');"></h4></div><div class = "questionin_dis"><h4>${qlist.dis5}</h4></div>
									</div>
									<br>
	   								<br>
	   								<br>
	   					</div>
	   					 <div class = "plural-pic" >
	   									<img id="preview" class = "test ${qlist.no}"  name = "${qlist.no}+pic"  src="qimage?no=${qlist.no} " onload="file('${qlist.no}+pic');"  width="400" height="250" >
	   					</div> 							  
      		</div>
  		</c:forEach>
   	</div>  		

  
  
   <form action = "${pageContext.request.contextPath}/question/result"> 
   
   			<input type = "hidden" name = "category_no" value = "${session}">
  	 		<input type = "hidden" name = "csname" value = "${csname}">
  	 
   	<input type = "submit" value = "제출">
   </form>
 

</section>
   </main>
<!--     <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>