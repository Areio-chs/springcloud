<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>注册成功</title>

    <!-- 1. 导入CSS的全局样式 -->
    <link href="${pageContext.request.contextPath}css/bootstrap.min.css" rel="stylesheet">
    <!-- 2. jQuery导入，建议使用1.9以上的版本 -->
    <script src="${pageContext.request.contextPath}js/jquery-2.1.0.min.js"></script>
    <!-- 3. 导入bootstrap的js文件 -->
    <script src="${pageContext.request.contextPath}js/bootstrap.min.js"></script>

    <style>
        .div1{
            height: 50px;
            text-align: center;
            margin-top: 50px;
        }
        p{
            text-align: center;
        }
    </style>

</head>
<body>

<div class="div1">
    <span style="font-size: 25px">恭喜你，注册成功！！！</span>
</div>
<p>
    <span id="time" style="color: red">5</span>秒之后，自动跳转到登录页面......
</p>
<script>
    var time = document.getElementById("time");
    var  scond = 5;
    function showTime() {

        scond--;
        if(scond <= 0){
            location.href = "${pageContext.request.contextPath}/user/login";
        }
        time.innerHTML = scond + " ";
    }
    setInterval(showTime,1000);
</script>

</body>
</html>