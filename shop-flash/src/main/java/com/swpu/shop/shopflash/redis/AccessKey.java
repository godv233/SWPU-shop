package com.swpu.shop.shopflash.redis;

/**
 * redis的限流的key
 * @author GODV
 */
public class AccessKey extends BasePrefix{

	private AccessKey( int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}

	public static AccessKey withExpire(int expireSeconds) {
		return new AccessKey(expireSeconds, "access:");
	}

}
