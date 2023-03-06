package com.xdt.dataset_server.Dao;

import com.xdt.dataset_server.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * @author XDT
 * @ClassName UserDao
 * @Description: TODO
 * @Date 2023/2/27 16:47
 **/
@Mapper
@Repository
public interface UserDao {

    List<User> SelectAllUser();

    List<User> selectAllUserOnlyName();

    List<User> selectAllUserByField();

    boolean insertUser(User user);

    boolean updateUser(User user);

    boolean delUser(String uuid);

    User findByUname(String uname);
    User findByUnameAndPassword(String uname, String password);

    User selectUserByUuid(String uuid);
}
