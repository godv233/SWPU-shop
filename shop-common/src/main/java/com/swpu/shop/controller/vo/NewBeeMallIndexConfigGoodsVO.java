package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页配置商品VO
 * @author GODV
 */
@Data
public class NewBeeMallIndexConfigGoodsVO implements Serializable {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;

    private String tag;
}
