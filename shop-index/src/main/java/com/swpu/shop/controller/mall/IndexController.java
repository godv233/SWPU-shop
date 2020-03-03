package com.swpu.shop.controller.mall;

import com.swpu.shop.common.Constants;
import com.swpu.shop.common.IndexConfigTypeEnum;
import com.swpu.shop.controller.vo.NewBeeMallIndexCarouselVO;
import com.swpu.shop.controller.vo.NewBeeMallIndexCategoryVO;
import com.swpu.shop.controller.vo.NewBeeMallIndexConfigGoodsVO;
import com.swpu.shop.service.NewBeeMallCarouselService;
import com.swpu.shop.service.NewBeeMallCategoryService;
import com.swpu.shop.service.NewBeeMallIndexConfigService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Resource
    private NewBeeMallCarouselService newBeeMallCarouselService;

    @Resource
    private NewBeeMallIndexConfigService newBeeMallIndexConfigService;

    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    /**
     * 首页展示
     * @param request
     * @return
     */
    @GetMapping({"/index", "/", "/index.html"})
    public String indexPage(HttpServletRequest request) {
        List<NewBeeMallIndexCategoryVO> categories = newBeeMallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            return "error/error_5xx";
        }
        List<NewBeeMallIndexCarouselVO> carousels = newBeeMallCarouselService.getCarouselsForIndex(Constants.INDEX_CAROUSEL_NUMBER);
        List<NewBeeMallIndexConfigGoodsVO> hotGoods = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_HOT.getType(), Constants.INDEX_GOODS_HOT_NUMBER);
        List<NewBeeMallIndexConfigGoodsVO> newGoods = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_NEW.getType(), Constants.INDEX_GOODS_NEW_NUMBER);
        List<NewBeeMallIndexConfigGoodsVO> recommendGoods = newBeeMallIndexConfigService.getConfigGoodsesForIndex(IndexConfigTypeEnum.INDEX_GOODS_RECOMMOND.getType(), Constants.INDEX_GOODS_RECOMMOND_NUMBER);
        request.setAttribute("categories", categories);//分类数据
        request.setAttribute("carousels", carousels);//轮播图
        request.setAttribute("hotGoodses", hotGoods);//热销商品
        request.setAttribute("newGoodses", newGoods);//新品
        request.setAttribute("recommendGoodses", recommendGoods);//推荐商品
        return "mall/index";
    }
}
