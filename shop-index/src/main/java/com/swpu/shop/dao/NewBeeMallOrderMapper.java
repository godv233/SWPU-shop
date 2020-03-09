package com.swpu.shop.dao;

import com.swpu.shop.controller.vo.NewBeeMallOrderItemVO;
import com.swpu.shop.controller.vo.NewBeeMallOrderListVO;
import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface NewBeeMallOrderMapper {
    /**
     * 查找订单
     * @param orderNo
     * @return
     */
    NewBeeMallOrder selectByOrderNo(String orderNo);

    /**
     * 总数
     * @param pageUtil
     * @return
     */
    int getTotalNewBeeMallOrders(PageQueryUtil pageUtil);

    /**
     * 秒杀总数
     *
     * @param pageUtil
     * @return
     */
    int getTotalFlashOrders(PageQueryUtil pageUtil);

    /**
     * 列表
     * @param pageUtil
     * @return
     */
    List<NewBeeMallOrder> findNewBeeMallOrderList(PageQueryUtil pageUtil);

    /**
     * 秒杀订单列表
     *
     * @param pageUtil
     * @return
     */
    List<NewBeeMallOrderListVO> findFlashOrderList(PageQueryUtil pageUtil);
    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(NewBeeMallOrder record);

    /**
     * 关闭订单
     * @param orderIds
     * @param orderStatus
     * @return
     */
    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(NewBeeMallOrder record);

    /**
     * 查询秒杀订单详情
     *
     * @param orderNo
     * @return
     */
    List<NewBeeMallOrderItemVO> selectOrderItem(long orderNo);
}