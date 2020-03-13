package com.swpu.shop.shopflash.rabbitmq;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.config.MqConfig;
import com.swpu.shop.shopflash.service.FlashSaleService;
import com.swpu.shop.shopflash.service.GoodsService;
import com.swpu.shop.shopflash.service.OrderService;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import com.swpu.shop.shopflash.vo.FlashSaleOrder;
import com.swpu.shop.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 消息队列的消费者
 * @author 曾伟
 * @date 2019/11/26 15:07
 */
@Service
public class MqReceiver {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private FlashSaleService flashSaleService;


    private static Logger logger = LoggerFactory.getLogger(MqConfig.class);

    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void receive(String message) throws IOException {
        logger.info(message);
        SaleMessage saleMessage = (SaleMessage) JsonUtil.jsonToObj(SaleMessage.class,message);
        MallUser user = saleMessage.getUser();
        long goodsId = saleMessage.getGoodsId();
        //真正的下单操作

        //判断商品库存
        FlashGoodsVo goodsVo = goodsService.goodsVoById(goodsId);
        if (goodsVo != null) {
            int stock = goodsVo.getStockCount();
            if (stock <= 0) {
                //库存不足，秒杀失败
                return;
            }
        }
        //判断是否已经秒杀到了
        FlashSaleOrder order = orderService.orderByUserIdGoodsId(user.getUserId(), goodsId);
        if (order != null) {
            //不能重复秒杀
            return;
        }
        //下单，需要事务控制，写在一个service里，使用事务控制。
        //减库存
        //下订单
        //写入秒杀订单
        flashSaleService.flash(user, goodsVo);
    }
}
