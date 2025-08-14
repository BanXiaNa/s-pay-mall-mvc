package com.banxia.domain.res;

import lombok.Data;

/**
 * @Author BanXia
 * @description: 微信二维码响应实体类
 * @Date 2025/8/12 22:31
 */
@Data
public class WeixinQrCodeRes {

    // 二维码的ticket，可用来换取二维码
    private String ticket;
    // 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
    private Long expire_seconds;
    // 二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
    private String url;

}
