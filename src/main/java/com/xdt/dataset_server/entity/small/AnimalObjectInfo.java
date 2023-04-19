package com.xdt.dataset_server.entity.small;

import lombok.Data;

import java.util.Date;

@Data
public class AnimalObjectInfo {
    private String uuid;
    private String name;
    private String bucketName;
    private Date createTime;
    private String userUuid;
    private String url;
}
