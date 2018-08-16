<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../img/favicon.ico">
    <title>Keeding Blog</title>
    <link href="../dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="../css/blog.css" rel="stylesheet">
    <style type="text/css">
    	.content{
    		/* 单行省略号 */
    		/* overflow: hidden;
    		text-overflow:ellipsis;
    		white-space: nowrap; */
    		display: -webkit-box;
			-webkit-box-orient: vertical;
			-webkit-line-clamp: 7;
			overflow: hidden;
    	}
    </style>
    <script type="text/javascript">
    	window.onload = function(){
    		$.ajax({
                url: 'http://localhost:8080/KeedingBlogs_Maven_New/notes/getNotesList.action',
                dataType: 'json',
                method: 'post',
                success: function(data) {
                	var headline = $(".headline");
                	var content = $(".content");
                	var detailLink = $(".detailLink>a");
//                 	var d = document.getElementsByClassName("btn btn-default detail");
                	$.each(content,function(i){
						$(headline[i]).text(data[i].mark);
						$(content[i]).text(data[i].content);
						$(detailLink[i]).prop("href", "http://www.baidu"+i+".com");
                	})
                },
                error: function(xhr) {
                    // 导致出错的原因较多，以后再研究
                    alert('error:' + JSON.stringify(xhr));
                }
            });
    	}
    </script>
  </head>
  <body>
  	<!--留言、在线IT书	标签、分类、时间、热门、统计、
		我的兴趣（音乐、艺术、书）、软件推荐、网站推荐、分享、评论、-->
    <div class="blog-masthead">
      <div class="container">
        <nav class="blog-nav">
          <a class="blog-nav-item active" href="#">Home</a>
          <a class="blog-nav-item" href="#">IT Book</a>
          <a class="blog-nav-item" href="#">留言</a>
          
        </nav>
      </div>
    </div>
    
    <div class="container">
		<div class="blog-header">
		  <h5 class="blog-title">最近博客</h5>
		  <p class="lead blog-description">The official example template of creating a blog with Bootstrap.</p>
		</div>

		<div class="row">
		<div class="col-sm-8 blog-main">
			<div class="row">
				<div class="col-xs-12 col-sm-6 ">
	              <h2 class="headline"></h2>
	              <p class="content"></p>
	              <p class="detailLink"><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
	            </div>
				<div class="col-xs-12 col-sm-6 ">
	              <h2 class="headline"></h2>
	              <p class="content"></p>
	              <p class="detailLink"><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
	            </div>
				<div class="col-xs-12 col-sm-6 ">
	              <h2 class="headline"></h2>
	              <p class="content"></p>
	              <p class="detailLink"><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
	            </div>
				<div class="col-xs-12 col-sm-6 ">
	              <h2 class="headline"></h2>
	              <p class="content"></p>
	              <p class="detailLink"><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
	            </div>
				<div class="col-xs-12 col-sm-6 ">
	              <h2 class="headline"></h2>
	              <p class="content"></p>
	              <p class="detailLink"><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
	            </div>
				<div class="col-xs-12 col-sm-6 ">
	              <h2 class="headline"></h2>
	              <p class="content"></p>
	              <p class="detailLink"><a class="btn btn-default" href="#" role="button">View details &raquo;</a></p>
	            </div>
			</div>
        </div>

        <div class="col-sm-3 col-sm-offset-1 blog-sidebar">
          <div class="sidebar-module sidebar-module-inset">
            <h4>About</h4>
            <p>Etiam porta <em>sem malesuada magna</em> mollis euismod. Cras mattis consectetur purus sit amet fermentum. Aenean lacinia bibendum nulla sed consectetur.</p>
          </div>
          <div class="sidebar-module sidebar-module-inset">
          	<aside class="widget clearfix">
						  <form id="searchform" action="http://www.youmeek.com">
						    <div class="input-group">
						      <input type="search" class="form-control" placeholder="搜索…" value="" name="s">
						      <span class="input-group-btn">
							      <button class="btn btn-default" type="submit">
							      	<i class="glyphicon glyphicon-search" aria-hidden="true"></i>
							      </button>
						      </span> </div>
						  </form>
						</aside>
          </div>
          <div class="sidebar-module">
            <h4>标签</h4>
            <ol class="list-unstyled">
              <li><a href="#">GitHub</a></li>
              <li><a href="#">Twitter</a></li>
              <li><a href="#">Facebook</a></li>
            </ol>
          </div>
          <div class="sidebar-module">
            <h4>时间分类</h4>
            <ol class="list-unstyled">
              <li><a href="#">March 2014</a></li>
              <li><a href="#">February 2014</a></li>
              <li><a href="#">January 2014</a></li>
              <li><a href="#">December 2013</a></li>
              <li><a href="#">November 2013</a></li>
              <li><a href="#">October 2013</a></li>
              <li><a href="#">September 2013</a></li>
              <li><a href="#">August 2013</a></li>
              <li><a href="#">July 2013</a></li>
              <li><a href="#">June 2013</a></li>
              <li><a href="#">May 2013</a></li>
              <li><a href="#">April 2013</a></li>
            </ol>
          </div>
        </div><!-- /.blog-sidebar -->

      </div><!-- /.row -->

    </div><!-- /.container -->

    <footer class="blog-footer">
      <p>Blog template built for <a href="http://getbootstrap.com">Bootstrap</a> by <a href="https://twitter.com/mdo">@mdo</a>.</p>
      <p>
        <a href="#">Back to top</a>
      </p>
    </footer>

    <script src="../js/jquery-1.11.1.js"></script>
    <script src="../dist/js/bootstrap.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
