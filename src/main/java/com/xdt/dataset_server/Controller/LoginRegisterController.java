package com.xdt.dataset_server.Controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xdt.dataset_server.Server.Impl.TokenServiceImpl;
import com.xdt.dataset_server.Server.Impl.UserServerImpl;
import com.xdt.dataset_server.entity.TokenInfo;
import com.xdt.dataset_server.entity.User;
import com.xdt.dataset_server.utils.JwtUtil;
import com.xdt.dataset_server.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.servlet.http.HttpSession;


/**
 * @author XDT
 * @ClassName LoginRegisterController
 * @Description: TODO
 * @Date 2023/2/27 21:22
 **/
@Slf4j
@RestController
@RequestMapping("/account")
public class LoginRegisterController {

    @Autowired
    private UserServerImpl userServer;

    @Autowired
    private TokenServiceImpl tokenService;


    @PostMapping("/login")
    public Result loginController(@RequestParam String uname, @RequestParam String password){
        User user = userServer.loginService(uname, password);
        if(user!=null){
            TokenInfo tokenInfo = JwtUtil.createToken(user);
            if(tokenService.insertToken(tokenInfo)){
                /*隐藏信息*/
                tokenInfo.setUserUuid(null);
                tokenInfo.setUserName(null);
                return Result.success(tokenInfo,"登录成功！");
            }else {
                return Result.error("500", "token失败");
            }
        }else{
            return Result.error("123","账号或密码错误！");
        }
    }

    /*暂时不开放注册*/
    //@PostMapping("/register")
    //public Result registController(@RequestBody User newUser){
    //    newUser.setUuid(IdUtil.simpleUUID());
    //    newUser.setCreateTime(DateUtil.date());
    //    newUser.setUpdateTime(DateUtil.date());
    //    User user = userServer.registService(newUser);
    //    if(user!=null){
    //        return Result.success(user,"注册成功！");
    //    }else{
    //        return Result.error("456","用户名已存在！");
    //    }
    //}

    @GetMapping("/hello")
    public String hello(){
        return "hello login register";
    }

    private void setAttribute(HttpSession session, String username, String uuid){
        if(session == null){
            log.warn("session is null, username#{}, uuid#{}", username, uuid);
        }else {
            session.setAttribute("username", username);
            session.setAttribute("uuid", uuid);
        }
    }



}
