<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    
    <meta http-equiv="pragma" content="no-cache">  
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="expires" content="0">     
    
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>复习计划</title>
	<%
    	pageContext.setAttribute("STATIC_PATH", "/studysns-static");
    %>
    <!-- Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link href="${STATIC_PATH}/css/font-awesome.min.css" rel="stylesheet">
    <link href="${STATIC_PATH}/font/fontawesome-webfont.woff" rel="stylesheet">

    <!-- HTML5 shim 和 Respond.js 是为了让 IE8 支持 HTML5 元素和媒体查询（media queries）功能 -->
    <!-- 警告：通过 file:// 协议（就是直接将 html 页面拖拽到浏览器中）访问页面时 Respond.js 不起作用 -->
    <!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <script>
    	var STATIC_PATH = '${STATIC_PATH}';
    	var CTX_PATH = '/studysns';
    </script>
    <style>
    	#main-box{
    		display: flex;
    		justify-content: center;
    	}
    	#main-box>div{
   		    flex: 1;
		    max-width: 1200px;
    	}
    	.noresolve-knowledge, .resolve-knowledge, .flow-knowledge{
    		position: relative;
    	}
    	.sns-abs{
   		    cursor: pointer;
		    top: 0px;
		    right: 15px;
		    position: absolute;
		    display: none;
    	}
    	.sns-red{
    		color: red;
    	}
    	.sns-rel{
    		position: relative;
    	}
    	.h3, h3 {
		    color: rgba(255, 0, 0, 0.69);
		    font-weight: 700;
		    font-size: 24px;
		}
		
		.sns-model{
			background: white;
			position: absolute;
			top: 0px;
			left: 0px;
			width: 100%;
			height: 100%;
			display: none;
			z-index: 9999999;
		}
    	
    </style>
  </head>
  <body>
    
    <div id='main-box'>
		<div class='sns-rel'>
			<div class='sns-model' id='notes-details'>
				<h1>笔记复习计划</h1>
				<div class="panel panel-default">
				  <div class="panel-body">
					     
				  </div>
				</div>
			</div>
		</div>
		
    </div>
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${STATIC_PATH}/js/lib/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
    <script src='${STATIC_PATH}/js/common/sns-common.js?'></script>
    <script src='${STATIC_PATH}/js/review-plan.js'></script>
  </body>
</html>