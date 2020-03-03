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
    /**
     * ids查找
     * @param goodsIds
     * @return
     */
    List<NewBeeMallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    /**
     * 更新
     * @param stockNumDTOS
     * @return
     */
    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    /**
     * 查找
     * @param goodsId
     * @return
     */
    NewBeeMallGoods selectByPrimaryKey(Long goodsId);

    /**
     * 搜索查找
     * @param pageUtil
     * @return
     */
    List<NewBeeMallGoods> findNewBeeMallGoodsListBySearch(PageQueryUtil pageUtil);

    /**
     * 得到搜索的总数
     * @param pageUtil
     * @return
     */
    int getTotalNewBeeMallGoodsBySearch(PageQueryUtil pageUtil);



}