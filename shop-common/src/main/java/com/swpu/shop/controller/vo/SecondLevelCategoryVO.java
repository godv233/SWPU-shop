package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO(第二级)
 * @author GODV
 */
@Data
public class SecondLevelCategoryVO implements Serializable {

    private Long categoryId;

    private Long parentId;

    private Byte categoryLevel;

    private String categoryName;

    private List<ThirdLevelCategoryVO> thirdLevelCategoryVOS;
}
