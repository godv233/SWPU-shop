package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页分类数据VO(第三级)
 * @author GODV
 */
@Data
public class ThirdLevelCategoryVO implements Serializable {

    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;
}
