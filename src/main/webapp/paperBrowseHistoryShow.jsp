<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="timestamp" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>浏览记录</title>
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

            <div class="data_content">
                <table class="table table-hover table-bordered">
                    <tr>
                        <!-- <th><input type="checkbox" id="checkedAll" /></th> -->

                        <th>论文标题</th>
                        <!-- <th>新闻类别</th> -->
                        <th>作者</th>
                        <th>来源</th>
                        <th>论文详情</th>
                        <th>浏览时间</th>
                    </tr>
                    <c:forEach var="history" items="${historyPage.contentList }"
                               varStatus="status">
                        <tr>
                                <%-- <td><input type="checkbox" name="ids"
                                    value="${collectionBack.id}" /></td> --%>

                            <td>${history.article }</td>

                            <td>
                                ${history.authors}
                            </td>

                            <td>
                                ${history.website}
                            </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath }/paperController/showPaperInfo?id=${history.paperId}">点我查看</a>
                                    </td>
                            <td>

                                <jsp:setProperty name="timestamp" property="time" value="${history.addAt}"/>
                                <fmt:formatDate value="${timestamp}" pattern="yyyy-MM-dd hh:mm:ss" />
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="pagination pagination-centered">
                <ul>
                    <li><a
                            href="${pageContext.request.contextPath }/paper-browse-history/show?currentPage=1">首页</a>
                    </li>

                    <c:if test="${historyPage.currentPage>1 }">
                        <li><a
                                href="${pageContext.request.contextPath }/paper-browse-history/show?currentPage=${historyPage.currentPage-1}">上一页</a>
                        </li>
                    </c:if>
                    <c:if test="${historyPage.currentPage<=1 }">
                        <li class='disabled'><a href='#'>上一页</a></li>
                    </c:if>
                    <li class='disabled'><a>第${historyPage.currentPage }页</a></li>
                    <li class='disabled'><a>共${historyPage.totalPage }页</a></li>
                    <c:if test="${historyPage.currentPage<historyPage.totalPage }">
                        <li><a
                                href="${pageContext.request.contextPath }/paper-browse-history/show?currentPage=${historyPage.currentPage+1}">下一页</a>
                        </li>
                    </c:if>
                    <c:if test="${historyPage.currentPage==historyPage.totalPage }">
                        <li class='disabled'><a href='#'>下一页</a></li>
                    </c:if>

                    <li><a
                            href="${pageContext.request.contextPath }/paper-browse-history/show?currentPage=${historyPage.totalPage}">尾页</a>
                    </li>


                </ul>
            </div>


        </div>
    </div>
</div>
</body>
</html>