package com.swpu.shop.controller.admin;

import com.swpu.shop.common.ServiceResultEnum;
import com.swpu.shop.controller.vo.NewBeeMallOrderItemVO;
import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.service.NewBeeMallOrderService;
import com.swpu.shop.util.PageQueryUtil;
import com.swpu.shop.util.Result;
import com.swpu.shop.util.ResultGenerator;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * @author GODV
 */
@Controller
@RequestMapping("/admin")
public class NewBeeMallOrderController {

    @Resource
    private NewBeeMallOrderService newBeeMallOrderService;

    /**
     * 跳转订单页面
     *
     * @param request
     * @return
     */
    @GetMapping("/orders")
    public String ordersPage(HttpServletRequest request) {
        request.setAttribute("path", "orders");
        return "admin/newbee_mall_order";
    }

    /**
     * 跳转秒杀订单
     *
     * @param request
     * @return
     */
    @GetMapping("/flashOrders")
    public String flashOrderPage(HttpServletRequest request) {
        request.setAttribute("path", "flashOrders");
        return "admin/newbee_mall_flash_order";
    }

    /**
     * 列表
     */
    @RequestMapping(value = "/orders/list", method = RequestMethod.GET)
    @ResponseBody
    public Result list(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallOrderService.getFlashOrdersPage(pageUtil));
    }

    @RequestMapping("/flashOrders/list")
    @ResponseBody
    public Result flashList(@RequestParam Map<String, Object> params) {
        if (StringUtils.isEmpty(params.get("page")) || StringUtils.isEmpty(params.get("limit"))) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(newBeeMallOrderService.getNewBeeMallOrdersPage(pageUtil));
    }

    /**
     * 修改
     */
    @RequestMapping(value = "/orders/update", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody NewBeeMallOrder newBeeMallOrder) {
        if (Objects.isNull(newBeeMallOrder.getTotalPrice())
                || Objects.isNull(newBeeMallOrder.getOrderId())
                || newBeeMallOrder.getOrderId() < 1
                || newBeeMallOrder.getTotalPrice() < 1
                || StringUtils.isEmpty(newBeeMallOrder.getUserAddress())) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = newBeeMallOrderService.updateOrderInfo(newBeeMallOrder);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }

    /**
     * 详情
     */
    @GetMapping("/order-items/{id}")
    @ResponseBody
    public Result info(@PathVariable("id") Long id) {
        List<NewBeeMallOrderItemVO> orderItems = newBeeMallOrderService.getOrderItems(id);
        if (!CollectionUtils.isEmpty(orderItems)) {
            return ResultGenerator.genSuccessResult(orderItems);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }

    /**
     * 配货
     */
    @RequestMapping(value = "/orders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result checkDone(@RequestBody Long[] ids) {
        return doAction(ids, "checkDone");
    }

    /**
     * 秒杀配货
     */
    @RequestMapping(value = "/flashOrders/checkDone", method = RequestMethod.POST)
    @ResponseBody
    public Result flashCheckDone(@RequestBody Long[] ids) {
        return doAction(ids, "flashCheckDone");
    }

    /**
     * 出库
     */
    @RequestMapping(value = "/orders/checkOut", method = RequestMethod.POST)
    @ResponseBody
    public Result checkOut(@RequestBody Long[] ids) {
        return doAction(ids, "checkOut");
    }

    /**
     * 关闭订单
     */
    @RequestMapping(value = "/orders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeOrder(@RequestBody Long[] ids) {
        return doAction(ids, "closeOrder");
    }

    /**
     * 关闭秒杀订单
     */
    @RequestMapping(value = "/flashOrders/close", method = RequestMethod.POST)
    @ResponseBody
    public Result closeFlashOrder(@RequestBody Long[] ids) {
        return doAction(ids, "closeFlashOrder");
    }

    /**
     * 代码提取
     *
     * @param ids  ids
     * @param type 执行类型
     * @return
     */
    public Result doAction(Long[] ids, String type) {
        if (ids.length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        String result = null;
        if ("checkDone".equals(type)) {
            result = newBeeMallOrderService.checkDone(ids);
        } else if ("flashCheckDone".equals(type)) {
            result = newBeeMallOrderService.flashCheckDone(ids);
        } else if ("checkOut".equals(type)) {
            result = newBeeMallOrderService.checkOut(ids);
        } else if ("closeOrder".equals(type)) {
            result = newBeeMallOrderService.closeOrder(ids);
        } else if ("closeFlashOrder".equals(type)) {
            result = newBeeMallOrderService.closeFlashOrder(ids);
        }
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }
}
