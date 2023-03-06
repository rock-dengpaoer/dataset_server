package com.xdt.dataset_server.Server.Impl;

import com.xdt.dataset_server.Dao.UserDao;
import com.xdt.dataset_server.Server.UserServer;
import com.xdt.dataset_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author XDT
 * @ClassName UserServerImpl
 * @Description: TODO
 * @Date 2023/2/27 16:55
 **/
@Service(value = "UserServer")
public class UserServerImpl implements UserServer {

    @Autowired
    private  UserDao userDao;

    @Override
    public List<User> SelectAllUser() {
        return userDao.SelectAllUser();
    }

    @Override
    public List<User> selectAllUserOnlyName() {
        return userDao.selectAllUserOnlyName();
    }

    @Override
    public List<User> selectAllUserByField() {
        return userDao.selectAllUserByField();
    }

    @Override
    public boolean insertUser(User user) {
        try {
            userDao.insertUser(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean updateUser(User user) {
        try {
            userDao.updateUser(user);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delUser(String uuid) {
        try {
            userDao.delUser(uuid);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public User loginService(String uname, String password) {
        User user = userDao.findByUnameAndPassword(uname, password);
        if(user!=null){
            user.setPassword("");
        }
        return user;
    }

    @Override
    public User registService(User user) {
        if(userDao.findByUname(user.getName()) != null){
            return null;
        }else {
            userDao.insertUser(user);
            return userDao.selectUserByUuid(user.getUuid());
        }
    }
}
