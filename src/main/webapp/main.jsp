<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>主界面</title>
    <!-- Bootstrap -->
    <link href="${pageContext.request.contextPath }/bootstrap/css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="${pageContext.request.contextPath }/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet"
          media="screen">
    <link href="${pageContext.request.contextPath }/vendors/easypiechart/jquery.easy-pie-chart.css" rel="stylesheet"
          media="screen">
    <link href="${pageContext.request.contextPath }/assets/styles.css" rel="stylesheet" media="screen">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <script src="${pageContext.request.contextPath }/vendors/modernizr-2.6.2-respond-1.1.0.min.js"></script>
    <!--/.fluid-container-->
    <script src="${pageContext.request.contextPath }/vendors/jquery-1.9.1.min.js"></script>
    <script src="${pageContext.request.contextPath }/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath }/vendors/easypiechart/jquery.easy-pie-chart.js"></script>
    <script src="${pageContext.request.contextPath }/assets/scripts.js"></script>
    <script src="${pageContext.request.contextPath }/echarts/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath }/echarts/echarts-wordcloud.min.js"></script>

</head>

<body>
<div class="navbar navbar-fixed-top">
    <jsp:include page="head.jsp"></jsp:include>
</div>
<div class="container-fluid">
    <div class="row-fluid">
        <jsp:include page="sidebar.jsp"></jsp:include>
        <div class="span6" id="content">

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

            </div>


            <div class="block">
                <div class="navbar navbar-inner block-header">
                    <div class="muted pull-left">论文标题词云图</div>

                </div>
                <div class="block-content collapse in">
                    <div class="span12">
                        <div id="wordCloud" style="height: 500px; position: relative;"></div>
                    </div>
                </div>
            </div>
        </div>


    </div>
</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/echarts.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/echarts/echarts-wordcloud.min.js"></script>
<script type="text/javascript">
    var keywords = {
        "Memory": 8.269477773791774,
        "Approximation": 8.718512475694316,
        "Minimization": 8.087052690875714,
        "Method": 8.539515954380471,
        "Latent": 10.940329465736832,
        "Analysis": 18.217415544337033,
        "Machine": 14.126638356848458,
        "Embeddings": 10.524202451315631,
        "Contextual": 9.367909234802202,
        "Based": 8.416649681670261,
        "Fast": 18.215739324456244,
        "Social": 8.786097889075767,
        "Functions": 11.39533065353269,
        "Distributed": 16.861936849870105,
        "Adaptive": 13.397033957724037,
        "Reinforcement": 23.856292200584925,
        "Submodular": 8.155198894429944,
        "Descent": 11.668288565822609,
        "Bayesian": 19.061152713523573,
        "Prediction": 18.22855334579301,
        "Structured": 11.189046277126911,
        "Algorithm": 11.707938823161138,
        "Scalable": 15.290189592491581,
        "Component": 8.401006834659551,
        "Multiple": 11.043451129913613,
        "Linear": 10.676092917460968,
        "Variational": 15.208642742323375,
        "Processes": 12.569600378948552,
        "Mining": 10.103398776324793,
        "Structural": 8.346191501552793,
        "Deep": 36.80159741019503,
        "Bandits": 12.85218607204498,
        "Recommendation": 8.430989000186134,
        "Graph": 11.096752923388916,
        "Tensor": 13.861486616691094,
        "Maximization": 11.235842032211107,
        "Text": 10.994754243585977,
        "Algorithms": 19.35722369157382,
        "Graphical": 8.698495018538685,
        "Stochastic": 25.102188153553712,
        "Sampling": 17.51368159509857,
        "Kernel": 15.74343111418597,
        "Discovery": 9.985055123772106,
        "Approach": 16.01620506327147,
        "Sparse": 17.897757461911713,
        "Dynamic": 12.732685822574405,
        "Optimization": 33.455950297712306,
        "Optimal": 15.477151270299496,
        "Framework": 8.042612873206778,
        "Convolutional": 12.839826658108842,
        "Statistical": 9.12888184236321,
        "Regret": 8.290731159325594,
        "Networks": 49.22464888669562,
        "Adversarial": 11.48257358857441,
        "Approximate": 9.891248379320093,
        "Feature": 15.324932845025643,
        "Monte": 8.6093263457545,
        "Detection": 15.09205910938638,
        "Continuous": 8.986018494046865,
        "Temporal": 8.928401678612603,
        "Gradient": 17.985314196245945,
        "Decision": 9.158310146942426,
        "Unsupervised": 9.113402312309983,
        "Process": 8.827709988702011,
        "Search": 14.679672962851454,
        "Graphs": 14.019417973042867,
        "Evaluation": 8.961818157521583,
        "Estimation": 18.60075340225022,
        "Efficient": 20.583710734243894,
        "Time": 12.695869639064227,
        "Modeling": 14.938140184054664,
        "System": 9.486753869470022,
        "Clustering": 25.639546100233265,
        "Gaussian": 12.913578838570471,
        "Generative": 15.496493036619071,
        "Matrix": 18.23550920026399,
        "Network": 18.087313860183016,
        "Classification": 14.54065928351385,
        "Inference": 19.019056411260216,
        "Local": 10.977301324599962,
        "Faster": 9.438395077627307,
        "Recurrent": 14.607376836398856,
        "Models": 32.074846686457406,
        "Neural": 34.50551493371443,
        "Hierarchical": 13.99598914423668,
        "Model": 16.41186885275151,
        "Online": 23.984072971152635,
        "Policy": 11.756227146252758,
        "Convex": 9.412642074516432,
        "Multi-Task": 8.235616233207729,
        "Sequence": 12.365848480003212,
        "Systems": 9.11704184493007,
        "Active": 13.724696533434887,
        "Methods": 8.291145686786946,
        "Streams": 8.941750287820005,
        "Robust": 20.911143237035287,
        "Regression": 14.418387037631833,
        "Data": 29.026840189822142,
        "Carlo": 8.28176795593505,
        "Factorization": 9.89840040273247
    };
    var data = [];
    for (var key in keywords) {
        data.push({
            name: key,
            value: Math.sqrt(keywords[key])
        })
    }


    var option = {

        backgroundColor: '#F7F7F7',
        series: [{
            type: 'wordCloud',
            sizeRange: [10, 100],
            rotationRange: [-90, 90],
            rotationStep: 45,
            gridSize: 2,
            shape: 'pentagon',

            drawOutOfBound: false,
            textStyle: {
                normal: {
                    color: function () {
                        return 'rgb(' + [
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160),
                            Math.round(Math.random() * 160)
                        ].join(',') + ')';
                    }
                },
                emphasis: {
                    color: 'red'
                }
            },
            data: data
        }]
    };


    var myChart = echarts.init(document.getElementById('wordCloud'));

    myChart.setOption(option);

    window.onresize = function () {
        myChart.resize();
    }
</script>

<script>
    $(function () {
        // Easy pie charts
        $('.chart').easyPieChart({animate: 1000});
    });
</script>
</html>