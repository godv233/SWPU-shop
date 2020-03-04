package com.swpu.shop.shopflash.service;

import com.swpu.shop.shopflash.mapper.FlashGoodsDao;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品service
 * @author 曾伟
 * @date 2019/11/21 20:00
 */
@Service
public class GoodsService {
    @Autowired
    private FlashGoodsDao goodsDao;

    /**
     * 列表
     * @return
     */
    public List<FlashGoodsVo> goodsVoList(){
        return  goodsDao.getGoodsVoList();
    }

    /**
     * 根据id得到goods
    * @param goodsId
     * @return
     */
    public FlashGoodsVo goodsVoById(long goodsId) {
        return goodsDao.getGoodsVoById(goodsId);
    }

    /**
     * 减少库存
     */
    public boolean reduceStock(FlashGoodsVo goodsVo) {
        int result = goodsDao.reduceStock(goodsVo.getGoodsId());
        return result>0;
    }
}
