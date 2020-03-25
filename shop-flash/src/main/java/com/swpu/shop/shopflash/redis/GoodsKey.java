package com.swpu.shop.shopflash.redis;

/**
 * 商品key
 * @author GODV
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static GoodsKey getGoodsList = new GoodsKey(30, "gl:");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd:");
    public static GoodsKey getGoodsStock = new GoodsKey(-1, "gs:");
}
