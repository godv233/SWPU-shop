package com.swpu.shop.service;

import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.dao.IndexConfigMapper;
import com.swpu.shop.dao.NewBeeMallGoodsMapper;
import com.swpu.shop.entity.IndexConfig;
import com.swpu.shop.entity.NewBeeMallGoods;
import com.swpu.shop.util.BeanUtil;
import com.swpu.shop.util.PageQueryUtil;
import com.swpu.shop.util.PageResult;
import com.swpu.shop.controller.vo.NewBeeMallIndexConfigGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author GODV
 */
@Service
public class NewBeeMallIndexConfigService{

    @Autowired
    private IndexConfigMapper indexConfigMapper;

    @Autowired
    private NewBeeMallGoodsMapper goodsMapper;

    /**
     * 分页查询
     * @param pageUtil
     * @return
     */
    public PageResult getConfigsPage(PageQueryUtil pageUtil) {
        List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigList(pageUtil);
        int total = indexConfigMapper.getTotalIndexConfigs(pageUtil);
        PageResult pageResult = new PageResult(indexConfigs, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    /**
     * 保存
     * @param indexConfig
     * @return
     */
    public String saveIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 更新
     * @param indexConfig
     * @return
     */
    public String updateIndexConfig(IndexConfig indexConfig) {
        //todo 判断是否存在该商品
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

//    public IndexConfig getIndexConfigById(Long id) {
//        return null;
//    }

    public Boolean deleteBatch(Long[] ids) {
        if (ids.length < 1) {
            return false;
        }
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }
}
