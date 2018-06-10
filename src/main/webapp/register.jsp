<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>注册 - PPS</title>

</head>
<body>
<div id="box"></div>
<div class="cent-box register-box">
    <div class="cent-box-header">
        <h1 class="main-title hide">知乎</h1>
        <h2 class="sub-title">生活热爱分享 - Thousands Find</h2>
    </div>

    <div class="cont-main clearfix">
        <div class="index-tab">
            <div class="index-slide-nav">
                <a href="${pageContext.request.contextPath }/login.jsp">登录</a> <a
                    href="${pageContext.request.contextPath }/register.jsp" class="active">注册</a>
                <div class="slide-bar slide-bar1"></div>
            </div>
        </div>

        <div class="login form">
            <form
                    action="${pageContext.request.contextPath }/userController/register.action"
                    method="post" onsubmit="return check()">
                <div class="group">
                    <div class="group-ipt email">
                        <input type="email" name="email" id="email" class="ipt"
                               placeholder="邮箱地址" required>
                    </div>
                    <div class="group-ipt user">
                        <input type="text" name="name" id="name" class="ipt"
                               placeholder="用戶名" required>
                    </div>
                    <div class="group-ipt user">
                        <input type="text" name="preferences" id="preferences" class="ipt" placeholder="研究方向" required>

                    </div>
                    <div class="group-ipt password">
                        <input type="password" name="password" id="password" class="ipt"
                               placeholder="设置登录密码" required>
                    </div>
                    <div class="group-ipt password">
                        <input type="password" name="password1" id="password1"
                               class="ipt" placeholder="重复密码" required>
                    </div>
                    <div class="button">
                        <button type="submit" class="login-btn register-btn" id="button">注册</button>
                    </div>
                </div>
            </form>


        </div>
    </div>


</div>
</div>

<div class="footer">
    <p>知乎 - Thousands Find</p>
    <p>
        Designed By ZengRong & <a href="zrong.me">aspku.com</a> 2016
    </p>
</div>

</body>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath }/style/register-login.css">
<script src='${pageContext.request.contextPath }/js/particles.js'
        type="text/javascript"></script>

<script src='${pageContext.request.contextPath }/js/jquery.min.js'
        type="text/javascript"></script>
<script src='${pageContext.request.contextPath }/js/layer/layer.js'
        type="text/javascript"></script>

<script src='${pageContext.request.contextPath }/js/background.js'
        type="text/javascript"></script>

<script type="text/javascript">


    function check() {
        var password = $("#password").val();
        var password1 = $("#password1").val();
        if (password1 != password) {
            alert("两次输入密码不相同");
            return false;
        }

        $.post("${pageContext.request.contextPath}/userController/checkEmail", {email: $("#email").val()}, function (result) {

            if (result == "success") {
                alert("邮箱已被使用");
                return false;
            }
        });



        return true;
    }
</script>
</html>