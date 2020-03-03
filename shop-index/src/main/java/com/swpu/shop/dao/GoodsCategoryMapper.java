package com.swpu.shop.dao;

import com.swpu.shop.entity.GoodsCategory;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface GoodsCategoryMapper {
    /**
     *获得层级目录
     * @param parentIds
     * @param categoryLevel
     * @param number
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds,
                                                           @Param("categoryLevel") int categoryLevel,
                                                              @Param("number") int number);

    /**
     * categoryId得到目录
     * @param categoryId
     * @return
     */
    GoodsCategory selectByPrimaryKey(Long categoryId);
}