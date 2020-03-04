package com.swpu.shop.shopflash.service;

import com.swpu.shop.entity.MallUser;
import com.swpu.shop.entity.NewBeeMallOrder;
import com.swpu.shop.shopflash.redis.MiaoshaKey;
import com.swpu.shop.shopflash.redis.RedisService;
import com.swpu.shop.shopflash.vo.FlashGoodsVo;
import com.swpu.shop.util.UUIDUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 秒杀的service
 * @author 曾伟
 * @date 2019/11/21 22:19
 */
@Service
public class FlashSaleService {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private OrderService orderService;

    /**
     * 图片验证码
     * @param user
     * @param goodsId
     * @return
     */
    public BufferedImage createVerifyCode(MallUser user, long goodsId) {
        if(user == null || goodsId <=0) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xD48DDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        int times=50;
        for (int i = 0; i < times; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 24));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.verifyCode, user.getUserId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    /**
     * 验证图片验证码
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    public boolean checkVerifyCode(MallUser user, long goodsId, int verifyCode) {
        if(user == null || goodsId <=0) {
            return false;
        }
        Integer codeOld = (Integer) redisService.get(MiaoshaKey.verifyCode, user.getUserId()+","+goodsId);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(MiaoshaKey.verifyCode, user.getUserId()+","+goodsId);
        return true;
    }

    /**
     * 计算验证码的结果
     * @param exp
     * @return
     */
    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     *  得到验证码的计算式，只有 + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * 生成url中的path
     * @param userId
     * @param goodsId
     * @return
     */
    public String createPath(Long userId,Long goodsId) {
        String str = UUIDUtils.uuid();
        redisService.set(MiaoshaKey.miaoshaPath, "" + userId+ "_" + goodsId, str);
        return str;
    }

    /**
     * 验证path
     * @param path
     * @param userId
     * @param goodsId
     * @return
     */
    public boolean check(String path, Long userId, Long goodsId) {
        if (userId == null || goodsId==null|| StringUtils.isEmpty(path)) {
            return false;
        }
        String redisPath = (String) redisService.get(MiaoshaKey.miaoshaPath, "" + userId + "_" + goodsId);
        return redisPath.equals(path);
    }
    /**
     * 秒杀操作（原子）。
     *
     * @param user
     * @param goodsVo
     * @return
     */
    @Transactional
    public NewBeeMallOrder flash(MallUser user, FlashGoodsVo goodsVo) {
        //减库存
        boolean success = goodsService.reduceStock(goodsVo);
        if (success) {
            //下订单
            //写入秒杀订单
            return orderService.createOrder(user, goodsVo);
        } else {
            setGoodsOver(goodsVo.getGoodsId());
            return null;
        }
    }
    /**
     * 设置商品秒杀结束
     * @param goodsId
     */
    private void setGoodsOver(long goodsId) {
        redisService.set(MiaoshaKey.goodsOver, "" + goodsId, true);
    }
}
