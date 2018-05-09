<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录</title>


</head>
<body>
<div id="box"></div>
<div class="cent-box">
    <div class="cent-box-header">
        <h1 class="main-title hide" style="margin-left:10%;width:80%">PushPaper</h1>
        <h2 class="sub-title">生活热爱分享 - Thousands Find</h2>
    </div>

    <div class="cont-main clearfix">
        <div class="index-tab">
            <div class="index-slide-nav">
                <a href="${pageContext.request.contextPath }/login.jsp" class="active">登录</a> <a
                    href="${pageContext.request.contextPath }/register.jsp">注册</a>
                <div class="slide-bar"></div>
            </div>
        </div>

        <div class="login form">
            <form
                    action="${pageContext.request.contextPath }/userController/login.action"
                    method="post">
                <div class="group">
                    <div class="group-ipt email">
                        <input type="text" name="email" id="email" class="ipt"
                               placeholder="邮箱地址" required>
                    </div>
                    <div class="group-ipt password">
                        <input type="password" name="password" id="password" class="ipt"
                               placeholder="输入您的登录密码" required>
                    </div>

                    <div class="button">
                        <button type="submit" class="login-btn register-btn" id="button">登录</button>

                    </div>
                </div>
            </form>

        </div>


    </div>
</div>

<div class="footer">
    <p>PushPaper - Thousands Find</p>
    <p>
        Designed By WangJie 2018
    </p>
</div>


</body>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/style/register-login.css">
<script src='${pageContext.request.contextPath }/js/particles.js' type="text/javascript"></script>

<script src='${pageContext.request.contextPath }/js/jquery.min.js' type="text/javascript"></script>
<script src='${pageContext.request.contextPath }/js/layer/layer.js' type="text/javascript"></script>

<script src='${pageContext.request.contextPath }/js/background.js' type="text/javascript"></script>
</html>