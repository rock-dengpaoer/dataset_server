package com.xdt.dataset_server.entity.small;

import lombok.Data;

import java.util.Date;

@Data
public class Animal {
    private String uuid;
    private String nameCn;
    private String nameEn;
    private String introduce;
    private Date createTime;
    private Date updateTime;
    private String userUuid;
}
