package com.swpu.shop.shopflash.service;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.shopflash.mapper.OrderDao;
import com.swpu.shop.shopflash.redis.OrderKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import com.swpu.shop.shopflash.vo.FlashOrderDetailVo;
import com.swpu.shop.shopflash.vo.FlashSaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 订单service
 * @author 曾伟
 * @date 2019/11/21 22:10
 */
@Service
public class OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private RedisService redisService;
    @Autowired
    private GoodsService goodsService;
    /**
     * 通过userid和goodsId得到订单
     * @param userId
     * @param goodsId
     * @return
     */
    public FlashSaleOrder orderByUserIdGoodsId(Long userId, long goodsId) {
        //判断数据库中时候已经有了订单。
//        return orderDao.getOrderByUserIdGoodsId(userId,goodsId);
        //将订单的信息存入redis。判断的时候直接访问redis而不是数据库。
        FlashSaleOrder order = (FlashSaleOrder) redisService.get(OrderKey.getMiaoshaOrderByUidGid, "" + userId + goodsId);
        return order;


    }

    /**
     * 下订单
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    public NewBeeMallOrder createOrder(MallUser user, FlashGoodsVo goodsVo) {
        //下普通订单
        NewBeeMallOrder orderInfo=new NewBeeMallOrder();
//        orderInfo.setCreateTime(new Date());
//        orderInfo.setUserAddress(user.getAddress());
//        orderInfo(goodsVo.getId());
//        orderInfo.setGoodsName(goodsVo.getGoodsName());
//        orderInfo.setGoodsPrice(goodsVo.getMiaoshaPrice());
//        //一般通过程序判断:这里直接写死
//        orderInfo.setOrderChannel(1);
//        orderInfo.setStatus(0);
//        orderInfo.setUserId(user.getId());
//        orderDao.insert(orderInfo);
        //秒杀订单
        FlashSaleOrder flashSaleOrder=new FlashSaleOrder();
        flashSaleOrder.setGoodsId(goodsVo.getGoodsId());
        flashSaleOrder.setUserId(user.getUserId());
        orderDao.flashOrder(flashSaleOrder);
        boolean set = redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getUserId() + goodsVo.getGoodsId(), flashSaleOrder);
        return orderInfo;
    }

    /**
     * 得到订单
     * @param orderId
     * @return
     */
    public FlashOrderDetailVo getOrderDetailById(long orderId) {
        FlashOrderDetailVo detailVo=new FlashOrderDetailVo();
        FlashSaleOrder order = orderDao.getOrderByOrderId(orderId);
        FlashGoodsVo goods= goodsService.goodsVoById(order.getGoodsId());
        detailVo.setOrder(order);
        detailVo.setGoods(goods);
        return detailVo;

    }
}
