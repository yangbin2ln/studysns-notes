<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
  	<%@include file="./common.jsp"%>
  	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="pragma" content="no-cache">  
	<meta http-equiv="cache-control" content="no-cache">  
	<meta http-equiv="expires" content="0">    
	<link href="${STATIC_PATH}/css/notesreviewplan-list/main.css" rel="stylesheet"> 
	<title>笔记复习计划</title>
  </head>

	<body>
		<div>
			<div class='main-container'>
				<c:forEach items="${data.data}" var="obj">
				<div class='top-docs-item'>
					<div>${obj.title}</div>
					<div>${obj.abstractSummary}</div>
					<div>上次复习时间 2018年9月5日</div>
					<div>已复习环节数量 3</div>
					<div>剩余复习复习环节数量 2</div>
					<div>错过复习环节数量 0</div>
					<div>已解决知识点</div>
					<div>流程性知识点</div>
				</div>
				</c:forEach>
			</div>
		</div>
	</body>
	
</html>