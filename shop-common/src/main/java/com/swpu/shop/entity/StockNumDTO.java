package com.swpu.shop.entity;

import lombok.Data;

/**
 * 库存修改所需实体
 * @author GODV
 */
@Data
public class StockNumDTO {
    private Long goodsId;

    private Integer goodsCount;

}
