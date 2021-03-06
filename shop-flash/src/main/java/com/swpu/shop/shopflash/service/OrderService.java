package com.swpu.shop.shopflash.service;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.entity.NewBeeMallOrderItem;
import com.swpu.shop.shopflash.mapper.OrderDao;
import com.swpu.shop.shopflash.redis.OrderKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import com.swpu.shop.shopflash.vo.FlashOrderDetailVo;
import com.swpu.shop.shopflash.vo.FlashSaleOrder;
import com.swpu.shop.util.SnowflakeIdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


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
    @Autowired
    private UserService userService;
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
    public NewBeeMallOrderItem createOrder(MallUser user, FlashGoodsVo goodsVo) {
        FlashSaleOrder flashSaleOrder=new FlashSaleOrder();
        //生成订单号
        //机器id为1.数据id为1 这是秒杀系统，后期可以放在配置文件中
        SnowflakeIdWorker snowflakeIdWorker = new SnowflakeIdWorker(1L, 1L);
        flashSaleOrder.setOrderId(snowflakeIdWorker.nextId());
        flashSaleOrder.setOrderTime(new Date());
        flashSaleOrder.setGoodsId(goodsVo.getGoodsId());
        flashSaleOrder.setUserId(user.getUserId());
        //默认设置为1.可以调整
        flashSaleOrder.setOrderStatus(1);
        flashSaleOrder.setPayType(1);
        flashSaleOrder.setTotalPrice(goodsVo.getFlashPrice());
        orderDao.flashOrder(flashSaleOrder);
        //插入redis
        boolean set = redisService.set(OrderKey.getMiaoshaOrderByUidGid, "" + user.getUserId() + goodsVo.getGoodsId(), flashSaleOrder);
        //下普通订单
        NewBeeMallOrderItem orderItem = new NewBeeMallOrderItem();
        orderItem.setGoodsId(flashSaleOrder.getGoodsId());
        orderItem.setGoodsCoverImg(goodsVo.getGoodsCoverImg());
        orderItem.setCreateTime(new Date());
        orderItem.setSellingPrice(goodsVo.getFlashPrice().intValue());
        orderItem.setGoodsCount(1);
        orderItem.setGoodsName(goodsVo.getGoodsName());
        orderItem.setOrderId(flashSaleOrder.getOrderId());
        orderDao.insertOrderItem(orderItem);
        return orderItem;
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
