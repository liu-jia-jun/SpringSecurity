package com.security.demo.controller;

import com.security.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author admin
 */
@Controller("testController")
public class TestController {

    @Autowired
    private TestService testService;


    @RequestMapping("/hello/{username}")
    @ResponseBody
    public String hello(@PathVariable("username") String username) {
        return testService.selectByUsername(username).toString();
    }

    /**
     * 配置用户没有权限访问资源时跳转的页面
     * 替换403页面提示用户没用访问权限
     * @return
     */
    @RequestMapping("/nopermission")
    public String noPermission(){
        return "nopermission";
    }

    @RequestMapping("error")
    public String error(){
        return "error";
    }

    @RequestMapping("/message")
    public String message(){
        return "message";
    }

    @RequestMapping("/show")
    public String show(){
        return "show";
    }

    @RequestMapping("/login")
    public String login() {

        return "login";
    }


    @RequestMapping("/index")
    public String index() {

        return "index";
    }

    @RequestMapping("/worker")
    public String worker(){
        return "worker";
    }


    /**
     * @Secured注解表示,当用户的角色匹配到该注解中的值表示可以访问
     * 多个用户用逗号隔开,角色添加"ROLE_"前缀
     *
     * @return
     */
    @RequestMapping("/secured")
//    @Secured({"ROLE_admin","ROLE_worker"})
    public String secured(){
        return "secured";
    }

    @RequestMapping("/preauthorize")
    @PreAuthorize("hasAnyAuthority('admin')")
    @ResponseBody
    public String preAuthorize(){
        return "preauthorize";
    }
}
