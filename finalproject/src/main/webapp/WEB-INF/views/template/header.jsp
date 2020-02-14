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

function islogin(id){
	
	if(id.length<1){
			console.log("asd");
			$(".logined").hide();
			$(".nolgoin").show();
	}else{
			$(".nologin").hide();
			$(".logined").show();

	}
}
	
</script>



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
<body onload="islogin('${id}')">
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
            <a class="nav-link" href="${pageContext.request.contextPath}/question/list">User Quiz</a>
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
            <a class="nav-link" href="${pageContext.request.contextPath}/users/info">Memberinfo</a>
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
  <header class="normheader" style="background-image: url('img/contact-bg.jpg')">
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