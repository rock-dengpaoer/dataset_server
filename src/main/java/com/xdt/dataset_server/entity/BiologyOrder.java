package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author XDT
 * @ClassName BiologyOrder
 * @Description: TODO
 * @Date 2023/2/28 10:23
 **/
@Data
public class BiologyOrder {
    //uuid
    private String uuid;
    //拉丁名
    private String nameLatin;
    //中文名
    private String nameCn;
    //介绍
    private String introduce;
    //所属纲的id
    private String classUuid;
    /*创建时间*/
    private Date createTime;
    /*更新时间*/
    private Date updateTime;
    /*创建者*/
    private String userUuid;
}
