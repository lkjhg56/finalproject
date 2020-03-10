<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous">
</script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous">
</script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous">
</script>
	


<script>

function islogin(id, path){
	
	if(id.length<1){
			$(".logined").hide();
			$(".nolgoin").show();
	}else{
			$(".nologin").hide();
			$(".logined").show();

	}
	
	$.ajax({
		url : "${pageContext.request.contextPath}/main/search",
		type : "post",
		data :{id:id},
		success:function(resp){
		
			var premium = "";
			if(resp.is_premium==0){
				premium += '무료회원'
			}else{
				premium += '유료회원'
			}
			
			var htmls = "";
			if(resp.id == null){
				htmls += '<h6>로그인해 주세요</h6>'
				htmls += '<a href="'+path+'/users/login"><button type="button" class="btn btn-primary btn-block">login</button></a>'
			}else{
				htmls += '<h6>'+resp.id+'님    '+'<a href="'+path+'/users/logout">logout</a></h6>'
				htmls += '<h6> 등급 : '+resp.grade+'</h6>'
				if(premium=='무료회원'){
				htmls += '<form action = "'+path+'/pay/premium">'
				htmls += '<input type ="hidden" name = "point" value = "'+resp.point+'">'
				htmls += '<h6>토큰 :'+resp.point+'<a href ="'+path+'/pay/list"> 충전</a></h6>'
				htmls += '<input class = "btn_1 btn btn-primary" type ="submit" value = "프리미엄 전환">'
				htmls += '</form>'
				}else{
				htmls += '<h6>토큰 :'+resp.point+'<a href ="'+path+'/pay/list"> 충전</a></h6>'
				htmls += '<h6>'+premium+'</h6>'
				}
				
			}
			
			$("#member_zone").html(htmls);
		}
})


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
						htmls += '<div class ="init">최신문제</div>';
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


<style>


.for_aside{
	 content:"";
    display:block;
    clear:both;
    flex-wrap:wrap-reverse;
}

.section_area{
	 float:left;
    width:70%;
}
.aside_area{
	 float:left;
    width:30%;
    min-height:600px;
}

.member_info{
	 border-spacing: 15px;
    background-color: #AAA5A4;
    width:100%;
    border: 1px solid black;
   
}

.member_info1{
	width : 100%;
	height: 150px;
	 background-color: #edf0ee;
	  padding-left: 15px;
     padding-top: 20px;
     padding-right : 15px;
      border: 4px solid #d4d9d6;
	 
}

.aside_link{
	width:100%;
	min-height:500px;
	  border: 4px solid #f2f5f3;
	  padding-top :10px;
	  padding-left :10px;
	  font-size:16px;
}

.btn_1{
	font-size:15px;
	
}
@font-face { font-family: 'CookieRunOTF-Bold'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_twelve@1.0/CookieRunOTF-Bold00.woff') format('woff'); font-weight: normal; font-style: normal; }


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



</style>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>문제 마스터 홈페이지</title>

<link href="${pageContext.request.contextPath}/res/static/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

  <!-- Custom fonts for this template -->
  <link href="${pageContext.request.contextPath}/res/static/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href='https://fonts.googleapis.com/css?family=Lora:400,700,400italic,700italic' rel='stylesheet' type='text/css'>
  <link href='https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css'>

  <!-- Custom styles for this template -->
  <link href="${pageContext.request.contextPath}/res/static/css/clean-blog.css" rel="stylesheet">

<style>
	.logo{
		width : 100;
		height: 30
	}
	

</style>
</head>
<body onload="islogin('${id}', '${pageContext.request.contextPath}')">
<main>

<!-- 운영자 페이지 넣어야 함. -->

<!-- Navigation -->
  <nav class="navbar navbar-expand-lg navbar-light fixed-top" id="mainNav">
    <div class="container">
      <a class="navbar-brand" href="${pageContext.request.contextPath}"><img class= "logo" src="${pageContext.request.contextPath}/res/image/logo3.png"></a>
      <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
        Menu
        <i class="fas fa-bars"></i>
      </button>
      <div class="collapse navbar-collapse" id="navbarResponsive">
        <ul class="navbar-nav ml-auto">
           <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/">Home</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/board/list">Board</a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/question/choose">Quiz</a>
          </li>
          <li>
            <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/question/list">CustomQuiz</a>
          </li>


          
          <li class="nav-item nologin">
            <a class="nav-link" href="${pageContext.request.contextPath}/users/login">Login</a>
          </li>
         
            <li class="nav-item nologin">
            <a class="nav-link" href="${pageContext.request.contextPath}/users/join">Regist</a>
          </li>

           <li>
            <li class="nav-item logined">
            <a class="nav-link" href="${pageContext.request.contextPath}/users/info">
            <c:choose>
            	<c:when test="${grade=='관리자'}">
            		Admin Page
            	</c:when>
            	<c:otherwise>
            		Memberinfo	
            	</c:otherwise>
            </c:choose>
            
            </a>
          </li>
            <li>
            <li class="nav-item logined">
            <a class="nav-link" href="${pageContext.request.contextPath}/users/logout">Logout</a>
          </li>        
        </ul>
      </div>
    </div>
  </nav>

  <br>
  <br>
  <br>


<!-- 헤더  -->
  <header class="normheader">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="page-heading">
          </div>
        </div>
      </div>
    </div>

  </header>
  

 
  
  	<div class= "forfooter">

<section>