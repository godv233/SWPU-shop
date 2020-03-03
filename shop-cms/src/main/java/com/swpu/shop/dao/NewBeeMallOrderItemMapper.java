package com.swpu.shop.dao;

import com.swpu.shop.entity.NewBeeMallOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface NewBeeMallOrderItemMapper {

    /**
     * 根据订单id获取订单项列表
     *
     * @param orderId
     * @return
     */
    List<NewBeeMallOrderItem> selectByOrderId(Long orderId);

    int updateByPrimaryKeySelective(NewBeeMallOrderItem record);

    int updateByPrimaryKey(NewBeeMallOrderItem record);
}