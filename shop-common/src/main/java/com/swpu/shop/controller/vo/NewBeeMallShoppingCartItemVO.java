package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 购物车页面购物项VO
 * @author GODV
 */
@Data
public class NewBeeMallShoppingCartItemVO implements Serializable {

    private Long cartItemId;

    private Long goodsId;

    private Integer goodsCount;

    private String goodsName;

    private String goodsCoverImg;

    private Integer sellingPrice;
}
