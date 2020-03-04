package com.swpu.shop.shopflash.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swpu.shop.shopflash.config.MqConfig;
import com.swpu.shop.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 生产者
 * @author 曾伟
 * @date 2019/11/26 15:06
 */
@Service
public class MqSender {
    private static Logger logger = LoggerFactory.getLogger(MqConfig.class);
    @Autowired
    private AmqpTemplate amqpTemplate;
//    public void send(Object object){
//        amqpTemplate.convertAndSend(MqConfig.QUEUE,object);
//        logger.info("send");
//    }

    /**
     * 生产者
     * @param message
     */
    public void miaoshaMessage(SaleMessage message) throws JsonProcessingException {
        String msg= JsonUtil.objToJson(message);
        logger.info(msg);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE,msg);
    }
}
