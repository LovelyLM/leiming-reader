package com.leiming.controller;


import cn.hutool.captcha.GifCaptcha;
import cn.hutool.captcha.ICaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@Controller
public class CaptchaController {
    @GetMapping("/getCaptcha")
    public void getGifCode(HttpServletResponse response, HttpServletRequest request) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Expires", "0");
        response.setContentType("image/gif");
        ICaptcha captcha = new GifCaptcha(120, 50, 4);
        captcha.createCode();
        log.info(captcha.getCode());
        request.getSession().setAttribute("captcha", captcha.getCode());
        captcha.write(response.getOutputStream());
    }
}
