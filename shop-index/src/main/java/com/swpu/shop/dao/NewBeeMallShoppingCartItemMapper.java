package com.swpu.shop.dao;

import com.swpu.shop.entity.NewBeeMallShoppingCartItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface NewBeeMallShoppingCartItemMapper {
    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(List<Long> ids);

    /**
     * UserIdAndGoodsId查找
     * @param newBeeMallUserId
     * @param goodsId
     * @return
     */
    NewBeeMallShoppingCartItem selectByUserIdAndGoodsId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("goodsId") Long goodsId);

    /**
     * userid查找
     * @param newBeeMallUserId
     * @return
     */
    int selectCountByUserId(Long newBeeMallUserId);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(NewBeeMallShoppingCartItem record);

    /**
     * 查找
     * @param cartItemId
     * @return
     */
    NewBeeMallShoppingCartItem selectByPrimaryKey(Long cartItemId);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(NewBeeMallShoppingCartItem record);

    /**
     * 删除
     * @param cartItemId
     * @return
     */
    int deleteByPrimaryKey(Long cartItemId);

    /**
     * 查找
     * @param newBeeMallUserId
     * @param number
     * @return
     */
    List<NewBeeMallShoppingCartItem> selectByUserId(@Param("newBeeMallUserId") Long newBeeMallUserId, @Param("number") int number);

}