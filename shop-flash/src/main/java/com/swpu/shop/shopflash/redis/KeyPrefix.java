package com.swpu.shop.shopflash.redis;

/**
 * key的接口
 */
public interface KeyPrefix {
	/**
	 * 过期时间
 	 * @return
	 */
	int expireSeconds();

	/**
	 * 前缀
	 * @return
	 */
	String getPrefix();
	
}
