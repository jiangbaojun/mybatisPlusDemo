package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


@Controller
public class IndexController {
    /** 通过EnableConfigurationProperties、ConfigurationProperties读取配置文件的配置 */

    @RequestMapping("/")
    public String index(Model model, HttpServletRequest request){
    	model.addAttribute("content", "Hello Thymeleaf8");
        return "index";
    }

    @RequestMapping("/ex/test")
    public String indexEx(Model model, HttpServletRequest request){
        System.out.println("测试异常");
    	int a = 5/0;
        return "index";
    }

}
