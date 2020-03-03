package com.swpu.shop.service;

import com.swpu.shop.dao.MallUserMapper;
import com.swpu.shop.entity.MallUser;
import com.swpu.shop.util.PageQueryUtil;
import com.swpu.shop.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;


/**
 * @author GODV
 */
@Service
public class NewBeeMallUserService {

    @Autowired
    private MallUserMapper mallUserMapper;

    public PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageUtil);
        int total = mallUserMapper.getTotalMallUsers(pageUtil);
        PageResult pageResult = new PageResult(mallUsers, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    public Boolean lockUsers(Integer[] ids, int lockStatus) {
        if (ids.length < 1) {
            return false;
        }
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }
}
