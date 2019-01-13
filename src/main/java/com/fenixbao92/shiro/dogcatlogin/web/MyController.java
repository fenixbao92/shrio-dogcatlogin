package com.fenixbao92.shiro.dogcatlogin.web;

import com.fenixbao92.shiro.dogcatlogin.token.CatToken;
import com.fenixbao92.shiro.dogcatlogin.token.DogToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * Create by fenixbao92 on 2019/1/11.
 */

@Controller
public class MyController {

    @RequestMapping(value = "/login/dog", method = {RequestMethod.GET})
    @ResponseBody
    public String loginDog(String userName,String password){
        DogToken dogToken = new DogToken(userName.trim(), password.trim());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(dogToken);
        }catch (UnknownAccountException e) {
            return "loginDog fail:UnknownAccountException";
        }catch(IncorrectCredentialsException e) {
            return "loginDog fail:IncorrectCredentialsException";
        }
        return "dog login success:";
    }

    @RequestMapping(value = "/login/cat", method = {RequestMethod.GET})
    @ResponseBody
    public String loginCat(String userName,String password){
        CatToken catToken = new CatToken(userName.trim(), password.trim());
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(catToken);
        }catch (UnknownAccountException e) {
            return "loginCat fail:UnknownAccountException";
        }catch(IncorrectCredentialsException e) {
            return "loginCat fail:IncorrectCredentialsException";
        }
        return "cat login success:";
    }

    @RequestMapping(value = "/index", method = {RequestMethod.GET})
    @ResponseBody
    public String login(){
        Subject subject = SecurityUtils.getSubject();
        if(!subject.isAuthenticated()){
            return "you have not login yet!I am a login page :)";
        }else {
            return (String) subject.getSession().getAttribute("words");
        }
    }

    @RequestMapping(value = "/test", method = {RequestMethod.GET,RequestMethod.POST})
    //@RequiresPermissions("user:*")
    @ResponseBody
    public String test(){
        return "/test reached on:"+new Date();
    }

}
