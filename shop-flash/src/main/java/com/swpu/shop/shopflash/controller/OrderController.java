package com.swpu.shop.shopflash.controller;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.shopflash.common.CodeMsg;
import com.swpu.shop.shopflash.common.Result;
import com.swpu.shop.shopflash.service.GoodsService;
import com.swpu.shop.shopflash.service.OrderService;
import com.swpu.shop.shopflash.vo.FlashGoodsDetailVo;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import com.swpu.shop.shopflash.vo.FlashOrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订单控制器
 *
 * @author 曾伟
 * @date 2019/11/25 19:01
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private GoodsService goodsService;

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Result<FlashOrderDetailVo> info(@RequestParam("orderId") long orderId) {
        FlashOrderDetailVo detail = orderService.getOrderDetailById(orderId);
        return Result.success(detail);

    }

}
