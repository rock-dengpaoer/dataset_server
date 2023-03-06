package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author XDT
 * @ClassName Object
 * @Description: TODO
 * @Date 2023/2/28 11:29
 **/
@Data
public class MinioObject {
    private String uuid;
    private String bucketName;
    private String objectName;
    private String type;
    private Double size;
    private Date uploadTime;
    private String userUuid;
    private String url;
}
