package com.swpu.shop.shopflash.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq的配置类
 *
 * @author 曾伟
 * @date 2019/11/26 15:07
 */
@Configuration
public class MqConfig {
    public static final String MIAOSHA_QUEUE = "sale.queue";

    /**
     *生成一个direct队列
     * @return
     */
    @Bean
    public Queue saleQueue() {
        return new Queue(MqConfig.MIAOSHA_QUEUE, true);
    }
}
