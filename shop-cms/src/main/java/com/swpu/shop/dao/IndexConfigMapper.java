package com.swpu.shop.dao;

import com.swpu.shop.entity.IndexConfig;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexConfigMapper {
    int deleteByPrimaryKey(Long configId);

    int insert(IndexConfig record);

    int insertSelective(IndexConfig record);

    IndexConfig selectByPrimaryKey(Long configId);

    int updateByPrimaryKeySelective(IndexConfig record);

    int updateByPrimaryKey(IndexConfig record);

    /**
     * 查询列表
     * @param pageUtil
     * @return
     */
    List<IndexConfig> findIndexConfigList(PageQueryUtil pageUtil);

    /**
     * 总数
     * @param pageUtil
     * @return
     */
    int getTotalIndexConfigs(PageQueryUtil pageUtil);

    int deleteBatch(Long[] ids);

}