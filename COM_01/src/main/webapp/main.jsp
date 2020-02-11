<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主页</title>

   <%-- <link href="templatemo_style.css" rel="stylesheet" type="text/css" />--%>
    <link href="${pageContext.request.contextPath}/css/index.css" rel="stylesheet" type="text/css" />

<%--    <script src="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js"></script>--%>
    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
<script>
    $(function () {
        //一开登录页面就打开串口
        $.get("${pageContext.request.contextPath}/serialPort/open", {}, function (data) {

        });
    })
</script>
</head>
<body>
<div class="container-fluid"  style="background: url('img/xixi.jpg');width: 100%;height: 100%;">




<div id="div_out">
    <div id="header1" align="center">
        <img src="img/xiao.jfif" id="imgs">
    </div>
    <div id="header2">
        <span id="span"><strong>智慧农业物联网传输网站</strong></span>
    </div>
</div>

<div id="wrap">
    <div class="head">
        <ul class="tabs headMenu">
            <li class="active"><a href="#tab1" >灯</a></li>
            <li id="Dian"><a href="#tab2">电压</a></li>
            <li id="Wen"><a href="#tab3">温度</a></li>
        </ul>
    </div>
    <div class="tab_container">
        <div id="tab1" class="tab_content" style="display: block; ">
            <img id="light1" src="img/off.gif">
            <img id="light2" src="img/off.gif">
        </div>
        <div id="tab2" class="tab_content" style="display: block; ">
            <div id="box1"></div>
        </div>
        <div id="tab3" class="tab_content" style="display: block; ">
            <div id="box2"></div>
        </div>
    </div>
</div>
</div>
</div>
</body>

<script>
// 先把所有隐藏  然后打开导航变亮并打开第一个框
    $(".tab_content").hide();
    $("ul.tabs li:first").addClass("active").show();
    $(".tab_content:first").show();


    $("ul.tabs li").click(function() {
        $("ul.tabs li").removeClass("active");
        $(this).addClass("active");

        $(".tab_content").hide();
        //获得 a标签的href属性
        var activeTab = $(this).find("a").attr("href");
        //fadeIn() 方法使用淡入效果来显示被选元素，假如该元素是隐藏的
        $(activeTab).fadeIn();
        return false;
    });



    //当点击按钮 进入这个方法  传入对应的指令order
    function send_message(order) {
        $.ajax({
            url:"${pageContext.request.contextPath}/serialPort/send",
            data:"order="+order,//发送请求参数
            type:"GET",
            success:function (result) {
                //console.log(result);
                alert(result);
            }
        });
    }

    //1.获取图片对象
    var light1 = document.getElementById("light1");
    var flag1 = false;//代表灯是灭的。 off图片
    //1.获取图片对象
    var light2 = document.getElementById("light2");
    var flag2 = false;//代表灯是灭的。 off图片

    //2.绑定单击事件,
    light1.onclick = function(){
        if(flag1){//灯一是开的，用于关灯。在灯二亮时关 发指令二，在灯二关时关，发错误指令
            light1.src = "${pageContext.request.contextPath}/img/off.gif";
            if(flag2)
            {
                //发指令二
                send_message(2);
            }else{
                //错误指令
                send_message(4);
            }
            flag1=false;
        }else{
            //灯一是灭的，用于开灯。此时灯二已经打开，则发送指令三，如果灯二是灭的，则发送指令一
            light1.src = "${pageContext.request.contextPath}/img/on.gif";
            if(flag2)
            {
                //指令三
                send_message(3);
            }else{
                //指令一
                send_message(1);
            }
            flag1=true;
        }
    }

    light2.onclick = function(){
        //
        if(flag2){//判断如果灯er是开的，则灭掉,
            light2.src = "${pageContext.request.contextPath}/img/off.gif";
            if(flag1){
                send_message(1);
            }
            else {
                send_message(4);
            }
            flag2=false;
        }else{
            //如果灯er是灭的，则打开
            light2.src = "${pageContext.request.contextPath}/img/on.gif";
            if(flag1)
            {
                send_message(3);
            }
            else {
                send_message(2);
            }
            flag2=true;
        }

    }

    var Dian = document.getElementById("Dian");
    Dian.onclick = function(){
        var myChart1 = echarts.init(document.getElementById('box1'));
        setInterval(function(){
            $.post("${pageContext.request.contextPath}/serialPort/testVoltage", {}, function (data) {
                myChart1.setOption({
                    title:{
                        text:'电压(V)/时间(s)'
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: [
                            <c:forEach begin="0" end="4" var="x" varStatus="s">
                            data.xList[${x}],
                            </c:forEach>
                        ],
                    },
                    yAxis: {
                        type: 'value',
                    },
                    series: {
                        name:'电压',
                        data: [
                            <c:forEach begin="0" end="4" var="y" varStatus="s">
                            data.yList[${y}],
                            </c:forEach>
                        ],
                        type:'line',
                        itemStyle: {  //节点数据显示
                            normal: {
                                label: {
                                    show: true,
                                    position: 'top',
                                    color:'blue',
                                }
                            }
                        },
                        areaStyle:{
                            normal:{

                            }
                        }
                    }
                }, true);
            })
        }, 6000)
    }
    var Wen = document.getElementById("Wen");
    Wen.onclick = function(){
        var myChart2 = echarts.init(document.getElementById('box2'));
        setInterval(function(){
            $.post("${pageContext.request.contextPath}/serialPort/testTemperature", {}, function (data) {
                myChart2.setOption({
                    title:{
                        text:'温度(℃)/时间(s)'
                    },
                    xAxis: {
                        type: 'category',
                        boundaryGap: false,
                        data: [
                            <c:forEach begin="0" end="4" var="x" varStatus="s">
                            data.xList[${x}],
                            </c:forEach>
                        ],
                    },
                    yAxis: {
                        type: 'value',
                    },
                    series: {
                        name:'温度',
                        data: [
                            <c:forEach begin="0" end="4" var="y" varStatus="s">
                            data.yList[${y}],
                            </c:forEach>
                        ],
                        type:'line',
                        itemStyle: {  //节点数据显示
                            normal: {
                                label: {
                                    show: true,
                                    position: 'top',
                                    color:'blue',
                                }
                            }
                        },
                        areaStyle:{
                            normal:{

                            }
                        }
                    }
                }, true);
            })
        }, 6000)
    }

</script>
</html>
