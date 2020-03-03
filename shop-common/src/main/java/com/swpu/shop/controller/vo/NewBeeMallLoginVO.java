package com.swpu.shop.controller.vo;

import lombok.Data;

/**
 * @author 曾伟 zengwei233@126.com
 * @date 2020/3/1 20:40
 */
@Data
public class NewBeeMallLoginVO {

    private String loginName;
    private String password;
    //验证码
    private String verifyCode;
}
