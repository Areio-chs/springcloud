<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>

</head>
<body>

<%--<div><h3>乐宗</h3></div>--%>
<div id="box" style="width:600px;height:400px;"></div>

<script>

    $(function () {
        var myChart = echarts.init(document.getElementById('box'));
        setInterval(function(){
            $.get("/COM_01/serialPort/testVoltage", {}, function (data) {
                /*alert(data);
                alert(data.xList);
                alert(data.yList);*/

                myChart.setOption({
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: [
                            <c:forEach begin="0" end="4" var="x" varStatus="s">
                            data.xList[${x}],
                                </c:forEach>
                        ],
                            //['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: {
                        data: [
                            <c:forEach begin="0" end="4" var="y" varStatus="s">
                            data.yList[${y}],
                            </c:forEach>
                        ],

                            //[820, 932, 901, 934, 1290, 1330, 1320],
                        type: 'line',
                        areaStyle: {}
                    }
                }, true);
            })
        }, 3000)
    })

</script>
</body>
</html>
