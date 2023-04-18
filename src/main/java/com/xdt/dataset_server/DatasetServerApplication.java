package com.xdt.dataset_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class DatasetServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasetServerApplication.class, args);
    }

}
