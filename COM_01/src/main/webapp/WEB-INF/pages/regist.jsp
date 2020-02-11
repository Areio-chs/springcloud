<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <meta charset="utf-8">
    <title>注册</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/common.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/register.css">
    <!--导入jquery-->
    <script src="${pageContext.request.contextPath}/js/jquery-2.1.0.min.js"></script>

    <script>
        function checkUsername() {
            //1.获取用户名值
            var username = $("#username").val();
            //2.定义正则
            var reg_username = /^\w{8,20}$/;

            //3.判断，给出提示信息
            var flag = reg_username.test(username);
            if(flag){
                //用户名合法
                $("#username").css("border","");
            }else{
                //用户名非法,加一个红色边框
                $("#username").css("border","1px solid red");
            }

            return flag;
        }

        //校验密码
        function checkPassword() {
            //1.获取密码值
            var password = $("#password").val();
            //2.定义正则
            var reg_password = /^\w{8,20}$/;

            //3.判断，给出提示信息
            var flag = reg_password.test(password);
            if(flag){
                //密码合法
                $("#password").css("border","");
            }else{
                //密码非法,加一个红色边框
                $("#password").css("border","1px solid red");
            }

            return flag;
        }


        $(function () {
            //当表单提交时，调用所有的校验方法
            $("#registerForm").submit(function(){
                //1.发送数据到服务器
                if(checkUsername() && checkPassword()){
                    //校验通过,发送ajax请求，提交表单的数据   username=zhangsan&password=123

                    $.post("${pageContext.request.contextPath}/user/regist",$(this).serialize(),function(result){
                        //处理服务器响应的数据  data  {flag:true,errorMsg:"注册失败"}
                        if(result){
                            //注册成功，跳转成功页面
                            location.href="${pageContext.request.contextPath}/user/regist_ok";
                        }else{
                            //注册失败,给errorMsg添加提示信息
                            $("#errorMsg").html("注册失败!");
                        }
                    });
                }
                //2.不让页面跳转
                return false;
                //如果这个方法没有返回值，或者返回为true，则表单提交，如果返回为false，则表单不提交
            });

            //当某一个组件失去焦点是，调用对应的校验方法
            $("#username").blur(checkUsername);
            $("#password").blur(checkPassword);
        });

    </script>


</head>
<body>
<div class="rg_layout">
    <div class="rg_form clearfix">
        <div class="rg_form_left">
            <p>新用户注册</p>
            <p>USER REGISTER</p>
        </div>
        <div class="rg_form_center">
            <div id="errorMsg" style="color:red;text-align: center"></div>
            <!--注册表单-->
            <form id="registerForm" action="user">
                <!--提交处理请求的标识符-->
                <input type="hidden" name="action" value="register">
                <table style="margin-top: 25px;">
                    <tr>
                        <td class="td_left">
                            <label for="username">用户名</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="username" name="username" placeholder="请输入账号(8-20位)">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                            <label for="password">密码</label>
                        </td>
                        <td class="td_right">
                            <input type="text" id="password" name="password" placeholder="请输入密码(8-20位)">
                        </td>
                    </tr>
                    <tr>
                        <td class="td_left">
                        </td>
                        <td class="td_right check">
                            <input type="submit" class="submit" value="注册">
                            <span id="msg" style="color: red;"></span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div class="rg_form_right">
            <p>
                已有账号？
                <a href="${pageContext.request.contextPath}/user/login">立即登录</a>
            </p>
        </div>
    </div>
</div>


</body>
</html>
