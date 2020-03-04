<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	</style>
	
	<script>
	function redirectPage(url){

	window.location.href = url;
	}
	</script>
	
<jsp:include page="/WEB-INF/views/template/mheader.jsp"></jsp:include>
</section>




	
	<div class = "button_zone">
			<div class="left_aside">
			</div>
			<div class = "mainlink1">
				<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
			</div>
				<div class = "mainlink2">
				<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
			</div>
				<div class = "mainlink3">
				<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
			</div>
				<div class = "mainlink4">
				<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
			</div>
				<div class = "right_aside">
			</div>
		
		</div>

<section>
<article>

  <!-- Main Content -->
  <div class="container">
    <div class="row">
      <div class="col-lg-8 col-md-10 mx-auto">
        <div class="post-preview">
          <a href="post.html">
            <h2 class="post-title">
             Lecture1
            </h2>
            <h3 class="post-subtitle">
              Questions will be solve in a min
            </h3>
          </a>
          <p class="post-meta">
            <a href="#">Start Lecture1</a>
            </p>
        </div>
        <hr>
        <div class="post-preview">
          <a href="post.html">
            <h2 class="post-title">
             Lecture2
            </h2>
          </a>
          <p class="post-meta">
            <a href="#">Start Lecture2</a>
            </p>
        </div>
    
        
        <hr>
        <!-- Pager -->
        <div class="clearfix">
          <a class="btn btn-primary float-right" href="#">Older Posts &rarr;</a>
        </div>
      </div>
    </div>
  </div>

  <hr>

  <!-- Footer -->
  
<h1>id : ${id}</h1>



</article>
<jsp:include page="/WEB-INF/views/template/mfooter.jsp"></jsp:include>