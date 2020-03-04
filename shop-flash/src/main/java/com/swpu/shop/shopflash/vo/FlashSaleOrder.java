package com.swpu.shop.shopflash.vo;

import lombok.Data;

/**秒杀订单
 * @author 曾伟
 * @date 2019/11/21 19:52
 */
@Data
public class FlashSaleOrder {
    private Long id;
    private Long userId;
    private Long orderId;
    private Long goodsId;
}
