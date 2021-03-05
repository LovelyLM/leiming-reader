package com.leiming.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author LovelyLM
 * @date 2021-03-06 3:21
 */
@Controller
@RequestMapping("admin")
public class AdminIndexController {

    @GetMapping("index.html")
    public ModelAndView index(){
        return new ModelAndView("admin/index");
    }
    @GetMapping("comment.html")
    public ModelAndView comment(){
        return new ModelAndView("admin/comment");
    }
}
