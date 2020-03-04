package com.swpu.shop.shopflash.redis;

/**
 * 订单相关的key
 */
public class OrderKey extends BasePrefix {
	private static final int ORDER_EXPIRE=3600*24;//一天

	public OrderKey(int expireSeconds,String prefix) {
		super(expireSeconds,prefix);
	}
	public static OrderKey getMiaoshaOrderByUidGid = new OrderKey(ORDER_EXPIRE,"order:");
}
