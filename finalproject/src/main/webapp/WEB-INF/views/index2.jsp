<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style>

.button_zone{
	position : absolute;
	left :31%;
	top :450px;
	z-index:10;
	}
.onmain{
 	width:15%;
 	height: 150px;
 	border-radius:20%;
 	cursor:pointer;
}
	</style>
	
	<script>
	function redirectPage(url){

	window.location.href = url;
	}
	</script>
	
<jsp:include page="/WEB-INF/views/template/mheader.jsp"></jsp:include>
<article>

  <div class = "container button_zone">
  	<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
  	<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
  	<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">
  	<img src="${pageContext.request.contextPath}/res/image/grey.jpg" class = "onmain" onclick="redirectPage('${pageContext.request.contextPath}/board/list')">

  </div>

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
        <div class="post-preview">
          <a href="post.html">
            <h2 class="post-title">
             Lecture3
            </h2>
          </a>
          <p class="post-meta">
            <a href="#">Start Lecture3</a>
            </p>
        </div>
        <hr>
        <div class="post-preview">
          <a href="post.html">
            <h2 class="post-title">
             Lecture4
            </h2>
          </a>
          <p class="post-meta">
            <a href="#">Start Lecture4</a>
            </p>
        </div>
        <hr>
        
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


