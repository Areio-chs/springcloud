package com.controller;

import com.bean.Msg;
import com.bean.User;
import com.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//代替了@ResponseBody和@controller,这样就不要再方法上写@ResponseBody了
//@ResponseBody的作用其实是将java对象转为json格式的数据,是作用在方法上的。
public class RegisterController {
    @Autowired
    RegisterService regservice;

    //提交信息进行注册
    /**
     *
     * 1、要支持JSR303校验（后台校验,防止绕过前端直接进入到后台）
     * 2、导入Hibernate-Validator
     *
     *
     * @return
     */

    @PostMapping("/reg")
    public Msg saveUser(@Valid User user, BindingResult result){
//@Valid是用来支持校验的，在USer类中写上检验规则
        if(result.hasErrors()){
            //如果校验失败，应该返回失败，显示校验失败的错误信息
            Map<String, Object> map = new HashMap<>();//键值对
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError : errors) {
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }        // 键                            //值
            return Msg.fail().add("errorFields", map);
        }else{
            regservice.saveUser(user);
            return Msg.success();
        }

    }
    /**
     * 检查用户名是否可用
     * @param userName
     * @return
     */

    @RequestMapping("/checkuser")//省去了value=，没有写method默认get/post都支持
    //可以是这样(String empName)，从请求参数中获得这个名的值，加上前面那个是明确告诉mvc要取出empName的值
    public Msg checkuser(@RequestParam("userName")String userName){//从请求参数获得 赋给这个变量
        //先判断用户名是否是合法的表达式;
        String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
        if(!userName.matches(regx)){//如果不匹配
            return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母的组合或者2-5位中文");
        }

        //数据库用户名重复校验
        boolean b = regservice.checkUser(userName);
        if(b){
            return Msg.success();
        }else{
            return Msg.fail().add("va_msg", "用户名不可用");
        }
    }



    //    
//    @RequestMapping(value="/log",method= RequestMethod.POST)
//    public String loginUser(User user,Model model){//用户传进来的参数
//
//        List<User> users = regservice.loginUser(user);//数据库的,根据输入用户名查询用户的信息
//        User user1 = users.get(0);
//        if ((user1.getPassword()).equals(user.getPassword()))//当这个用户密码与数据库对应
//        {
//            //当这个用户的输入的密码正确，进入主页面
//            return "hello";
//        }
//        else {
//            model.addAttribute("errorInfo","您的账户或密码有误");
//            return "index";
//        }
//    }

    @RequestMapping(value="/log",method= RequestMethod.POST)
    public Msg loginUser(User user/*, @RequestParam ("autoLogin")String autoLogin, HttpServletRequest req, HttpServletResponse response*/){//用户传进来的参数

        List<User> users = regservice.loginUser(user);//数据库的,根据输入用户名查询用户的信息

        if(users.isEmpty()){
            //System.out.println("2222");
            return Msg.fail().add("errorInfo","该用户不存在");
        } else if (( users.get(0).getPassword()).equals(user.getPassword()))//当这个用户密码与数据库对应
        {
            //当这个用户的输入的密码正确，进入主页面
//        if (autoLogin!=null){
//            Cookie cookie_username=new Cookie("cookie_username",user.getUsername());
//            Cookie cookie_password=new Cookie("cookie_password",user.getUsername());
//           //设置cookie的持久化时间
//            cookie_username.setMaxAge(60*60);
//            cookie_password.setMaxAge(60*60);
//            //设置cookie携带路径
//            cookie_username.setPath(req.getContextPath());
//            cookie_password.setPath(req.getContextPath());
//            //发送cookie
//            response.addCookie(cookie_username);
//            response.addCookie(cookie_password);
//
//        }
            return Msg.success();
        }
        else {
            //System.out.println(users.get(0));
            return Msg.fail().add("errorInfo","您的账户或密码有误");
        }
    }

    @RequestMapping("/select")
    public Msg getUser(){
        List<User> users= regservice.getAll();
        return Msg.success().add("people",users);
    }



}
