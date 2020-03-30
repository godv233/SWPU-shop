package com.swpu.shop.shopflash.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.swpu.shop.entity.MallUser;
import com.swpu.shop.shopflash.common.CodeMsg;
import com.swpu.shop.shopflash.common.Result;
import com.swpu.shop.shopflash.intercepter.AccessLimit;
import com.swpu.shop.shopflash.rabbitmq.MqSender;
import com.swpu.shop.shopflash.rabbitmq.SaleMessage;
import com.swpu.shop.shopflash.redis.GoodsKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.service.FlashSaleService;
import com.swpu.shop.shopflash.service.OrderService;
import com.swpu.shop.shopflash.vo.FlashSaleOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.OutputStream;

/**
 * 秒杀的控制器
 *
 * @author 曾伟
 * @date 2019/11/21 21:58
 */
@Controller
@RequestMapping("/flash")
public class FlashSaleController{
    @Autowired
    private RedisService redisService;
    @Autowired
    private FlashSaleService flashSaleService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MqSender sender;

    /**
     * 得到生成校验码：
     *
     * @param response
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result<String> getFlashVerifyCod(HttpServletResponse response, MallUser user,
                                            @RequestParam("goodsId") long goodsId) {
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        try {
            BufferedImage image = flashSaleService.createVerifyCode(user, goodsId);
            OutputStream out = response.getOutputStream();
            ImageIO.write(image, "JPEG", out);
            out.flush();
            out.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
    }


    /**
     * 得到path
     *需要在这个地方做一个限流的处理
     * accessLimit .这个地方可以做一个注解的形式
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/path")
    @AccessLimit(seconds = 5,maxCount = 2,needLogin = true)
    @ResponseBody
    public Result<String> getFlashPath(Model model, MallUser user, @RequestParam("goodsId") long goodsId,
                                       @RequestParam(value = "verifyCode", defaultValue = "0") int verifyCode) {
        //测试时defaultValue.还需要修改
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //查询接口访问的次数，实现限流防刷
        boolean check = flashSaleService.checkVerifyCode(user, goodsId, verifyCode);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }
        String str = flashSaleService.createPath(user.getUserId(), goodsId);
        return Result.success(str);
    }

    /**
     * 页面静态化的秒杀接口
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @RequestMapping("/{path}/do_flash")
    @ResponseBody
    public Result<Integer> sale(Model model, MallUser user, @RequestParam("goodsId") long goodsId,
                                @PathVariable("path") String path) throws JsonProcessingException {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        //验证path
        boolean check = flashSaleService.check(path, user.getUserId(), goodsId);
        if (!check) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }


        //接口优化
        //1.预减库存
        long decrease = redisService.decrease(GoodsKey.getGoodsStock, "" + goodsId);
        if (decrease < 0) {
            return Result.error(CodeMsg.MIAOSHA_FAIL);
        }
        //判断是否秒杀过了
        FlashSaleOrder order = orderService.orderByUserIdGoodsId(user.getUserId(), goodsId);
        if (order != null) {
            //不能重复秒杀
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }
        //入队
        SaleMessage message = new SaleMessage();
        message.setGoodsId(goodsId);
        message.setUser(user);
        sender.miaoshaMessage(message);
        return Result.success(0);
    }



    /**
     * 查询秒杀结果：
     * 成功：orderId
     * 失败：-1
     * 还在处理中：0
     *
     * @param model
     * @param user
     * @param goodsId
     * @return
     */
    @GetMapping("/result")
    @ResponseBody
    public Result<Long> flashResult(Model model, MallUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = flashSaleService.getFlashResult(user.getUserId(), goodsId);
        return Result.success(result);

    }
}
