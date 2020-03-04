package com.swpu.shop.shopflash.vo;

import lombok.Data;

/**
 * 订单详情
 * @author GODV
 */
@Data
public class FlashOrderDetailVo {
	private FlashGoodsVo goods;
	private FlashSaleOrder order;
}
