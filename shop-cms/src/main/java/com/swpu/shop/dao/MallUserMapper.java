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
     * 查询列表
     * @param pageUtil
     * @return
     */
    List<MallUser> findMallUserList(PageQueryUtil pageUtil);

    /**
     * 总数
     * @param pageUtil
     * @return
     */
    int getTotalMallUsers(PageQueryUtil pageUtil);

    /**
     * 锁定user
     * @param ids
     * @param lockStatus
     * @return
     */
    int lockUserBatch(@Param("ids") Integer[] ids, @Param("lockStatus") int lockStatus);
}