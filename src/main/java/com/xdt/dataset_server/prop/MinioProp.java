package com.xdt.dataset_server.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author XDT
 * @ClassName MinioProp
 * @Description: TODO
 * @Date 2023/2/28 11:08
 **/
@Data
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioProp {
    /**
     * 连接地址
     */
    private String endpoint;
    /**
     * 用户名
     */
    private String accessKey;
    /**
     * 密码
     */
    private String secretKey;
}
