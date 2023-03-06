package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author XDT
 * @ClassName BiologyClass
 * @Description: TODO
 * @Date 2023/2/27 20:11
 **/
@Data
public class BiologyClass {
    private String uuid;
    private String nameCn;
    private String nameLatin;
    private String introduce;
    private Date createTime;
    private Date updateTime;
    private String userUuid;
}
