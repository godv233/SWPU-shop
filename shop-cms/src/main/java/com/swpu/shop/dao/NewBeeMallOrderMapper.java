package com.swpu.shop.dao;

import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface NewBeeMallOrderMapper {

    /**
     * id得到订单
     * @param orderId
     * @return
     */
    NewBeeMallOrder selectByPrimaryKey(Long orderId);


    /**
     * 更新订单
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(NewBeeMallOrder record);

    /**
     * 更新订单
     * @param record
     * @return
     */
    int updateByPrimaryKey(NewBeeMallOrder record);

    /**
     * 订单列表
     * @param pageUtil
     * @return
     */
    List<NewBeeMallOrder> findNewBeeMallOrderList(PageQueryUtil pageUtil);

    /**
     * 得到订单总数
     * @param pageUtil
     * @return
     */
    int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

    /**
     * 查询订单
     * @param orderIds
     * @return
     */
    List<NewBeeMallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);

    /**
     * 出库
     * @param orderIds
     * @return
     */
    int checkOut(@Param("orderIds") List<Long> orderIds);

    /**关闭订单
     * @param orderIds
     * @param orderStatus
     * @return
     */
    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    /**
     * 配货
     * @param asList
     * @return
     */
    int checkDone(@Param("orderIds") List<Long> asList);
}