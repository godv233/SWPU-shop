package com.swpu.shop.service;

import com.swpu.shop.common.Constants;
import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.controller.vo.NewBeeMallUserVO;
import com.swpu.shop.dao.MallUserMapper;
import com.swpu.shop.entity.MallUser;
import com.swpu.shop.redis.RedisService;
import com.swpu.shop.redis.UserKey;
import com.swpu.shop.util.BeanUtil;
import com.swpu.shop.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.UUID;

/**
 * @author GODV
 */
@Service
public class NewBeeMallUserService{

    @Autowired
    private MallUserMapper mallUserMapper;
    @Autowired
    private RedisService redisService;

    /**
     * 登录的逻辑
     * @param loginName
     * @param passwordMD5
     * @param httpSession
     * @return
     */
    public String login(String loginName, String passwordMD5, HttpSession httpSession, HttpServletResponse response) {
        MallUser user = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordMD5);
        if (user != null && httpSession != null) {

            if (user.getLockedFlag() == 1) {
                return ServiceResultEnum.LOGIN_USER_LOCKED.getResult();
            }

            //将用户的信息存放在redis中,并且将token设置进cookie
            String token = UUID.randomUUID().toString().replace("-", "");
            redisService.set(UserKey.token,token,user);
            Cookie cookie=new Cookie("token",token);
            cookie.setMaxAge(UserKey.token.expireSeconds());
            response.addCookie(cookie);
            //昵称太长 影响页面展示
            if (user.getNickName() != null && user.getNickName().length() > 7) {
                String tempNickName = user.getNickName().substring(0, 7) + "..";
                user.setNickName(tempNickName);
            }
            NewBeeMallUserVO newBeeMallUserVO = new NewBeeMallUserVO();
            BeanUtil.copyProperties(user, newBeeMallUserVO);
            //设置购物车中的数量
            httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, newBeeMallUserVO);
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }
    /**
     * 注册逻辑
     * @param loginName
     * @param password
     * @return
     */
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser = new MallUser();
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        String passwordMD5 = MD5Util.MD5Encode(password, "UTF-8");
        registerUser.setPasswordMd5(passwordMD5);
        if (mallUserMapper.insertSelective(registerUser) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }
    /**
     * 更新个人信息
     * @param mallUser
     * @param httpSession
     * @return
     */
    public NewBeeMallUserVO updateUserInfo(MallUser mallUser, HttpSession httpSession) {
        MallUser user = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
        if (user != null) {
            user.setNickName(mallUser.getNickName());
            user.setAddress(mallUser.getAddress());
            user.setIntroduceSign(mallUser.getIntroduceSign());
            if (mallUserMapper.updateByPrimaryKeySelective(user) > 0) {
                NewBeeMallUserVO newBeeMallUserVO = new NewBeeMallUserVO();
                user = mallUserMapper.selectByPrimaryKey(mallUser.getUserId());
                BeanUtil.copyProperties(user, newBeeMallUserVO);
                httpSession.setAttribute(Constants.MALL_USER_SESSION_KEY, newBeeMallUserVO);
                return newBeeMallUserVO;
            }
        }
        return null;
    }

}
