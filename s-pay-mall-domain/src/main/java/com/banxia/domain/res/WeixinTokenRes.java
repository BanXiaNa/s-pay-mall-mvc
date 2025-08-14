package com.banxia.domain.res;

import lombok.Data;

/**
 * @Author BanXia
 * @description: 获取微信公众号token
 * @Date 2025/8/12 16:26
 */
@Data
public class WeixinTokenRes {

    private String access_token;
    private int expires_in;
    private String errcode;
    private String errmsg;
}
