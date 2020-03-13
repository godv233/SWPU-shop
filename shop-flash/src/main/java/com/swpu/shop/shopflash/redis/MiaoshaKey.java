package com.swpu.shop.shopflash.redis;

/**
 * redis的秒杀相关的key
 * @author GODV
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaKey goodsOver = new MiaoshaKey(-1, "gl:");
    public static MiaoshaKey miaoshaPath = new MiaoshaKey(60, "path:");
    public static MiaoshaKey verifyCode = new MiaoshaKey(300, "vc:");

}
