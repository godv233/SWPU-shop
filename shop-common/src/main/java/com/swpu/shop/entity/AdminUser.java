package com.swpu.shop.entity;

import lombok.Data;

/**
 * 用户
 *
 * @author GODV
 */
@Data
public class AdminUser {
    private Integer adminUserId;

    private String loginUserName;

    private String loginPassword;

    private String nickName;

    private Byte locked;
}