package com.leiming.controller;

import cn.hutool.core.util.StrUtil;
import com.leiming.entity.Evaluation;
import com.leiming.entity.Member;
import com.leiming.service.EvaluationService;
import com.leiming.service.MemberService;
import com.leiming.service.MyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 10796
 */
@Slf4j
@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private EvaluationService evaluationService;

    /**
     * 返回注册页面
     * @return 返回视图
     */
    @GetMapping("register.html")
    public ModelAndView showRegister(){
        return new ModelAndView("register");
    }

    /**
     * 用户注册的接口
     * @param member 用户
     * @param captcha 验证码
     * @param request
     * @return
     */
    @PostMapping("register")
    @ResponseBody
    public Map register(Member member, String captcha , HttpServletRequest request){
        //从session中获取验证码
        String trueCaptcha = (String)request.getSession().getAttribute("captcha");

        Map<String,String> map = new HashMap<>();
        //判断session中验证码或者输入的验证码是否为空，或者输入和session中的验证码是否相同
        if (StrUtil.isBlank(captcha) ||  StrUtil.isBlank(trueCaptcha) || !StrUtil.equals(captcha, trueCaptcha, true)){
            map.put("code","captcha01");
            map.put("mas","验证码错误");
            return map;
        }

        map.put("code", "0");
        map.put("msg", "success");
        //捕捉可能有的异常
        try{
            memberService.createMember(member);
        }catch (MyException e){
            map.put("msg", e.getCode());
        }
        return map;
    }

    /**
     * 返回登录页面
     * @return 返回视图
     */
    @GetMapping("login.html")
    public ModelAndView showLogin(){
        return new ModelAndView("login");
    }

    @PostMapping("check_login")
    @ResponseBody
    public Map check_login(Member member, String captcha, HttpSession session){
        //从session中获取验证码
        String trueCaptcha = (String)session.getAttribute("captcha");
        log.info("输入的验证码"+captcha);

        Map<String,String> map = new HashMap<>();
        //判断session中验证码或者输入的验证码是否为空，或者输入和session中的验证码是否相同
        if (StrUtil.isBlank(captcha) ||  StrUtil.isBlank(trueCaptcha) || !StrUtil.equals(captcha, trueCaptcha, true)){
            map.put("code","captcha01");
            map.put("msg","验证码错误");
            return map;
        }

        map.put("code", "0");
        map.put("msg", "success");
        //捕捉可能有的异常
        try{
            session.setAttribute("loginMember", memberService.checkLogin(member));
            log.info("success");

        }catch (MyException e){
            map.put("code", e.getCode());
            map.put("msg", e.getMsg());
        }
        return map;
    }
    @PostMapping("evaluation")
    @ResponseBody
    public Map evaluation(Evaluation evaluation){
        log.info(String.valueOf(evaluation));
        Map<String,String> map = new HashMap<>();
        map.put("code", "0");
        map.put("msg", "success");
        //捕捉可能有的异常
        try{
            evaluationService.evaluation(evaluation);
        }catch (MyException e){
            map.put("code", e.getCode());
            map.put("msg", e.getMsg());
        }
        return map;
    }
}
