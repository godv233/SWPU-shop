package com.swpu.shop.controller.admin;

import com.swpu.shop.common.Constants;
import com.swpu.shop.common.NewBeeMallCategoryLevelEnum;
import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.controller.vo.FlashGoodsVo;
import com.swpu.shop.entity.GoodsCategory;
import com.swpu.shop.entity.NewBeeMallGoods;
import com.swpu.shop.service.NewBeeMallCategoryService;
import com.swpu.shop.service.NewBeeMallGoodsService;
import com.swpu.shop.util.PageQueryUtil;
import com.swpu.shop.util.Result;
import com.swpu.shop.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 商品管理
 * @author GODV
 */
@Controller
@RequestMapping("/admin")
public class NewBeeMallGoodsController {

    @Resource
    private NewBeeMallGoodsService newBeeMallGoodsService;
    @Resource
    private NewBeeMallCategoryService newBeeMallCategoryService;

    /**
     * 返回商品页面
     *
     * @param request
     * @return
     */
    @GetMapping("/goods")
    public String goodsPage(HttpServletRequest request) {
        request.setAttribute("path", "newbee_mall_goods");
        return "admin/newbee_mall_goods";
    }

    /**
     * 秒杀商品
     * @param request
     * @return
     */
    @GetMapping("/flashGoods")
    public String flashGoodsPage(HttpServletRequest request){
        request.setAttribute("path","newbee_mall_flash_goods");
        return "admin/newbee_mall_flash_goods";
    }

    /**
     * 商品更改。回显数据
     * @param request
     * @return
     */
    @GetMapping("/goods/edit")
    public String edit(HttpServletRequest request) {
        request.setAttribute("path", "edit");
        //查询所有的一级分类
        List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            //查询一级分类列表中第一个实体的所有二级分类
            List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                //查询二级分类列表中第一个实体的所有三级分类
                List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                request.setAttribute("firstLevelCategories", firstLevelCategories);
                request.setAttribute("secondLevelCategories", secondLevelCategories);
                request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                request.setAttribute("path", "goods-edit");
                return "admin/newbee_mall_goods_edit";
            }
        }
        return "error/error_5xx";
    }

    /**
     * 商品更改
     * @param request
     * @param goodsId
     * @return
     */
    @GetMapping("/goods/edit/{goodsId}")
    public String edit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId) {
        request.setAttribute("path", "edit");
        NewBeeMallGoods newBeeMallGoods = newBeeMallGoodsService.getNewBeeMallGoodsById(goodsId);
        if (newBeeMallGoods == null) {
            return "error/error_400";
        }
        if (newBeeMallGoods.getGoodsCategoryId() > 0) {
            if (newBeeMallGoods.getGoodsCategoryId() != null || newBeeMallGoods.getGoodsCategoryId() > 0) {
                //有分类字段则查询相关分类数据返回给前端以供分类的三级联动显示
                GoodsCategory currentGoodsCategory = newBeeMallCategoryService.getGoodsCategoryById(newBeeMallGoods.getGoodsCategoryId());
                //商品表中存储的分类id字段为三级分类的id，不为三级分类则是错误数据
                if (currentGoodsCategory != null && currentGoodsCategory.getCategoryLevel() == NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
                    //查询所有的一级分类
                    List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
                    //根据parentId查询当前parentId下所有的三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(currentGoodsCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    //查询当前三级分类的父级二级分类
                    GoodsCategory secondCategory = newBeeMallCategoryService.getGoodsCategoryById(currentGoodsCategory.getParentId());
                    if (secondCategory != null) {
                        //根据parentId查询当前parentId下所有的二级分类
                        List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondCategory.getParentId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                        //查询当前二级分类的父级一级分类
                        GoodsCategory firestCategory = newBeeMallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                        if (firestCategory != null) {
                            //所有分类数据都得到之后放到request对象中供前端读取
                            request.setAttribute("firstLevelCategories", firstLevelCategories);
                            request.setAttribute("secondLevelCategories", secondLevelCategories);
                            request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                            request.setAttribute("firstLevelCategoryId", firestCategory.getCategoryId());
                            request.setAttribute("secondLevelCategoryId", secondCategory.getCategoryId());
                            request.setAttribute("thirdLevelCategoryId", currentGoodsCategory.getCategoryId());
                        }
                    }
                }
            }
        }
        if (newBeeMallGoods.getGoodsCategoryId() == 0) {
            //查询所有的一级分类
            List<GoodsCategory> firstLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), NewBeeMallCategoryLevelEnum.LEVEL_ONE.getLevel());
            if (!CollectionUtils.isEmpty(firstLevelCategories)) {
                //查询一级分类列表中第一个实体的所有二级分类
                List<GoodsCategory> secondLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(firstLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_TWO.getLevel());
                if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                    //查询二级分类列表中第一个实体的所有三级分类
                    List<GoodsCategory> thirdLevelCategories = newBeeMallCategoryService.selectByLevelAndParentIdsAndNumber(Collections.singletonList(secondLevelCategories.get(0).getCategoryId()), NewBeeMallCategoryLevelEnum.LEVEL_THREE.getLevel());
                    request.setAttribute("firstLevelCategories", firstLevelCategories);
                    request.setAttribute("secondLevelCategories", secondLevelCategories);
                    request.setAttribute("thirdLevelCategories", thirdLevelCategories);
                }
            }
        }
        request.setAttribute("goods", newBeeMallGoods);
        request.setAttribute("path", "goods-edit");
        return "admin/newbee_mall_goods_edit";
    }

    /**
     * 更改或者新增秒杀商品
     * @param request
     * @param goodsId
     * @return
     */
    @GetMapping("/flashGoods/edit/{goodsId}")
    public String flashGoodsEdit(HttpServletRequest request, @PathVariable("goodsId") Long goodsId){
        request.setAttribute("path", "edit");
        FlashGoodsVo flashGoods = newBeeMallGoodsService.getFlashGoodsById(goodsId);
        if (flashGoods == null) {
            //新增
            FlashGoodsVo goodsVo=new FlashGoodsVo();
            goodsVo.setGoodsId(goodsId);
            request.setAttribute("goods", goodsVo);
//            return "error/error_400";
        }else {
            //修改时数据回显
            request.setAttribute("goods",flashGoods);
        }
        request.setAttribute("path", "flashGoods-edit");
        return "admin/newbee_mall_flashGoods_edit";

    }

    /**
     * 商品列表
     */
    @RequestMapping(value = "/goods/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.getNewBeeMallGoodsPage(pageUtil));
    }

    /**
     * 秒杀商品列表
     * @param params
     * @return
     */
    @GetMapping("/flashGoods/list")
    @ResponseBody
    public Result flashList(@RequestParam Map<String,Object> params){
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallGoodsService.getFlashGoodsPage(pageUtil));
    }

    /**
     * 批量删除秒杀商品
     * 其实就是删除表的关联关系
     * @param ids
     * @return
     */
    @PostMapping("flashGoods/delete")
    @ResponseBody
    public Result deleteFlashGoods(@RequestBody Long[] ids){
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (newBeeMallGoodsService.deleteFlashGoodsBatch(ids)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("删除失败");
        }
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/goods/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(@RequestBody NewBeeMallGoods newBeeMallGoods) {
        if (checkGoodsData(newBeeMallGoods)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallGoodsService.saveNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 修改
     */
    @RequestMapping(value = "/goods/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody NewBeeMallGoods newBeeMallGoods) {
        if (Objects.isNull(newBeeMallGoods.getGoodsId()) || checkGoodsData(newBeeMallGoods)) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallGoodsService.updateNewBeeMallGoods(newBeeMallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 判断数据是否异常，其实可以直接在jsr303。再类上使用注解来实现
     *
     * @param newBeeMallGoods
     * @return
     */
    private boolean checkGoodsData(NewBeeMallGoods newBeeMallGoods) {
        return StringUtils.isEmpty(newBeeMallGoods.getGoodsName())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsIntro())
                || StringUtils.isEmpty(newBeeMallGoods.getTag())
                || Objects.isNull(newBeeMallGoods.getOriginalPrice())
                || Objects.isNull(newBeeMallGoods.getGoodsCategoryId())
                || Objects.isNull(newBeeMallGoods.getSellingPrice())
                || Objects.isNull(newBeeMallGoods.getStockNum())
                || Objects.isNull(newBeeMallGoods.getGoodsSellStatus())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsCoverImg())
                || StringUtils.isEmpty(newBeeMallGoods.getGoodsDetailContent());

    }

    /**
     * 新增或修改秒杀商品
     */
    @RequestMapping(value = "/flashGoods/update")
    @ResponseBody
    public Result update(@RequestBody FlashGoodsVo goodsVo) {
        //判断传过来的商品是否在秒杀列表中
        FlashGoodsVo goods = newBeeMallGoodsService.getFlashGoodsById(goodsVo.getGoodsId());
        String result;
        if (goods == null) {
            //新增
            result = newBeeMallGoodsService.saveFlashGoods(goodsVo);
        } else {
            //修改
            result = newBeeMallGoodsService.updateFlashGoods(goodsVo);
        }
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
    /**
     * 批量修改销售状态
     */
    @RequestMapping(value = "/goods/status/{sellStatus}", method = RequestMethod.PUT)
    @ResponseBody
    public Result delete(@RequestBody Long[] ids, @PathVariable("sellStatus") int sellStatus) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (newBeeMallGoodsService.batchUpdateSellStatus(ids, sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }
}