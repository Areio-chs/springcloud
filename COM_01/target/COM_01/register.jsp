<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>注册页面</title>
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
    </style>
</head>
<body>

<div class="container-fluid"  style="background: url('img/zhuce.png');width: 100%;height: 600px;">
    <div class="row"style="margin-top: 60px; text-align: center;"></div>
    <div class="row">
        <div class="col-md-2"></div>
        <div class="col-md-8" id="registForm"
             style="background: #fff; padding: 40px 80px; margin: 60px; border: 7px solid #ccc;">
            <font style="color: #3164af; font-size: 18px ;font-weight: normal; padding: 10px;">个人注册</font>USER REGISTER
            <form  class="form-horizontal" id="myForm" style="margin-top: 5px; " >
                <div class="form-group">
                    <label for="user_name" class="col-sm-2 control-label">用户名</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="user_name"
                               placeholder="请输入用户名" name="username">
                        <span class="help-block"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label for="password" class="col-sm-2 control-label">密码</label>
                    <div class="col-sm-8">
                        <input type="text" class="form-control" id="password"
                               placeholder="请输入密码" name="password">
                        <span class="help-block"></span>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-offset-2 col-sm-10">
                        <button class="btn btn-primary" id="rei" type="button">注册</button>
                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-2"></div>

    </div>
    <div class="row"></div>

</div>
<script type="text/javascript">

    //校验用户名是否可用
    $("#user_name").change(function(){
        //发送ajax请求校验用户名是否可用
        var userName = this.value;//输入框的value值
        $.ajax({
            url:"${APP_PATH}/checkuser",
            data:"userName="+userName,//发送请求参数名=值
            type:"POST",
            success:function(result){
                if(result.code==100){//100代表成功
                    show_validate_msg("#user_name","success","用户名可用");
                    //可用或者不可用时 给这个按钮添加属性
                    $("#rei").attr("ajax-va","success");
                }else{
                    //不可用  把按钮禁止用掉
                    show_validate_msg("#user_name","error",result.extend.va_msg);
                    $("#rei").attr("ajax-va","error");
                }
            }
        });
    });


//点击注册用户方法
    $("#rei").click(function () {
        //1.先对要提交给服务器的数据进行校验（校验失败时点击注册不发ajax）
        if(!validate_add_form()){
            //alert("错误");
            return false;
        }
        //拿到按钮的可用或者不可属性，判断它是成功还是失败，失败时直接return不发送ajax
        //（用户不可用时点击注册不发ajax）
        if($(this).attr("ajax-va")=="error"){
            return false;
        }

        //2校验成功才发送请求
        $.ajax({
            url:"${APP_PATH}/reg",
            type:"POST",
            //拿到表单里 form标签下的所有键值，并序列化
            data:$("#myForm").serialize(),
            error: function(request){
              alert("error");
            },
            success:function (result) {
                if(result.code==100) {
                    window.location.href = "index.jsp";
                }
                else{
                    //显示失败信息,后台校验的，有哪个字段的错误信息就显示哪个字段的；
                    if(undefined != result.extend.errorFields.userName)
                    {
                        show_validate_msg("#user_name", "error", result.extend.errorFields.userName);
                    }
                }
            }
        });
    });
    //校验表单数据
    function validate_add_form() {
        //1拿到要校验的数据，使用正则表达式
      var userName=  $("#user_name").val();//拿到输入框的值
        var regName = /(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})/;
        if(!regName.test(userName)){
            //alert("用户名可以是2-5位中文或者6-16位英文和数字的组合");
            show_validate_msg("#user_name", "error", "用户名可以是2-5位中文或者6-16位英文和数字的组合");
            return false;
        }else{
            show_validate_msg("#user_name", "success", "");
            return true;
        }
    }
    //显示校验结果的提示信息
    function show_validate_msg(ele,status,msg){


        //清除当前元素的校验状态
        $(ele).parent().removeClass("has-success has-error");
        $(ele).next("span").text("");

        //给当前input元素的父元素添加一些类 他的框在错误时就会显示对应的颜色
        if("success"==status){
            $(ele).parent().addClass("has-success");
            $(ele).next("span").text(msg);
        }else if("error" == status){
            $(ele).parent().addClass("has-error");
            $(ele).next("span").text(msg);
        }
    }




</script>
</body>
</html>