package com.swpu.shop.util;

import java.util.UUID;

/** UUID工具类
 * @author 曾伟
 * @date 2019/11/16 10:03
 */
public class UUIDUtils {
    public static String uuid(){
        /**
         * 去掉-的uuid
         */
        return UUID.randomUUID().toString().replace("-","");
    }
}
