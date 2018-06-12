<%--<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>

</head>
<body>--%>
<div class="span3">


    <ul class="nav nav-list bs-docs-sidenav nav-collapse collapse">
        <li style="text-align:center">热点论文</li>
        <c:forEach var="hotPaper" items="${top10Paper }">
            <li style="text-align:center">
                <a href="${pageContext.request.contextPath}/paperController/showPaperInfo?id=${hotPaper.paperId}" style="align-content: center"><p ><strong>${fn:substring(hotPaper.article,0,40) }...</strong></a>
            </li>
        </c:forEach>

    </ul>


</div>



</body>
<script type="text/javascript">
    var dom = document.getElementById("container");
    var myChart = echarts.init(dom);
    var app = {};
    option = null;
    myChart.showLoading();

    $.getJSON('${pageContext.request.contextPath }/main', function (json) {
        myChart.hideLoading();
        myChart.setOption(option = {
            title: {
                text: '论文标题词分布'
            },
            animationDurationUpdate: 1500,
            animationEasingUpdate: 'quinticInOut',
            series : [
                {
                    type: 'graph',
                    layout: 'none',
                    // progressiveThreshold: 700,
                    data: json.nodes.map(function (node) {
                        return {
                            x: node.x,
                            y: node.y,
                            id: node.id,
                            name: node.label,
                            symbolSize: node.size,
                            itemStyle: {
                                normal: {
                                    color: '#' + Math.floor(Math.random() * 16777215).toString(16)
                                }
                            }
                        };
                    }),
                    edges: json.edges.map(function (edge) {
                        return {
                            source: edge.sourceID,
                            target: edge.targetID
                        };
                    }),
                    label: {

                        normal: {
                            position: 'right',
                            show: true
                        }
                    },
                    roam: true,
                    focusNodeAdjacency: true,
                    lineStyle: {
                        normal: {

                            opacity: 0
                        }
                    }
                }
            ]
        }, true);
    });
    if (option && typeof option === "object") {
        myChart.setOption(option, true);
    }
</script>

</html>