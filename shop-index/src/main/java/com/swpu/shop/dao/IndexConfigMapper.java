package com.swpu.shop.dao;

import com.swpu.shop.entity.IndexConfig;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface IndexConfigMapper {
    /**
     * 根据类型找到IndexConfig首页数据
     * @param configType
     * @param number
     * @return
     */
    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);

}