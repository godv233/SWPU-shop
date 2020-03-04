package com.swpu.shop.shopflash.mapper;

import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.shopflash.vo.FlashSaleOrder;
import org.apache.ibatis.annotations.*;

/**
 * 订单
 * @author 曾伟
 * @date 2019/11/21 22:12
 */
@Mapper
public interface OrderDao {
    /**
     *订单
     * @param userId
     * @param goodsId
     * @return
     */
    @Select("select * from miaosha_order where user_id=#{userId} and goods_id = #{goodsId}")
    FlashSaleOrder getOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    /**
     * 插入订单
     * @param orderInfo
     * @return
     */
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(NewBeeMallOrder orderInfo);

    /**
     * 插入秒杀订单
     * @param flashSaleOrder
     * @return
     */
    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int flashOrder(FlashSaleOrder flashSaleOrder);

    /**
     * id得到订单
     * @param orderId
     * @return
     */
    @Select("select * from order_info where id = #{orderId}")
    NewBeeMallOrder getOrderById(@Param("orderId") long orderId);
}
