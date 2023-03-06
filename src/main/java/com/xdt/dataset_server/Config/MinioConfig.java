package com.xdt.dataset_server.Config;

import com.xdt.dataset_server.prop.MinioProp;
import io.minio.MinioClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author XDT
 * @ClassName MinioConfig
 * @Description: TODO
 * @Date 2023/2/28 11:19
 **/
@Configuration
@EnableConfigurationProperties(MinioProp.class)
public class MinioConfig {

    @Autowired
    private MinioProp minioProp;

    /**
     * 获取MinioClient
     */
    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(minioProp.getEndpoint())
                .credentials(minioProp.getAccessKey(), minioProp.getSecretKey())
                .build();
    }

}
