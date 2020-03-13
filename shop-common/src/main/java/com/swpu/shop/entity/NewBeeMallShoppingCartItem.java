package com.swpu.shop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author GODV
 */
@Data
public class NewBeeMallShoppingCartItem {
    private Long cartItemId;

    private Long userId;

    private Long goodsId;

    private Integer goodsCount;

    private Byte isDeleted;

    private Date createTime;

    private Date updateTime;
}