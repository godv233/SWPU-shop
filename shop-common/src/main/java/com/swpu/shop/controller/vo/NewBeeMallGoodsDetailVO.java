package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 商品详情页VO
 * @author GODV
 */
@Data
public class NewBeeMallGoodsDetailVO implements Serializable {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private String[] goodsCarouselList;

    private Integer sellingPrice;

    private Integer originalPrice;

    private String goodsDetailContent;
}
