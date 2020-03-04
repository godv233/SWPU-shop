package com.swpu.shop.shopflash.config;

import com.swpu.shop.shopflash.redis.GoodsKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.service.GoodsService;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 在Spring Boot程序启动之后调用
 * redis预加载  @Order(value = 1) 表示执行顺序
 * @author GODV
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/4 21:19
 */
@Component
@Order(value = 1)
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        redisInit();
    }
    /**
     * 预加载库存
     */
    public void redisInit(){
        List<FlashGoodsVo> list = goodsService.goodsVoList();
        if (list != null && !list.isEmpty()) {
            //加入缓存
            for (FlashGoodsVo goods : list) {
                redisService.set(GoodsKey.getGoodsStock, "" + goods.getGoodsId(), goods.getStockCount());
            }
        }
    }
}
