package com.swpu.shop.controller.admin;

import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.entity.Carousel;
import com.swpu.shop.service.NewBeeMallCarouselService;
import com.swpu.shop.util.PageQueryUtil;
import com.swpu.shop.util.Result;
import com.swpu.shop.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @author 13
 * @qq交流群 796794009
 * @email 2449207463@qq.com
 * @link https://github.com/newbee-ltd
 */
@Controller
@RequestMapping("/admin")
public class NewBeeMallCarouselController {

    @Resource
    NewBeeMallCarouselService newBeeMallCarouselService;

    /**
     * 跳转页面
     * @param request
     * @return
     */
    @GetMapping("/carousels")
    public String carouselPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_carousel");
        return "admin/newbee_mall_carousel";
    }

    /**
     * 轮播图列表
     */
    @RequestMapping(value = "/carousels/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallCarouselService.getCarouselPage(pageUtil));
    }

    /**
     * 添加轮播图
     */
    @RequestMapping(value = "/carousels/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody Carousel carousel) {
        if (StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallCarouselService.saveCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改轮播图
     */
    @RequestMapping(value = "/carousels/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Carousel carousel) {
        if (Objects.isNull(carousel.getCarouselId())
                || StringUtils.isEmpty(carousel.getCarouselUrl())
                || Objects.isNull(carousel.getCarouselRank())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallCarouselService.updateCarousel(carousel);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 轮播图详情 哈哈
     */
    @GetMapping("/carousels/info/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Integer id) {
        Carousel carousel = newBeeMallCarouselService.getCarouselById(id);
        if (carousel == null) {
            return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(carousel);
    }

    /**
     * 删除轮播图
     */
    @RequestMapping(value = "/carousels/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Integer[] ids) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (newBeeMallCarouselService.deleteBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

}