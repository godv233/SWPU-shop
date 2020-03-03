package com.swpu.shop.dao;

import com.swpu.shop.entity.NewBeeMallGoods;
import com.swpu.shop.entity.StockNumDTO;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface NewBeeMallGoodsMapper {
    int deleteByPrimaryKey(Long goodsId);

    int insert(NewBeeMallGoods record);

    int insertSelective(NewBeeMallGoods record);

    /**
     * 查找
     * @param goodsId
     * @return
     */
    NewBeeMallGoods selectByPrimaryKey(Long goodsId);

    int updateByPrimaryKeySelective(NewBeeMallGoods record);

    int updateByPrimaryKey(NewBeeMallGoods record);

    /**
     * 列表查询
     * @param pageUtil
     * @return
     */
    List<NewBeeMallGoods> findNewBeeMallGoodsList(PageQueryUtil pageUtil);

    int getTotalNewBeeMallGoods(PageQueryUtil pageUtil);

    /**
     * 批量更新状态
     * @param orderIds
     * @param sellStatus
     * @return
     */
    int batchUpdateSellStatus(@Param("orderIds") Long[] orderIds, @Param("sellStatus") int sellStatus);

}