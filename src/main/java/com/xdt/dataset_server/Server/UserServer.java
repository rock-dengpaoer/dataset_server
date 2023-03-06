package com.xdt.dataset_server.Server;

import com.xdt.dataset_server.entity.User;

import java.util.List;

public interface UserServer {
    List<User> SelectAllUser();

    List<User> selectAllUserOnlyName();

    List<User> selectAllUserByField();

    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean delUser(String uuid);

    /*登录*/
    User loginService(String uname, String password);

    /*注册*/
    User registService(User user);
}
