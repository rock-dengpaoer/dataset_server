package com.xdt.dataset_server.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author XDT
 * @ClassName TokenInfo
 * @Description: TODO
 * @Date 2023/2/28 9:15
 **/
@Data
public class TokenInfo {
     private String UserUuid;
     private String UserName;
     private String Token;
     private Date CreateTime;
     private long Expiration;
     private Date InvalidTime;
     private Date IssuedTime;
}
