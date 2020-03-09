package com.swpu.shop.shopflash.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**秒杀订单
 * @author 曾伟
 * @date 2019/11/21 19:52
 */
@Data
public class FlashSaleOrder {
    private Long userId;
    private Long orderId;
    private Long goodsId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date orderTime;
}
