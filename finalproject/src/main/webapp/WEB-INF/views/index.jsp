<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.2.0/css/bootstrap.min.css">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/style.css?version=20200122">
<link href="https://fonts.googleapis.com/css?family=Noto+Sans+KR:400,700|Open+Sans:400,400i,700,700i|Source+Code+Pro&amp;subset=korean" rel="stylesheet">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/css/result.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/custom.css?version=20200122">
<link rel="stylesheet" href="https://ddo7jzca0m2vt.cloudfront.net/unify/css/theme-colors/blue.css?version=20200122">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
<jsp:include page="/WEB-INF/views/template/mheader.jsp"></jsp:include>
<style>
.button_zone::after{
	 content:"";
    display:block;
    clear:both;
    flex-wrap:wrap-reverse;
}
.button_zone{
	position : absolute;
	top :450px;
	z-index:10;
	}
.onmain{
 	width:100%;
 	height: 100%;
 	border-radius:10%;
 	cursor:pointer;
}

.left_aside{
	 float:left;
    width:36%;
    height:150px;

}
.right_aside{
	 float:left;
    width:36%;
    height:150px;

}
.mainlink1{
	 float:left;
    width:7%;
    height:150px;
}
.mainlink2{
	 float:left;
    width:7%;
    height:150px;
}
.mainlink3{
	 float:left;
    width:7%;
    height:150px;
}
.mainlink4{
	 float:left;
    width:7%;
    height:150px;
}

@media (min-width: 768px){
	.col-md-3 {
	    -ms-flex: 0 0 25%;
	    flex: 0 0 25%;
	    max-width: 25%;
	}
}

@media (max-width: 992px){
	.md-margin-bottom-40 {
	    margin-bottom: 40px;
	}
  }
@media (min-width: 768px){
	.col-sm-6 {
	    width: 50%;
	}
}

.flex-wrap{
          display: flex;
          flex-direction: row;           /* 줄 안에 배치 */
      }
      @media screen and (max-width:640px){
          .flex-wrap{
           flex-direction: column;   /* 칸 안에 배치 */    
           }
      }     
      
      .item1{
      	  background-color:whitesmoke;
          flex-grow: 1;                   /* 줄에서 남은 공간을 차지하는 비율 */
          height: 200px;                 /* 1개만 높이가 있어도 나머지가 같은 높이로 설정된다. */
      }
      .item2{
          flex-grow: 1;
      }
       .item3{
       background-color:yellow;
          flex-grow: 1;                
                      
      }

.init{
	padding-top : 7px;
	font-family:'CookieRunOTF-Bold';
}



#slider {
            width: 90%;
            height: 400px;
            margin: auto;
            position: relative;
        }

        #slider .slides-container {
            position: relative;
            width: 100%;
            height: 100%;
        }

        #slider .slide {
            width: 100%;
            height: 100%;
            position: absolute;
            left: 0;
            top: 0;
            display: none;
        }

        #slider .slide .text {
            width: 100%;
            height: 100%;
            color: black;
            text-transform: uppercase;
            font-size: 17px;
            font-weight: bold;
        }

        #slider .slide:first-child {
            display: block;
        }
   .memo{
   	font-size: 14px;
   	color: #777;
   }
   
   .memo-title{
   	color: #0076C0;
   	font-size: 17px;
   }
   .Btitle{
   		font-size: 17px;
   }
   .Qtitle{
   		font-size: 17px;
   }

</style>
	
<script>
function redirectPage(url){
window.location.href = url;
}


function islogin(id, path){
	
	if(id.length<1){
			$(".logined").hide();
			$(".nolgoin").show();
	}else{
			$(".nologin").hide();
			$(".logined").show();

	}
	
	$.ajax({
		url : "${pageContext.request.contextPath}/main/getRank",
		type : "post",
		success:function(resp){
			var rank = 1;
			htmls = "";
			htmls += '<div class = "text">'
			htmls += '<div class ="init">포인트 랭킹</div>'
			$(resp).each(function(){
				
				htmls += '<div class ="init">'+rank+'.'+this.name+'</div>'
				rank += 1;
				
			})
			htmls += '</div>'
			$(".mRank").html(htmls);
			
				$.ajax({
					url : "${pageContext.request.contextPath}/main/newQList",
					type : "post",
					success:function(resp){
						var rank = 1;
						htmls = "";
						htmls += '<div class = "text">';
						$(resp).each(function(){
							
							htmls += '<div class ="init">'+rank+'.'+this.question_title+'</div>'
							rank += 1;
							
							
							
						})
						htmls += '</div>'
					$(".mNewList").html(htmls);
					}
					
				})
				
		}
	})
	
	$.ajax({
		url : "${pageContext.request.contextPath}/main/gList",
		type : "post",
		success:function(resp){
			var rank = 1;
			htmls = "";
			htmls += '<div class = "text">';
			htmls += '<div class ="init">많이 풀린 문제</div>';
			$(resp).each(function(){
				
				htmls += '<div class ="init">'+rank+'.'+this+'</div>'
				rank += 1;
				
			})
			htmls += '</div>'
		$(".mFQlist").html(htmls);
		}
	})



	
};






 function dxSimpleSlider(element = '#dx-slider', auto = false, pause) {

    // Get parent element
    var $this = $(element);

    // Slides container
    var slidesCont = $this.children('.slides-container');
    // Get all slides
    var slides = slidesCont.children('.slide');



    // Get Previous / Next links
    var arrowsCont = $this.children('.arrows');
    var prevSlide = arrowsCont.children('.prev');
    var nextSlide = arrowsCont.children('.next');

    // Total slides count
    var slidesCount = slides.length;

    // Set currentSlide to first child
    var currentSlide = slides.first();
    var currentSlideIndex = 1;

    var autoPlay = null;

    // Hide all slides except first and add active class to first
    slides.not(':first').css('display', 'none');
    currentSlide.addClass('active');

    // Function responsible for fading to next slide
    function fadeNext() {
        currentSlide.removeClass('active').fadeOut(700);

        if(currentSlideIndex == slidesCount) {
            currentSlide = slides.first();
            currentSlide.delay(500).addClass('active').fadeIn(700);
            currentSlideIndex = 1;
        } else {
            currentSlideIndex++;
            currentSlide = currentSlide.next();
            currentSlide.delay(500).addClass('active').fadeIn(700);
        }


    }

    // Function responsible for fading to previous slide
    function fadePrev() {
        currentSlide.removeClass('active').fadeOut(700);

        if(currentSlideIndex == 1) {
            currentSlide = slides.last();
            currentSlide.delay(500).addClass('active').fadeIn();
            currentSlideIndex = slidesCount;
        } else {
            currentSlideIndex--;
            currentSlide = currentSlide.prev();
            currentSlide.delay(500).addClass('active').fadeIn(700);
        }

  
    }

    // Function that starts the autoplay and resets it in case user navigated (clicked prev or next)
    function AutoPlay() {
        clearInterval(autoPlay);

        if(auto == true)
            autoPlay = setInterval(function() {fadeNext()}, pause);
    }

    // Detect if user clicked on arrow for next slide and fade next slide if it did
    $(nextSlide).click(function(e) {
        e.preventDefault();
        fadeNext();
        AutoPlay();
    });

    // Detect if user clicked on arrow for previous slide and fade previous slide if it did
    $(prevSlide).click(function(e) {
        e.preventDefault();
        fadePrev();
        AutoPlay();
    });

    // Start autoplay if auto is set to true
    AutoPlay();

}

$(function() {
    dxSimpleSlider('#slider', true, 8000);
});

</script>


<!-- Main Content -->
<body class=" pace-done">
<div class="container content">
<div class="col-md-4 col-sm-6 md-margin-bottom-40">
	<div class="flex-wrap blog-twitter">
		<div class="headline"><h2><a>QMaster 랭킹</a></h2></div>
		
		<c:forEach var="grade_point_rank" items="${grade_point_rank}">		
			<div class="blog-twitter-inner" style="width: 100%">
               <span class="memo-title">${grade_point_rank.rank}등</span>
               <span class="article-subject Qtitle">${grade_point_rank.name}</span>
               <span class="memo">&nbsp; ${grade_point_rank.grade_point}point</span>
            </div>	
         </c:forEach>	     		
	</div>
</div>	

<div class="col-md-4 col-sm-6 md-margin-bottom-40">
	<div class="flex-wrap blog-twitter">
		<div class="headline"><h2><a href="${pageContext.request.contextPath}/question/list">추가된 문제</a></h2></div>
				
		<c:forEach var="newQ" items="${newQ}">				
			<div class="blog-twitter-inner" style="width: 100%">
              <span class="memo-title">${newQ.question_no}번</span>
               <a href="${pageContext.request.contextPath}/question/solve?question_no=${newQ.question_no}" class="article-subject Qtitle">${newQ.question_title }</a>
               <span class="twitter-time">??분 전
               <span></span><i class="far fa-eye"></i> ${newQ.read_count}
               </span>
            </div>	
         </c:forEach>		
	</div>
</div>	

<div class="col-md-4 col-sm-6 md-margin-bottom-40">
	<div class="flex-wrap blog-twitter">
		<div class="headline"><h2><a href="${pageContext.request.contextPath}/board/list">새로운 글</a></h2></div>
			
			<c:forEach var="recentC" items="${recentC}">					
			<div class="blog-twitter-inner Btitle" style="width: 100%">
               <div><a href="${pageContext.request.contextPath}/board/content?board_no=${recentC.board_no}" class="article-subject">[${recentC.board_category}] ${recentC.board_title}</a></div> 
               <span class="twitter-time">${recentC.board_wdate.substring(0,16) }&nbsp; 
               <span class="glyphicon glyphicon-comment"></span>&nbsp;${recentC.board_replycount}&nbsp; <i class="far fa-eye"></i>&nbsp;${recentC.board_readcount }
               </span>
            </div>	
           </c:forEach> 		
	</div>
</div>	
</div>
	

    
        
<hr>

</body>
  <!-- Footer -->
<jsp:include page="/WEB-INF/views/template/mfooter.jsp"></jsp:include>