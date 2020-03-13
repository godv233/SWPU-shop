package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO
 * @author GODV
 */
@Data
public class NewBeeMallIndexCategoryVO implements Serializable {

    private Long categoryId;

    private Byte categoryLevel;

    private String categoryName;

    private List<SecondLevelCategoryVO> secondLevelCategoryVOS;

}
