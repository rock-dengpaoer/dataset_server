package com.xdt.dataset_server.Controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.xdt.dataset_server.Server.Impl.UserServerImpl;
import com.xdt.dataset_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * @author XDT
 * @ClassName Controller1
 * @Description: TODO
 * @Date 2023/2/27 16:24
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserServerImpl userServer;

    @GetMapping("/hello")
    public String hello(){
        return "hello world!";
    }

    @GetMapping("/selectAllUser")
    public List<User> selectAllUser(){
        List<User> userList = this.userServer.SelectAllUser();
        for(int i = 0; i < userList.size(); i++){
            User user = userList.get(i);
            System.out.println(user);
        }
        return userList;
    }

    @GetMapping("/selectAllUserOnlyName")
    public List<User> selectAllUserOnlyName(){
        return this.userServer.selectAllUserOnlyName();
    }

    @GetMapping("/selectAllUserByField")
    public List<User> selectAllUserByField(){
        return this.userServer.selectAllUserByField();
    }

    /*发送json数据*/
    @PostMapping("/insertUser")
    public Boolean insertUser(@RequestBody User user
    ){
        System.out.println(user);
        String simpleUUID = IdUtil.simpleUUID();
        user.setUuid(simpleUUID);
        user.setCreateTime(DateUtil.date(System.currentTimeMillis()));
        user.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        user.setIsAuthentication(0);

        System.out.println(user);

        return userServer.insertUser(user);
    }

    @PutMapping("/updateUser")
    public Boolean updateUser(
            @RequestBody User user
    ){
        user.setUpdateTime(DateUtil.date(System.currentTimeMillis()));
        return userServer.updateUser(user);
    }

    @DeleteMapping("/delUser")
    public Boolean delUser(@RequestParam("uuid") String uuid){
        return userServer.delUser(uuid);
    }
}
