package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 搜索列表页商品VO
 * @author GODV
 */
@Data
public class NewBeeMallSearchGoodsVO implements Serializable {

    private Long goodsId;

    private String goodsName;

    private String goodsIntro;

    private String goodsCoverImg;

    private Integer sellingPrice;

}
