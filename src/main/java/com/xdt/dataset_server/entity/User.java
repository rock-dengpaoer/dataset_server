package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;


/**
 * @author XDT
 * @ClassName User
 * @Description: TODO
 * @Date 2023/2/27 16:24
 **/
@Data
public class User {
    private String uuid;
    private String email;
    private String name;
    private String password;
    private String phone;
    private String job;
    private String address;
    private Integer isAuthentication;
    private String idNumber;
    private Date createTime;
    private Date updateTime;
    private String portraitUuid;
    private String introduce;
}
