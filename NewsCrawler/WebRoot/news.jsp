<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'news.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<style type="text/css">
div {
	margin: 0 auto;
}

#news {
	width: 640px;
	border-style: solid;
	border-color: rgb(102, 102, 102);
	border-width: 1px;
}

h1 {
	font-family: 宋体;
	font-size: 20;
}

#introduce {
	font-family: 宋体;
	font-size: 10;
	color: rgb(102, 102, 102);
	border-bottom-style: solid;
	border-color: rgb(102, 102, 102);
	border-width: 1px;
}

#content {
	margin: 25px;
	font-family: 宋体;
	font-size: 14;
	line-height: 24px;
}

#url {
	font-family: 宋体;
	font-size: 10;
	color: rgb(102, 102, 102);
	margin: 25px;
}

.img_wrapper {
	width: 100%;
	text-align: center;
}

.img_descr {
	display: none;
}
</style>
<script type="text/javascript">
	
</script>
</head>

<body>
	<div align="center">
		<div id="news" align="center">
			<h1 id="title" align="center">${requestScope.news.title}</h1>
			<div id="introduce" align="center">
				<span>发布时间:${requestScope.news.newsdate}</span><span
					style="margin-left: 50px">新闻类型：${requestScope.news.type}</span>
			</div>
			<div id="content" align="left">${requestScope.news.content}</div>
			<div id="url" align="right">
				原文链接：<a href="${requestScope.news.url}" target="_blank">${requestScope.news.url}</a>
			</div>
		</div>
	</div>
</body>
</html>
