<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>论文列表</title>
<!-- Bootstrap -->
<link
	href="${pageContext.request.contextPath }/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath }/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet" media="screen">
<link
	href="${pageContext.request.contextPath }/vendors/easypiechart/jquery.easy-pie-chart.css"
	rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath }/assets/styles.css"
	rel="stylesheet" media="screen">
<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
            <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<script
	src="${pageContext.request.contextPath }/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>

<script
	src="${pageContext.request.contextPath }/vendors/jquery-1.9.1.js"></script>
<script
	src="${pageContext.request.contextPath }/bootstrap/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath }/vendors/datatables/js/jquery.dataTables.min.js"></script>


<script src="${pageContext.request.contextPath }/assets/scripts.js"></script>
<script src="${pageContext.request.contextPath }/assets/DT_bootstrap.js"></script>

</head>
<body>
	<div class="navbar navbar-fixed-top">
		<jsp:include page="head.jsp"></jsp:include>
	</div>
	<div class="container-fluid">
		<div class="row-fluid">
			<jsp:include page="sidebar.jsp"></jsp:include>

			<div class="span9" id="content">
				<!-- block -->
				
			<c:forEach var="paperInfo" items="${pageDTO.contentList }">
		<div class="row-fluid">
                 <div class="block">
                       <div class="navbar navbar-inner block-header">
                            <div class="panel-heading">
                              <a href="${pageContext.request.contextPath }/paperController/${paperInfo.paperId  }/paperInfoShow.action">${paperInfo.article}</a>
                            </div>
                       </div>
                        <div class="block-content collapse in">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#abstract${paperInfo.paperId  }" data-toggle="tab" aria-expanded="true">摘要</a>
                                </li>
                                <li class=""><a href="#info${paperInfo.paperId  }" data-toggle="tab" aria-expanded="false">更多</a>
                                </li>                               
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="abstract${paperInfo.paperId  }">
                        
                                    <p>${paperInfo.paperAbstract }</p>
                                    
                                </div>
                                <div class="tab-pane fade" id="info${paperInfo.paperId  }">
                                    <h5>作者</h5>
                                    <p>${paperInfo.authors }</p>
                                    <p>来源：${pageDTO.contentFlag }</p>
                                    <c:if test="${paperInfo.pdfFile != null }">
                                        <p>-<a href="${pageContext.request.contextPath }/paperController/${paperInfo.pdfFile }/download.action">Download PDF</a></p>
                                    </c:if>
                                    <c:if test="${paperInfo.suppPDFFile != null }">
                                        <p>-<a href="${pageContext.request.contextPath }/paperController/${paperInfo.suppPDFFile }/download.action">Supplementary PDF</a></p>
                                    </c:if>
                                    
                                </div>
                                
                            </div>
                        </div>
                    </div>
                </div>
              
             
				</c:forEach>
				
				
			 <!--  
				<c:forEach var="paperInfo" items="${pageDTO.contentList }">
					<div class="row-fluid">
						<div class="block">
							<div class="navbar navbar-inner block-header">
								<div class="muted pull-left">
									<a
										href="${pageContext.request.contextPath }/paperController/paperInfoShow.action">${paperInfo.article}</a>
								</div>
							</div>
							<div class="block-content collapse in">
								<div class="span12">
									<div class="paper paperAbstract">
						
										摘要 <br>${paperInfo.paperAbstract }
									</div>
									<div class="paper authors">
										
										作者:${paperInfo.authors }
									</div>
									<div class="paper website">
									
										来源: ${paperInfo.website }
									</div>
								</div>
							</div>
						</div>
					</div>
				</c:forEach>
      -->
				<!-- /block -->
				
				<div class="pagination pagination-centered">
					<ul>
						<li><a
							href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=1">首页</a></li>

						<c:if test="${pageDTO.currentPage>1 }">
							<li><a
								href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=${pageDTO.currentPage-1}">上一页</a></li>
						</c:if>
						<c:if test="${pageDTO.currentPage<=1 }">
							<li class='disabled'><a href='#'>上一页</a></li>
						</c:if>
						<li class='disabled'><a>第${pageDTO.currentPage }页</a></li>
						<li class='disabled'><a>共${pageDTO.totalPage }页</a></li>
						<c:if test="${pageDTO.currentPage<pageDTO.totalPage }">
							<li><a
								href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=${pageDTO.currentPage+1}">下一页</a></li>
						</c:if>
						<c:if test="${pageDTO.currentPage==pageDTO.totalPage }">
							<li class='disabled'><a href='#'>下一页</a></li>
						</c:if>

						<li><a
							href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=${pageDTO.totalPage}">尾页</a></li>


					</ul>
				</div>
			</div>


		</div>
	</div>
</body>
</html>