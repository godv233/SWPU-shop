package com.swpu.shop.dao;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author GODV
 */
public interface MallUserMapper {
    /**
     * 查找账号和密码
     * @param loginName
     * @param password
     * @return
     */
    MallUser selectByLoginNameAndPasswd(@Param("loginName") String loginName, @Param("password") String password);

    /**
     * 根据name找user
     * @param loginName
     * @return
     */
    MallUser selectByLoginName(String loginName);

    /**
     * 插入
     * @param record
     * @return
     */
    int insertSelective(MallUser record);

    /**
     * 根据id找user
     * @param userId
     * @return
     */
    MallUser selectByPrimaryKey(Long userId);

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(MallUser record);

}