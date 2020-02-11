package com.lrm.web;

import com.lrm.po.Comment;
import com.lrm.po.User;
import com.lrm.service.BlogService;
import com.lrm.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by limi on 2017/10/22.
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")//从全局配置里面拿到
    private String avatar;

    @GetMapping("/comments/{blogId}")//根据id拿到评论列表
    public String comments(@PathVariable Long blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
//        返回一个片段而不是一整个页面
    }


    @PostMapping("/comments")//发布评论
    public String post(Comment comment, HttpSession session) {
        Long blogId = comment.getBlog().getId();

        comment.setBlog(blogService.getBlog(blogId));
        User user = (User) session.getAttribute("user");
        if (user != null) {//有用户时 用自己的头像
//            这个博客只有博主才有user 即后台使用者   所有其他人是拿不到头像的
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);//是否是管理员的评论
        } else {
            comment.setAvatar(avatar);//没有用户时默认头像
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + blogId;
    }



}
