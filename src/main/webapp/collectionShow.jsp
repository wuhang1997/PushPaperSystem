<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>论文收藏</title>
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
        <div class="span9" id="content" style="padding-top: 100px">

            <div class="data_content">
                <table class="table table-hover table-bordered">
                    <tr>
                        <!-- <th><input type="checkbox" id="checkedAll" /></th> -->

                        <th>论文标题</th>
                        <th>作者</th>
                        <th>详情</th>
                        <th>操作</th>
                    </tr>
                    <c:forEach var="collection" items="${collectionPage.contentList }"
                               varStatus="status">
                        <tr>
                                <%-- <td><input type="checkbox" name="ids"
                                    value="${collectionBack.id}" /></td> --%>

                            <td>${collection.article }</td>
                                    <td>
                                            ${collection.authors}
                                    </td>
                            <td>
                                <a href="${pageContext.request.contextPath }/paperController/showPaperInfo?id=${collection.paperId}">点我查看</a>
                            </td>
                                <%-- <td>${collectionBack.title }</td> --%>
                                <%-- <td>${collectionBack.newsType.typeName }</td> --%>
                                <%-- <td><fmt:formatDate value="${newsBack.publishDate }"
                                        type="date" pattern="yyyy-MM-dd" /></td>
                                <td><button class="btn btn-mini btn-info" type="button"
                                        onclick="javascript:window.location.href='${pageContext.request.contextPath }/newsController/goUpdateNewsUI.action?newsId=${newsBack.newsId}'">修改</button>&nbsp; --%>
                            <td>
                                <button class="btn btn-mini btn-danger" type="button"
                                        onclick="collectionDelete(${collection.paperId})">删除
                                </button>

                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>

            <div class="pagination pagination-centered">
                <ul>
                    <li><a
                            href="${pageContext.request.contextPath }/paper-collection/show?currentPage=1">首页</a>
                    </li>

                    <c:if test="${collectionPage.currentPage>1 }">
                        <li><a
                                href="${pageContext.request.contextPath }/paper-collection/show?currentPage=${collectionPage.currentPage-1}">上一页</a>
                        </li>
                    </c:if>
                    <c:if test="${collectionPage.currentPage<=1 }">
                        <li class='disabled'><a href='#'>上一页</a></li>
                    </c:if>
                    <li class='disabled'><a>第${collectionPage.currentPage }页</a></li>
                    <li class='disabled'><a>共${collectionPage.totalPage }页</a></li>
                    <c:if test="${collectionPage.currentPage<collectionPage.totalPage }">
                        <li><a
                                href="${pageContext.request.contextPath }/paper-collection/show?currentPage=${collectionPage.currentPage+1}">下一页</a>
                        </li>
                    </c:if>
                    <c:if test="${collectionPage.currentPage==collectionPage.totalPage }">
                        <li class='disabled'><a href='#'>下一页</a></li>
                    </c:if>

                    <li><a
                            href="${pageContext.request.contextPath }/paper-collection/show?currentPage=${collectionPage.totalPage}">尾页</a>
                    </li>


                </ul>
            </div>


        </div>
    </div>
</div>
</body>

<script type="text/javascript">
    function collectionDelete(paperId) {
        if (confirm("确认要删除这条收藏吗？")) {
            $.post("${pageContext.request.contextPath}/paper-collection/delete", {paperId: paperId},
                function (result) {


                    if (result == "success") {
                        alert("删除成功!");
                        window.location.href = "${pageContext.request.contextPath}/paper-collection/show?currentPage=1";
                    } else {
                        alert("删除失败");
                    }
                }
            );
        }
    }


</script>

</html>