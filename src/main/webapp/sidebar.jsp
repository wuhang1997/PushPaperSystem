<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="span3" id="sidebar">
		<ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">

			<li><a href="${pageContext.request.contextPath }/main.jsp"><i class="icon-chevron-right"></i>
					首页</a></li>


			<li><a href="${pageContext.request.contextPath }/paperController/ICML/paperShow.action">
			
			 <i class="icon-chevron-right"></i>
			  <span class="badge badge-info pull-right">${ICMLNum}</span>
					ICML</a></li>

			<li><a href="${pageContext.request.contextPath }/paper-browse-history/show?currentPage=1"><i class="icon-chevron-right"></i>
					浏览历史</a></li>
			<li><a href="${pageContext.request.contextPath }/paper-collection/show?currentPage=1"><i class="icon-chevron-right"></i>
					我的收藏</a></li>
			<li><a href="${pageContext.request.contextPath }/comment/list-comment?currentPage=1"><i class="icon-chevron-right"></i>
				我的评论</a></li>
			<li><a href="${pageContext.request.contextPath }/recommend/list-recommend?currentPage=1"><i class="icon-chevron-right"></i>
				推荐历史</a></li>

		</ul>
	</div>
</body>
</html>