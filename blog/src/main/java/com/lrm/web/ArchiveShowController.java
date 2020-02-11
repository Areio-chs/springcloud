package com.lrm.web;

import com.lrm.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by limi on 2017/10/23.
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;
//在数据库查到所有的年份   然后根据年份查到每个年份的博客 放到map 前端根据map进行渲染
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
//        拿到博客的条数
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
