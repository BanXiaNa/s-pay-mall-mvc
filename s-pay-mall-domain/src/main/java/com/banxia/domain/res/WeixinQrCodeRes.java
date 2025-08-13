package com.banxia.domain.res;

import lombok.Data;

/**
 * @Author BanXia
 * @description: 微信二维码响应实体类
 * @Date 2025/8/12 22:31
 */
@Data
public class WeixinQrCodeRes {

    private String ticket;
    private Long expire_seconds;
    private String url;

}
