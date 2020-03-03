package com.swpu.shop.dao;

import com.swpu.shop.entity.NewBeeMallOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface NewBeeMallOrderItemMapper {
    /**
     * 根据订单ids获取订单项列表
     *
     * @param orderIds
     * @return
     */
    List<NewBeeMallOrderItem> selectByOrderIds(@Param("orderIds") List<Long> orderIds);
    /**
     * 批量insert订单项数据
     *
     * @param orderItems
     * @return
     */
    int insertBatch(@Param("orderItems") List<NewBeeMallOrderItem> orderItems);
    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<NewBeeMallOrderItem> selectByOrderId(Long orderId);



}