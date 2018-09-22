<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
	<%@include file="./common.jsp"%>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>如何做笔记是个大学问</title>
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
				<h1>记笔记是个大学问</h1>
				<div class="panel panel-default">
				  <div class="panel-body">
					    <div id='notes-form'>
						  <div class="form-group">
						    <label>应用场景</label>
						    <input name='applicationScene' class="form-control" id="exampleInputEmail1" placeholder="应用场景">
						  </div>
						  <div class="form-group">
						    <label>高度总结</label>
						    <textarea class="form-control" id="exampleInputEmail1" placeholder="高度总结" name="abstractSummary"> </textarea>
						  </div>
						  
						  <div>
						    <label>知识点是否已经全部闭合</label>
						  </div>					  
						  <div class="form-group">
						    <label class="radio-inline">
							  <input type="radio" name="knowledgeClosed" value="0" checked=checked>未闭合
							</label>
							<label class="radio-inline">
							  <input type="radio" name="knowledgeClosed" value="1">已闭合
							</label>
						  </div>
						</div>
				  </div>
				</div>
				
				
				<h3>都解决了哪些知识点，整理一下</h3>
				<yes-knowledge></yes-knowledge>
				
		  		<button class="btn btn-default icon-plus-sign-alt" sns-click="addKnowledge">&nbsp&nbsp添加知识点</button>
				
				
				<h3>还有哪些知识点盲区，记录一下</h3>
				<no-knowledge></no-knowledge>
				
				<button class="btn btn-default icon-plus-sign-alt sns-red" sns-click="addBlindKnowledge">&nbsp&nbsp添加盲区知识点</button>
				
				
				<h3>流程性知识点</h3>
				<flow-knowledge></flow-knowledge>
				
				<button class="btn btn-default icon-plus-sign-alt" sns-click="addFLowKnowledge" style='margin-bottom:25px;'>&nbsp&nbsp添加流程知识点</button>
				
			</div>
			
		    <div style='margin-top:25px;'>
			    <div class="form-group">
				    <label>笔记标题</label>
				    <input name='notesTitle' class="form-control" placeholder="笔记标题">
			    </div>
		    	<label>笔记内容</label>
				<div id='nodes-box'></div>
		    </div>
			
				
			<div style='position:fixed;right:0px;top:50%; z-index: 9999999;'>
				<button class="btn btn-default icon-tags" sns-click='taggleDetails'>&nbsp&nbsp摘要切换</button>
				<div>
					<button sns-click='submitNotes' class='btn btn-default icon-save'>&nbsp&nbsp保存笔记</button>
				</div>
			</div>
			
		</div>
		
    </div>
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="${STATIC_PATH}/js/lib/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    
    <!-- 加载ueditor编辑器  -->
    <link href="${STATIC_PATH}/js/lib/ueditor/themes/default/css/ueditor.css" rel="stylesheet">
    <script src="${STATIC_PATH}/js/lib/ueditor/ueditor.config.js"></script>
    <script src="${STATIC_PATH}/js/lib/ueditor/ueditor.ins.js"></script>
    <script src="${STATIC_PATH}/js/lib/ueditor/lang/zh-cn/zh-cn.js"></script>
    <script src='${STATIC_PATH}/js/common/sns-common.js?'></script>
    <script src='${STATIC_PATH}/js/main.js'></script>
  </body>
</html>