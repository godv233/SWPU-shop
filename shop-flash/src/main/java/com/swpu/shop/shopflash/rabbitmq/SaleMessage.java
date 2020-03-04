package com.swpu.shop.shopflash.rabbitmq;

import com.swpu.shop.entity.MallUser;
import lombok.Data;

/**
 * 消息队列中存放的message对象
 * @author 曾伟
 * @date 2019/11/26 17:16
 */
@Data
public class SaleMessage {
    private MallUser user;
    private long goodsId;
}
