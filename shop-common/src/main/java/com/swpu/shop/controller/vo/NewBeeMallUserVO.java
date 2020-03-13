package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author GODV
 */
@Data
public class NewBeeMallUserVO implements Serializable {

    private Long userId;

    private String nickName;

    private String loginName;

    private String introduceSign;

    private String address;

    private Integer shopCartItemCount;

}
