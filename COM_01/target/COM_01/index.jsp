<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>登录页面</title>
    <%
        pageContext.setAttribute("APP_PATH", request.getContextPath());
    %>
    <script type="text/javascript"
            src="${APP_PATH }/static/js/jquery-1.12.4.min.js"></script>
    <link
            href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
            rel="stylesheet">
    <script
            src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <style>
        .col-md-8 {
            width: 55%;
        }
        span{
            color:red;
        }
    </style>
</head>
<body>
<div class="container-fluid"  style="background: url('img/denglu.png');width: 100%;height: 600px;">
    <div class="row"style="margin-top: 60px; text-align: center;"></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8" id="loginForm"
             style="background: #fff; padding: 40px 80px; margin: 60px; border: 7px solid #a5b2bd;;">
            <font style="color: #3164af; font-size: 18px ;font-weight: normal; padding: 10px;">个人登录</font>USER LOGIN
            <form  class="form-horizontal" id="mylogin" style="margin-top: 5px; " >


                <div class="form-group" >
                        <div class="col-sm-2">

                        </div>
                        <div class="col-sm-7" >
                                <span id="abc"></span>
                        </div>
                </div>


                <div class="form-group">

                    <label for="username" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-8">
                        <%--<span  id="add"></span>--%>
                        <input type="text" class="form-control" id="username"
                               placeholder="请输入用户名" name="username">
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="password"
                               placeholder="请输入密码" name="password">
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-2">

                    </div>
                    <div class="col-sm-8">
                        <label><input type="checkbox" name="autoLogin" value="autoLogin">自动登录</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <label><input type="checkbox">记住用户名</label>
                        <font style="color: #3164af; font-size: 15px ;font-weight: normal; padding: 10px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;还未注册？
                            <a href="register.jsp">立即注册</a></font>
                    </div>
                </div>
                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-primary" id="lo" type="button">登录</button>

                    </div>
                </div>

            </form>
        </div>
        <div class="col-md-2"></div>

    </div>
    <div class="row"></div>

</div>
<script type="text/javascript">


    $("#lo").click(function () {
        $.ajax({
            url:"${APP_PATH}/log",
            type:"POST",
            //拿到表单里 form标签下的所有键值，并序列化
            data:$("#mylogin").serialize(),
            error: function(request){
                //请求失败
               // alert("error");
                //在本页面的用户名上面显示提示信息
                var s=request.extend.errorInfo;
                //将错误提示信息添加到span里
                //创建一个div元素,在里面添加info，然后再整体添加到#add
                $("<span></span>").append(s).appendTo("#add");

            },
            success:function (result) {
                //请求成功
                if (result.code==100) {
                    //alert("success");
                     window.location.href="main.jsp";
                }
                else{
                   // alert("error");
                    //在本页面的用户名上面显示提示信息
                   // var s=request.extend.errorInfo;
                  ways(result.extend.errorInfo);

                }


            }
        });
    });
    function ways(msg) {

    // <div class="col-sm-2">
    //         <span>&nbsp;&nbsp;&nbsp;&nbsp;</span>
    //     </div>
    //     <div class="col-sm-7" id="add">
    //
    //         </div>
    //     var s2=$("<span></span>").text(&nbsp;&nbsp;&nbsp;&nbsp;);
    //     var s1=$("<div></div>").addClass("col-sm-2").append(s2);


        //将错误提示信息添加到span里
        $("span").empty();
        $("span").text("   "+msg);



    }


</script>
</body>
</html>
