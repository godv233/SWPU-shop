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
     * index展示轮播图
     * @param number
     * @return
     */
    List<Carousel> findCarouselsByNum(@Param("number") int number);
}