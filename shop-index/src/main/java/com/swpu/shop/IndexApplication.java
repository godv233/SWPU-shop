package com.swpu.shop;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/3 11:57
 */
@MapperScan("com.swpu.shop.dao")
@SpringBootApplication
public class IndexApplication {
    public static void main(String[] args) {
        SpringApplication.run(IndexApplication.class,args);
    }
}
