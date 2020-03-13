package com.swpu.shop.controller.mall;

import com.swpu.shop.common.Constants;
import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.controller.vo.NewBeeMallLoginVO;
import com.swpu.shop.controller.vo.NewBeeMallUserVO;
import com.swpu.shop.entity.MallUser;
import com.swpu.shop.redis.RedisService;
import com.swpu.shop.redis.UserKey;
import com.swpu.shop.service.NewBeeMallUserService;
import com.swpu.shop.util.CookieUtil;
import com.swpu.shop.util.MD5Util;
import com.swpu.shop.util.Result;
import com.swpu.shop.util.ResultGenerator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 商城用户相关控制器
 * @author GODV
 */
@Controller
public class PersonalController {

    @Resource
    private NewBeeMallUserService newBeeMallUserService;
    @Autowired
    private RedisService redisService;

    /**
     * 在session中取出个人信息
     * @param request
     * @return
     */
    @GetMapping("/personal")
    public String personalPage(HttpServletRequest request) {
        request.setAttribute("path", "personal");
        return "mall/personal";
    }

    /**
     * 退出登录
     * @param httpSession
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,HttpSession httpSession) {
        //清除session
        httpSession.removeAttribute(Constants.MALL_USER_SESSION_KEY);
        //清除cookie并删除redis中的值
        //两种情况得到token
        String paramToken = request.getParameter(Constants.INDEX_TOKEN);
        String cookieToken = CookieUtil.getCookieValue(request, Constants.INDEX_TOKEN);
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        redisService.delete(UserKey.token,token);

        return "mall/login";
    }

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping({"/login", "login.html"})
    public String loginPage() {
        return "mall/login";
    }

    /**
     * 跳转注册页面
     * @return
     */
    @GetMapping({"/register", "register.html"})
    public String registerPage() {
        return "mall/register";
    }

    /**
     * 验证登录的逻辑
     * @param loginVO
     * @param httpSession
     * @return
     */
    @PostMapping("/login")
    @ResponseBody
    public Result login(NewBeeMallLoginVO loginVO, HttpServletResponse response,
                        HttpSession httpSession) {
        Result result = serverCheck(loginVO, httpSession);
        //校验不通过
        if (result != null) {
            return result;
        }
        //todo 清verifyCode
        String loginResult = newBeeMallUserService.login(loginVO.getLoginName(), MD5Util.MD5Encode(loginVO.getPassword(), "UTF-8"), httpSession,response);
        //登录成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(loginResult)) {

            return ResultGenerator.genSuccessResult();
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);
    }

    /**
     * 注册的逻辑
     * @param loginVO
     * @param httpSession
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public Result register(NewBeeMallLoginVO loginVO,
                           HttpSession httpSession) {
        Result result = serverCheck(loginVO, httpSession);
        //校验不通过
        if (result != null) {
            return result;
        }
        //todo 清verifyCode
        String registerResult = newBeeMallUserService.register(loginVO.getLoginName(), loginVO.getPassword());
        //注册成功
        if (ServiceResultEnum.SUCCESS.getResult().equals(registerResult)) {
            return ResultGenerator.genSuccessResult();
        }
        //注册失败
        return ResultGenerator.genFailResult(registerResult);
    }

    /**
     * 更新个人信息
     * @param mallUser
     * @param httpSession
     * @return
     */
    @PostMapping("/personal/updateInfo")
    @ResponseBody
    public Result updateInfo(@RequestBody MallUser mallUser, HttpSession httpSession) {
        NewBeeMallUserVO mallUserTemp = newBeeMallUserService.updateUserInfo(mallUser,httpSession);
        if (mallUserTemp == null) {
            Result result = ResultGenerator.genFailResult("修改失败");
            return result;
        } else {
            //返回成功
            Result result = ResultGenerator.genSuccessResult();
            return result;
        }
    }

    /**
     * 后台数据合法性校验
     * @param loginVO
     * @param httpSession
     * @return
     */
    public Result serverCheck(NewBeeMallLoginVO loginVO,HttpSession httpSession){
        if (StringUtils.isEmpty(loginVO.getLoginName())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_NULL.getResult());
        }
        if (StringUtils.isEmpty(loginVO.getPassword())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_PASSWORD_NULL.getResult());
        }
        if (StringUtils.isEmpty(loginVO.getVerifyCode())) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_NULL.getResult());
        }
        String kaptchaCode = httpSession.getAttribute(Constants.MALL_VERIFY_CODE_KEY) + "";
        if (StringUtils.isEmpty(kaptchaCode) || !loginVO.getVerifyCode().equals(kaptchaCode)) {
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_VERIFY_CODE_ERROR.getResult());
        }
        return null;
    }
}
