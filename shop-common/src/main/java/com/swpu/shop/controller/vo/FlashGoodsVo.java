package com.swpu.shop.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endDate;
}
