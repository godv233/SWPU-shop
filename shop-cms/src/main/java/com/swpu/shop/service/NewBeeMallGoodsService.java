package com.swpu.shop.service;

import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.controller.vo.FlashGoodsVo;
import com.swpu.shop.dao.NewBeeMallGoodsMapper;
import com.swpu.shop.entity.NewBeeMallGoods;
import com.swpu.shop.util.BeanUtil;
import com.swpu.shop.util.PageQueryUtil;
import com.swpu.shop.util.PageResult;
import com.swpu.shop.controller.vo.NewBeeMallSearchGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author GODV
 */
@Service
public class NewBeeMallGoodsService{

    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;

    /**
     * 分页查询列表
     * @param pageUtil
     * @return
     */
    public PageResult getNewBeeMallGoodsPage(PageQueryUtil pageUtil) {
        List<NewBeeMallGoods> goodsList = goodsMapper.findNewBeeMallGoodsList(pageUtil);
        int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 分页查询秒杀商品列表
     * @param pageUtil
     * @return
     */
    public PageResult getFlashGoodsPage(PageQueryUtil pageUtil) {
        List<FlashGoodsVo> goodsList = goodsMapper.findFlashGoodsList(pageUtil);
        int total = goodsMapper.getTotalNewBeeMallGoods(pageUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }


    /**
     * 保存
     * @param goods
     * @return
     */
    public String saveNewBeeMallGoods(NewBeeMallGoods goods) {
        if (goodsMapper.insertSelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 更新
     * @param goods
     * @return
     */
    public String updateNewBeeMallGoods(NewBeeMallGoods goods) {
        NewBeeMallGoods temp = goodsMapper.selectByPrimaryKey(goods.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        goods.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 更新秒杀商品
     * @param goods
     * @return
     */
    public String updateFlashGoods(FlashGoodsVo goods) {

        if (goodsMapper.updateFlashGoods(goods) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }




    /**
     * 查询
     * @param id
     * @return
     */
    public NewBeeMallGoods getNewBeeMallGoodsById(Long id) {
        return goodsMapper.selectByPrimaryKey(id);
    }

    /**
     * 批量更新商品状态
     * @param ids
     * @param sellStatus
     * @return
     */
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    /**
     * 批量删除秒杀商品
     * @param ids
     * @return
     */
    public boolean deleteFlashGoodsBatch(Long[] ids) {
        return goodsMapper.deleteFlashBatch(ids);
    }

    /**
     * 查询秒杀商品
     * @param goodsId
     * @return
     */
    public FlashGoodsVo getFlashGoodsById(Long goodsId) {
        return goodsMapper.selectFlashById(goodsId);
    }

    /**
     * 保存秒杀商品
     * @param goodsVo
     * @return
     */
    public String saveFlashGoods(FlashGoodsVo goodsVo) {
        if (goodsMapper.insertFlashGoods(goodsVo) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
}
