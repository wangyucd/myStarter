package com.mystarter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 应用启动类
 *
 * @author wangyu
 * @date 2026-02-18 11:40:00
 */
@SpringBootApplication
@MapperScan({"com.mystarter.user.infra", "com.mystarter.product.infra"})
@EnableFeignClients
public class MystarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(MystarterApplication.class, args);
    }
}
