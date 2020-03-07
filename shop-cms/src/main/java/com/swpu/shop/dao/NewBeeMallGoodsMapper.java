package com.swpu.shop.dao;

import com.swpu.shop.controller.vo.FlashGoodsVo;
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

    /**
     * 秒杀商品列表查询
     * @param pageUtil
     * @return
     */
    List<FlashGoodsVo> findFlashGoodsList(PageQueryUtil pageUtil);

    /**
     * 批量删除秒杀goods
     * @param ids
     * @return
     */
    boolean deleteFlashBatch(Long[] ids);

    /**
     * 更新秒杀
     * @param record
     * @return
     */
    int updateFlashGoods(FlashGoodsVo record);

    /**
     * 查询ById
     * @param goodsId
     * @return
     */
    FlashGoodsVo selectFlashById(Long goodsId);
}