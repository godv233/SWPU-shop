package com.swpu.shop.controller.vo;

import com.swpu.shop.entity.GoodsCategory;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 搜索页面分类数据VO
 * @author GODV
 */
@Data
public class SearchPageCategoryVO implements Serializable {

    private String firstLevelCategoryName;

    private List<GoodsCategory> secondLevelCategoryList;

    private String secondLevelCategoryName;

    private List<GoodsCategory> thirdLevelCategoryList;

    private String currentCategoryName;

}
