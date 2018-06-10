<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="timestamp" class="java.util.Date"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>笔记详情</title>
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
        <div class="span6" id="content">

            <div class="block">

                <div class="navbar navbar-inner block-header">
                    <div class="muted pull-left"></div>
                </div>
                <div class="block-content collapse in">
                    <div class="span12">

                        <fieldset>
                            <legend>Article</legend>

                            <div class="control-group">

                                <div class="controls">
                                    <span>${paper.article}
                                        <div class="collection" style="width: 15px;height: 15px"></div></span>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Authors</legend>

                            <div class="control-group">

                                <div class="controls">
                                    <span>${paper.authors}</span>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <legend>Abstract</legend>
                            <div class="control-group">
                                <div class="controls">
                                    <span>${paper.paperAbstract}</span>
                                </div>
                            </div>
                        </fieldset>
                        <fieldset>
                            <div class="block-content collapse in">
                                <ul class="nav nav-tabs">
                                    <li class=""><a href="#comment" data-toggle="tab"
                                                          aria-expanded="true">评论</a>
                                    </li>
                                    <li class="active"><a href="#note" data-toggle="tab"
                                                    aria-expanded="false">笔记</a>
                                    </li>
                                </ul>
                                <div class="tab-content">
                                    <div class="tab-pane fade " id="comment">
                                        <div class="control-group">
                                            <div class="controls">
                                                <div class="data_list comment_list">
                                                    <div class="dataHeader">用户评论</div>
                                                    <div class="commentDatas">
                                                        <c:forEach var="comment" items="${commentList }">
                                                            <div class="comment">

                                                                <jsp:setProperty name="timestamp" property="time"
                                                                                 value="${comment.addAt }"/>
                                                                <fmt:formatDate value="${timestamp}"
                                                                                pattern="yyyy-MM-dd hh:mm:ss"/>
                                                                &nbsp;&nbsp;&nbsp;&nbsp; ${comment.name }：
                                                                    ${comment.content }
                                                            </div>
                                                        </c:forEach>
                                                    </div>
                                                </div>
                                                <div class="publish_list">
                                                    <form action="${pageContext.request.contextPath}/comment/add"
                                                          method="post" onsubmit="return checkComment()">
                                                        <div>
                                                            <input type="hidden" value="${paper.paperId }"
                                                                   name="paperId"/>
                                                            <c:if test="${!empty user }"><input type="hidden"
                                                                                                value="${user.uid }"
                                                                                                name="userId"/></c:if>

                                                            <textarea style="width: 98%" rows="3" id="content"
                                                                      name="content"></textarea>
                                                        </div>
                                                        <div class="publishButton">
                                                            <button class="btn btn-primary" type="submit">发表评论</button>
                                                        </div>
                                                    </form>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="tab-pane fade active in" id="note">


                                        <fieldset>
                                            <form action="${pageContext.request.contextPath}/note/update" method="post" enctype="multipart/form-data">
                                                <table cellpadding="5" width="100%">
                                                    <div class="control-group">
                                                        <label class="control-label">标题 </label>
                                                        <div class="controls">
                                                            <input type="text" class="span3" name="article" value="${noteShowing.article}">

                                                        </div>
                                                    </div>

                                                    <div class="control-group">
                                                        <label class="control-label">内容 </label>
                                                        <div class="controls">
                                                            <textarea class="input-xlarge textarea" name="content" style="width: 810px; height: 200px"> ${noteShowing.content}</textarea>
                                                        </div>
                                                    </div>
                                                    <c:if test="${!empty user }">

                                                        <input type="hidden" name="paperId" value="${paper.paperId }"/>


                                                        <input type="hidden" name="userId" value="${user.uid}"/>

                                                    </c:if>
                                                    <div class="form-actions">
                                                        <input type="submit" class="btn btn-primary" value="更新" />&nbsp;&nbsp;
                                                        <input type="button" class="btn btn-primary" value="返回"
                                                               onclick="javascript:history.back()" />&nbsp;&nbsp;<font
                                                            id="error" color="red">${error }</font>
                                                    </div>
                                                </table>
                                            </form>
                                        </fieldset>

                                    </div>
                                </div>
                            </div>
                        </fieldset>
                    </div>
                </div>
            </div>
        </div>

        <div class="span3">
            <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
                <li style="text-align:center">笔记</li>
                <c:forEach var="note" items="${noteList }">
                    <li style="text-align:center">
                        <a href="#" style="align-content: center"><p>${fn:substring(note.article,0,40) }...</a>
                    </li>
                </c:forEach>

            </ul>

        </div>
    </div>
</div>
</body>


<link href="${pageContext.request.contextPath }/vendors/datepicker.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath }/vendors/uniform.default.css" rel="stylesheet" media="screen">
<link href="${pageContext.request.contextPath }/vendors/chosen.min.css" rel="stylesheet" media="screen">

<link href="${pageContext.request.contextPath }/vendors/wysiwyg/bootstrap-wysihtml5.css" rel="stylesheet" media="screen">

<script src="${pageContext.request.contextPath }/vendors/jquery-1.9.1.js"></script>
<script src="${pageContext.request.contextPath }/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath }/vendors/jquery.uniform.min.js"></script>
<script src="${pageContext.request.contextPath }/vendors/chosen.jquery.min.js"></script>
<script src="${pageContext.request.contextPath }/vendors/bootstrap-datepicker.js"></script>

<script src="${pageContext.request.contextPath }/vendors/wysiwyg/wysihtml5-0.3.0.js"></script>
<script src="${pageContext.request.contextPath }/vendors/wysiwyg/bootstrap-wysihtml5.js"></script>

<script src="${pageContext.request.contextPath }/vendors/wizard/jquery.bootstrap.wizard.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath }/vendors/jquery-validation/dist/jquery.validate.min.js"></script>
<script src="${pageContext.request.contextPath }/assets/form-validation.js"></script>

<script src="${pageContext.request.contextPath }/assets/scripts.js"></script>

<script>

    jQuery(document).ready(function() {
        FormValidation.init();
    });


    $(function() {
        $(".datepicker").datepicker();
        $(".uniform_on").uniform();
        $(".chzn-select").chosen();
        $('.textarea').wysihtml5();

        $('#rootwizard').bootstrapWizard({onTabShow: function(tab, navigation, index) {
                var $total = navigation.find('li').length;
                var $current = index+1;
                var $percent = ($current/$total) * 100;
                $('#rootwizard').find('.bar').css({width:$percent+'%'});
                // If it's the last tab then hide the last button and show the finish instead
                if($current >= $total) {
                    $('#rootwizard').find('.pager .next').hide();
                    $('#rootwizard').find('.pager .finish').show();
                    $('#rootwizard').find('.pager .finish').removeClass('disabled');
                } else {
                    $('#rootwizard').find('.pager .next').show();
                    $('#rootwizard').find('.pager .finish').hide();
                }
            }});
        $('#rootwizard .finish').click(function() {
            alert('Finished!, Starting over!');
            $('#rootwizard').find("a[href*='tab1']").trigger('click');
        });
    });
</script>

<script type="text/javascript">


    $(document).ready(function () {

        var collection = $(".collection");


        if ("${isCollected}" == 'true') {
            collection.css({"background-image": 'url(${pageContext.request.contextPath}/images/YiShouCang.png)'});
            collection.onOff = true;

        } else {
            collection.css({"background-image": 'url(${pageContext.request.contextPath}/images/WeiShouCang.png)'});
            collection.onOff = false;

        }

        collection.click(function () {
            var paperId = "${paper.paperId}";

            if (collection.onOff) {

                $.post("${pageContext.request.contextPath}/paper-collection/delete?", {paperId: paperId},
                    function (result) {

                        if (result == "success") {
                            collection.css({"background-image": 'url(${pageContext.request.contextPath}/images/WeiShouCang.png)'});
                            collection.onOff = false;
                        } else {
                            alert(result);
                        }
                    }
                );


            } else {


                $.post("${pageContext.request.contextPath}/paper-collection/add?", {paperId: paperId},
                    function (result) {

                        if (result == "success") {
                            collection.css({"background-image": 'url(${pageContext.request.contextPath}/images/YiShouCang.png)'});
                            collection.onOff = true;
                        } else {
                            alert(result);
                        }
                    }
                );


            }
        });


    });


</script>


</html>