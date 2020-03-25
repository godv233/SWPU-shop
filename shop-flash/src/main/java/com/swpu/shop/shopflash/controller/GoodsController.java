package com.swpu.shop.shopflash.controller;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.common.Result;
import com.swpu.shop.shopflash.redis.GoodsKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.service.GoodsService;
import com.swpu.shop.shopflash.vo.FlashGoodsDetailVo;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品控制器
 * @author 曾伟
 * @date 2019/11/16 11:00
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 跳转商品列表，并携带商品数据
     * @return
     * 在5000*10的情况下
     * 未使用redis之前的qps是1200左右
     * 使用redis之后是2880左右。系统的负载也降低了
     *
     *
     */
    @RequestMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String goodsList(HttpServletRequest request, HttpServletResponse response, Model model) {
        //从缓存中取
        String html = (String) redisService.get(GoodsKey.getGoodsList, "list");
        if (!StringUtils.isEmpty(html)) {
            return html;
        }
        //手动渲染
        //查询商品列表
        List<FlashGoodsVo> goodsVoList = goodsService.goodsVoList();
        model.addAttribute("goodslist", goodsVoList);
//        return "goods_list";
        WebContext context = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", context);
        if (!StringUtils.isEmpty(html)) {
            redisService.set(GoodsKey.getGoodsList, "list", html);
        }
        return html;
    }

    @RequestMapping(value="/detail/{goodsId}")
    @ResponseBody
    public Result<FlashGoodsDetailVo> detail(MallUser user, @PathVariable("goodsId")long goodsId) {
        FlashGoodsVo goods = goodsService.goodsVoById(goodsId);
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        int flashStatus = 0;
        int remainSeconds = 0;
        if(now < startAt ) {
            //秒杀还没开始，倒计时
            flashStatus = 0;
            remainSeconds = (int)((startAt - now )/1000);
        }else  if(now > endAt){
            //秒杀已经结束
            flashStatus = 2;
            remainSeconds = -1;
        }else {//秒杀进行中
            flashStatus = 1;
            remainSeconds = 0;
        }
        FlashGoodsDetailVo vo = new FlashGoodsDetailVo();
        vo.setGoods(goods);
        vo.setUser(user);
        vo.setRemainSeconds(remainSeconds);
        vo.setFlashStatus(flashStatus);
        return Result.success(vo);
    }
}
