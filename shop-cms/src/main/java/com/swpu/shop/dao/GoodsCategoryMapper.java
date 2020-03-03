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
     * 删除
     * @param categoryId
     * @return
     */
    int deleteByPrimaryKey(Long categoryId);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(GoodsCategory record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(GoodsCategory record);

    /**
     * 查询
     * @param categoryId
     * @return
     */
    GoodsCategory selectByPrimaryKey(Long categoryId);

    /**
     * 查询
     * @param categoryLevel
     * @param categoryName
     * @return
     */
    GoodsCategory selectByLevelAndName(@Param("categoryLevel") Byte categoryLevel, @Param("categoryName") String categoryName);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(GoodsCategory record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(GoodsCategory record);

    /**
     * 查询列表
     * @param pageUtil
     * @return
     */
    List<GoodsCategory> findGoodsCategoryList(PageQueryUtil pageUtil);

    /**
     * 总数
     * @param pageUtil
     * @return
     */
    int getTotalGoodsCategories(PageQueryUtil pageUtil);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    /**
     * 层级列表查询
     * @param parentIds
     * @param categoryLevel
     * @param number
     * @return
     */
    List<GoodsCategory> selectByLevelAndParentIdsAndNumber(@Param("parentIds") List<Long> parentIds, @Param("categoryLevel") int categoryLevel, @Param("number") int number);
}