<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>论文详情</title>
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
                                    <span >${paper.article}<div class="collection" style="width: 15px;height: 15px"></div></span>

                                </div>
                            </div>

                        </fieldset>
                        <fieldset>
                            <legend>Authors</legend>

                            <div class="control-group">

                                <div class="controls">
                                    <span >${paper.authors}</span>
                                </div>
                            </div>

                        </fieldset>

                            <fieldset>
                                <legend>Abstract</legend>
                                <div class="control-group">
                                    <div class="controls">
                                        <span >${paper.paperAbstract}</span>
                                    </div>
                                </div>

                            </fieldset>

                        <fieldset>
                            <legend>评论</legend>
                            <div class="control-group">
                                <div class="controls">
                                    <div class="data_list comment_list">
                                        <div class="dataHeader">用户评论</div>
                                        <div class="commentDatas">
                                            <c:forEach var="comment" items="${commentList }">
                                                <div class="comment">
                                                    <font>${comment.userName }：</font>${comment.content }&nbsp;&nbsp;&nbsp;[&nbsp;<fmt:formatDate value="${comment.addAt }" type="long" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;]
                                                </div>
                                            </c:forEach>
                                        </div>
                                    </div>

                                    <div class="publish_list">
                                        <form action="${pageContext.request.contextPath}/comment/add" method="post" onsubmit="return checkComment()">
                                            <div>
                                                <input type="hidden" value="${paper.paperId }" id="paperId" name="paperId"/>
                                                <c:if test="${!empty user }"><input type="hidden" value="${user.uid }" id="userId" name="userId"/></c:if>

                                                <textarea style="width: 98%" rows="3" id="content" name="content"></textarea>
                                            </div>
                                            <div class="publishButton">
                                                <button class="btn btn-primary" type="submit">发表评论</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                        </fieldset>


                    </div>
                </div>
            </div>
        </div>


    </div>
</div>
</body>

<script type="text/javascript">




    $(document).ready(function(){

        var collection=$(".collection");




        if("${isCollected}"=='true'){
            collection.css({"background-image":'url(${pageContext.request.contextPath}/images/YiShouCang.png)'});
            collection.onOff = true;

        }else{
            collection.css({"background-image":'url(${pageContext.request.contextPath}/images/WeiShouCang.png)'});
            collection.onOff = false;

        }

        collection.click(function(){
            var paperId = "${paper.paperId}";

            if (collection.onOff) {

                $.post("${pageContext.request.contextPath}/paper-collection/delete?",{paperId:paperId},
                    function(result){

                        if(result=="success"){
                            collection.css({"background-image":'url(${pageContext.request.contextPath}/images/WeiShouCang.png)'});
                            collection.onOff = false;
                        }else{
                            alert(result);
                        }
                    }
                );



            } else {



                $.post("${pageContext.request.contextPath}/paper-collection/add?",{paperId:paperId},
                    function(result){

                        if(result=="success"){
                            collection.css({"background-image":'url(${pageContext.request.contextPath}/images/YiShouCang.png)'});
                            collection.onOff = true;
                        }else{
                            alert(result);
                        }
                    }
                );


            }
        });





    });




</script>

</html>