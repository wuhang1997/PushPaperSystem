<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>论文列表</title>
    <link
            href="${pageContext.request.contextPath }/style/news.css"
            rel="stylesheet" media="screen">
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
            <div class="row-fluid">
                <form action="${pageContext.request.contextPath }/paperController/search-keywords" method="post">
                    <tr>
                        <td>
                            <span style="padding:4px 6px;margin-bottom: 10px;height:29px;">搜索:</span>

                            <input class="input" id="search" name="search" type="text" value="${pageDTO.search}">

                            <button type="submit" class="btn " style="height:29px;padding:4px 6px;margin-bottom: 10px;"
                                    id="button">
                                <i class="icon-search"></i></button>

                        </td>

                    </tr>
                </form>
                <%--   <div class="controls">
                       <input type="text" class="span6" id="typeahead" data-provide="typeahead" data-items="4"
                              data-source="[&quot;Alabama&quot;,&quot;Alaska&quot;,&quot;Arizona&quot;,&quot;Arkansas&quot;,&quot;California&quot;,&quot;Colorado&quot;,&quot;Connecticut&quot;,&quot;Delaware&quot;,&quot;Florida&quot;,&quot;Georgia&quot;,&quot;Hawaii&quot;,&quot;Idaho&quot;,&quot;Illinois&quot;,&quot;Indiana&quot;,&quot;Iowa&quot;,&quot;Kansas&quot;,&quot;Kentucky&quot;,&quot;Louisiana&quot;,&quot;Maine&quot;,&quot;Maryland&quot;,&quot;Massachusetts&quot;,&quot;Michigan&quot;,&quot;Minnesota&quot;,&quot;Mississippi&quot;,&quot;Missouri&quot;,&quot;Montana&quot;,&quot;Nebraska&quot;,&quot;Nevada&quot;,&quot;New Hampshire&quot;,&quot;New Jersey&quot;,&quot;New Mexico&quot;,&quot;New York&quot;,&quot;North Dakota&quot;,&quot;North Carolina&quot;,&quot;Ohio&quot;,&quot;Oklahoma&quot;,&quot;Oregon&quot;,&quot;Pennsylvania&quot;,&quot;Rhode Island&quot;,&quot;South Carolina&quot;,&quot;South Dakota&quot;,&quot;Tennessee&quot;,&quot;Texas&quot;,&quot;Utah&quot;,&quot;Vermont&quot;,&quot;Virginia&quot;,&quot;Washington&quot;,&quot;West Virginia&quot;,&quot;Wisconsin&quot;,&quot;Wyoming&quot;]">


                   </div>--%>
            </div>
            <c:forEach var="paperInfo" items="${pageDTO.contentList }">
                <div class="row-fluid">
                    <div class="block">
                        <div class="navbar navbar-inner block-header">
                            <div class="panel-heading">
                                <a href="${pageContext.request.contextPath }/paperController/showPaperInfo?id=${paperInfo.paperId}">${paperInfo.article}</a>
                            </div>
                        </div>
                        <div class="block-content collapse in">
                            <ul class="nav nav-tabs">
                                <li class="active"><a href="#abstract${paperInfo.paperId  }" data-toggle="tab"
                                                      aria-expanded="true">摘要</a>
                                </li>
                                <li class=""><a href="#info${paperInfo.paperId  }" data-toggle="tab"
                                                aria-expanded="false">更多</a>
                                </li>
                            </ul>

                            <div class="tab-content">
                                <div class="tab-pane fade active in" id="abstract${paperInfo.paperId  }">

                                    <p>${paperInfo.paperAbstract }</p>

                                </div>
                                <div class="tab-pane fade" id="info${paperInfo.paperId  }">
                                    <h5>作者</h5>
                                    <p>${paperInfo.authors }</p>
                                    <p>来源：${paperInfo.website }</p>
                                    <c:if test="${paperInfo.pdfFile != null }">
                                        <p>
                                            -<a href="${pageContext.request.contextPath }/paperController/${paperInfo.pdfFile }/download.action">Download
                                            PDF</a></p>
                                    </c:if>
                                    <c:if test="${paperInfo.suppPDFFile != null }">
                                        <p>
                                            -<a href="${pageContext.request.contextPath }/paperController/${paperInfo.suppPDFFile }/download.action">Supplementary
                                            PDF</a></p>
                                    </c:if>

                                </div>

                            </div>
                        </div>
                    </div>
                </div>


            </c:forEach>


            <div class="span3">


                <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
                    <li>热点论文</li>
                    <c:forEach var="hotPaper" items="${top10Paper }">
                        <li>
                            <a href="${pageContext.request.contextPath}/paperController/showPaperInfo?id=${hotPaper.paperId}"><p>${fn:substring(hotPaper.article,0,40) }...</a>
                        </li>
                    </c:forEach>

                </ul>


            </div>


            <div class="pagination pagination-centered">
                <ul>
                    <li><a
                            href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=1">首页</a>
                    </li>

                    <c:if test="${pageDTO.currentPage>1 }">
                        <li><a
                                href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=${pageDTO.currentPage-1}">上一页</a>
                        </li>
                    </c:if>
                    <c:if test="${pageDTO.currentPage<=1 }">
                        <li class='disabled'><a href='#'>上一页</a></li>
                    </c:if>
                    <li class='disabled'><a>第${pageDTO.currentPage }页</a></li>
                    <li class='disabled'><a>共${pageDTO.totalPage }页</a></li>
                    <c:if test="${pageDTO.currentPage<pageDTO.totalPage }">
                        <li><a
                                href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=${pageDTO.currentPage+1}">下一页</a>
                        </li>
                    </c:if>
                    <c:if test="${pageDTO.currentPage==pageDTO.totalPage }">
                        <li class='disabled'><a href='#'>下一页</a></li>
                    </c:if>

                    <li><a
                            href="${pageContext.request.contextPath }/paperController/${pageDTO.contentFlag }/paperPage.action?currentPage=${pageDTO.totalPage}">尾页</a>
                    </li>


                </ul>
            </div>
        </div>


    </div>
</div>
</body>
</html>