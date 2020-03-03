package com.swpu.shop.dao;

import com.swpu.shop.entity.AdminUser;
import org.apache.ibatis.annotations.Param;

/**
 * @author GODV
 */
public interface AdminUserMapper {
    /**
     * 插入
     * @param record
     * @return
     */
    int insert(AdminUser record);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(AdminUser record);

    /**
     * 登陆方法
     *
     * @param userName
     * @param password
     * @return
     */
    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    /**
     * 查找
     * @param adminUserId
     * @return
     */
    AdminUser selectByPrimaryKey(Integer adminUserId);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(AdminUser record);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(AdminUser record);
}