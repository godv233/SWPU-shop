package com.swpu.shop.dao;

import com.swpu.shop.entity.Carousel;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface CarouselMapper {
    /**
     * 删除
     * @param carouselId
     * @return
     */
    int deleteByPrimaryKey(Integer carouselId);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Carousel record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(Carousel record);

    /**
     * 查询
     * @param carouselId
     * @return
     */
    Carousel selectByPrimaryKey(Integer carouselId);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Carousel record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Carousel record);

    /**
     * 查询列表
     * @param pageUtil
     * @return
     */
    List<Carousel> findCarouselList(PageQueryUtil pageUtil);

    /**
     * 总数
     * @param pageUtil
     * @return
     */
    int getTotalCarousels(PageQueryUtil pageUtil);

    /**
     * 批量删除
     * @param ids
     * @return
     */
    int deleteBatch(Integer[] ids);

    /**
     * 查询列表
     * @param number
     * @return
     */
    List<Carousel> findCarouselsByNum(@Param("number") int number);
}