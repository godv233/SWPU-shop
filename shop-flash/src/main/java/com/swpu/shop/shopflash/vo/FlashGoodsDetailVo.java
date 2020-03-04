package com.swpu.shop.shopflash.vo;


import com.swpu.shop.entity.MallUser;
import lombok.Data;

/**
 * @author GODV
 */
@Data
public class FlashGoodsDetailVo {
    private int flashStatus = 0;
    private int remainSeconds = 0;
    private FlashGoodsVo goods;
    private MallUser user;
}
