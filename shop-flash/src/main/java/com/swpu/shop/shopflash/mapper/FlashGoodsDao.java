package com.swpu.shop.shopflash.mapper;

import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 商品的数据库相关
 *
 * @author 曾伟
 * @date 2019/11/21 20:01
 */
@Mapper
public interface FlashGoodsDao {
    /**
     * 商品列表
     *
     * @return
     */
    @Select("select g.*,mg.stock_count ,mg.start_date,mg.end_date,mg.flash_price from flash_goods mg " +
            "left join tb_newbee_mall_goods_info g on mg.goods_id=g.goods_id")
    List<FlashGoodsVo> getGoodsVoList();

    /**
     * id得到商品
     *
     * @param goodsId
     * @return
     */
    @Select("SELECT g.*, mg.stock_count,mg.start_date, mg.end_date, mg.flash_price FROM flash_goods mg " +
            "LEFT JOIN tb_newbee_mall_goods_info g ON mg.goods_id = g.goods_id WHERE g.goods_id =#{goodsId}")
    FlashGoodsVo getGoodsVoById(@Param("goodsId") long goodsId);

    /**
     * 减库存
     *
     * @param id
     * @return
     */
    @Update("update flash_goods set stock_count=stock_count-1 where goods_id=#{id} and stock_count>0")
    int reduceStock(long id);
}
