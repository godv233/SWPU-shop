package com.swpu.shop.controller.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 首页轮播图VO
 * @author GODV
 */
@Data
public class NewBeeMallIndexCarouselVO implements Serializable {

    private String carouselUrl;

    private String redirectUrl;
}
