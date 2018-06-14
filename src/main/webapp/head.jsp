<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

            <div class="navbar-inner">
                <div class="container-fluid">
                    <a class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse"> <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                     <span class="icon-bar"></span>
                    </a>
                    <a class="brand" href="${pageContext.request.contextPath }/main.jsp">PaperPush</a>
                    <c:if test="${ empty user }">
                        <div class="nav-collapse collapse">
                            <ul class="nav pull-right">
                                <li class="dropdown">

                                    <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i><i class="caret"></i>

                                    </a>
                                    <ul class="dropdown-menu" id="dropdown">
                                        <li>
                                            <a tabindex="-1" href="${pageContext.request.contextPath }/login.jsp">登录</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a tabindex="-1" href="${pageContext.request.contextPath }/register.jsp">注册</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>

                        </div>
                    </c:if>
                    <c:if test="${!empty user }">
                        <div class="nav-collapse collapse">
                            <ul class="nav pull-right">
                                <li class="dropdown">

                                    <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> ${user.name} <i class="caret"></i>

                                    </a>
                                    <ul class="dropdown-menu">
                                        <li>
                                            <a tabindex="-1" href="${pageContext.request.contextPath }/userCenter.jsp">用户中心</a>
                                        </li>
                                        <li class="divider"></li>
                                        <li>
                                            <a tabindex="-1" href="${pageContext.request.contextPath }/userController/logout">退出</a>
                                        </li>
                                    </ul>
                                </li>
                            </ul>

                        </div>
                    </c:if>
                  <%-- <div class="nav-collapse collapse">
                        <ul class="nav pull-right">
                            <li class="dropdown">

                                <a href="#" role="button" class="dropdown-toggle" data-toggle="dropdown"> <i class="icon-user"></i> ${user.name} <i class="caret"></i>

                                </a>
                                <ul class="dropdown-menu">
                                    <li>
                                        <a tabindex="-1" href="${pageContext.request.contextPath }/userCenter.jsp">用户中心</a>
                                    </li>
                                    <li class="divider"></li>
                                    <li>
                                        <a tabindex="-1" href="${pageContext.request.contextPath }/userController/logout">注销</a>
                                    </li>
                                </ul>
                            </li>
                        </ul>

                    </div>--%>
                    <!--/.nav-collapse -->

                </div>
            </div>

</body>
<script type="text/javascript">
    /* 鼠标点击特效 */
   /* var a_idx = 0;
    jQuery(document).ready(function($) {
        $("body").click(function(e) {
            var a = new Array("富强", "民主", "文明", "和谐", "自由", "平等", "公正" ,"法治", "爱国", "敬业", "诚信", "友善");
            var $i = $("<span/>").text(a[a_idx]);
            a_idx = (a_idx + 1) % a.length;
            var x = e.pageX,
                y = e.pageY;
            $i.css({
                "z-index": 999999999999999999999999999999999999999999999999999999999999999999999,
                "top": y - 20,
                "left": x,
                "position": "absolute",
                "font-weight": "bold",
                "color": "#80ffc5"
            });
            $("body").append($i);
            $i.animate({
                    "top": y - 180,
                    "opacity": 0
                },
                1500,
                function() {
                    $i.remove();
                });
        });
    });*/
</script>
</html>