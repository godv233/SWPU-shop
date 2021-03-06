package com.swpu.shop.shopflash.mapper;

import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.entity.NewBeeMallOrderItem;
import com.swpu.shop.shopflash.common.Result;
import com.swpu.shop.shopflash.vo.FlashOrderDetailVo;
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
    @Select("select * from flash_order where user_id=#{userId} and goods_id = #{goodsId}")
    FlashSaleOrder getOrderByUserIdGoodsId(@Param("userId") Long userId, @Param("goodsId") long goodsId);

    /**
     * 插入订单
     * @param orderInfo
     * @return
     */
    @Insert("insert into tb_newbee_mall_order(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn = "id", keyProperty = "id", resultType = long.class, before = false, statement = "select last_insert_id()")
    long insert(NewBeeMallOrder orderInfo);

    /**
     * 插入秒杀订单
     * @param flashSaleOrder
     * @return
     */
    @Insert("insert into flash_order (order_id,user_id,goods_id,order_time,order_status,pay_type,total_price)" +
            "values(#{orderId},#{userId}, #{goodsId},#{orderTime},#{orderStatus},#{payType},#{totalPrice})")
    int flashOrder(FlashSaleOrder flashSaleOrder);
    @Select("select *\n" +
            "FROM flash_order\n" +
            "JOIN tb_newbee_mall_goods_info info ON flash_order.goods_id=info.goods_id\n" +
            "where flash_order.order_id=#{orderId}")
    FlashOrderDetailVo getOrderDetailById(long orderId);

    @Select("select * from flash_order where order_id=#{orderId}")
    FlashSaleOrder getOrderByOrderId(@Param("orderId") Long orderId);

    @Insert("insert into tb_newbee_mall_order_item (order_id,goods_id,goods_name,goods_cover_img,selling_price,goods_count,create_time)" +
            "values(#{orderId},#{goodsId}, #{goodsName},#{goodsCoverImg},#{sellingPrice},#{goodsCount},#{createTime})")
    @SelectKey(keyColumn = "order_item_id", keyProperty = "orderItemId", resultType = long.class, before = false, statement = "select last_insert_id()")
    int insertOrderItem(NewBeeMallOrderItem orderItem);
}
