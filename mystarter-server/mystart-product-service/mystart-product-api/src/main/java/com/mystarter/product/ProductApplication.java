package com.mystarter.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 商品模块独立启动类（微服务模式）
 *
 * @author wangyu
 * @date 2026-02-18 22:20:00
 */
@SpringBootApplication
@MapperScan("com.mystarter.product.infra")
@EnableFeignClients(basePackages = "com.mystarter.*.api.feign")
public class ProductApplication {

    /**
     * 商品模块独立启动入口
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
