package com.swpu.shop.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 商品
 * @author 曾伟
 * @date 2019/11/21 20:04
 */
@Data
public class FlashGoodsVo extends NewBeeMallIndexConfigGoodsVO {
    private Double flashPrice;
    private Integer stockCount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endDate;
}
