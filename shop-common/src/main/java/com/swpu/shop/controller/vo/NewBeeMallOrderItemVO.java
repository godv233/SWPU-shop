package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单详情页页面订单项VO
 * @author GODV
 */
@Data
public class NewBeeMallOrderItemVO implements Serializable {

    private Long goodsId;

    private Integer goodsCount;

    private String goodsName;

    private String goodsCoverImg;

    private Integer sellingPrice;

}
