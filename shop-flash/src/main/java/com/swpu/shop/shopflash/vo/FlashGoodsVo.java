package com.swpu.shop.shopflash.vo;

import com.swpu.shop.controller.vo.NewBeeMallIndexConfigGoodsVO;
import lombok.Data;

import java.util.Date;

/**
 * 商品
 * @author 曾伟
 * @date 2019/11/21 20:04
 */
@Data
public class FlashGoodsVo  extends NewBeeMallIndexConfigGoodsVO {
    private Double flashPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
