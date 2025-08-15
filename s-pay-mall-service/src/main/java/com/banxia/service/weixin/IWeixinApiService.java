package com.banxia.service.weixin;

import com.banxia.domain.vo.WeixinTemplateMessageVO;
import com.banxia.domain.req.WeixinQrCodeReq;
import com.banxia.domain.res.WeixinQrCodeRes;
import com.banxia.domain.res.WeixinTokenRes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * @Author BanXia
 * @description: 微信API服务
 * @Date 2025/8/12 16:25
 */
public interface IWeixinApiService {

    /**
     * 获取微信token
     * @param grantType 获取token的方式
     * @param appid 第三方应用的唯一凭证
     * @param secret 第三方的密钥
     * @return 响应结果
     */
    @GET("cgi-bin/token")
    Call<WeixinTokenRes> getToken(
            @Query("grant_type") String grantType,
            @Query("appid") String appid,
            @Query("secret") String secret
    );

    /**
     * 创建二维码
     * @param accessToken 微信token
     * @param weixinQrCodeReq 创建二维码的参数
     * @return 响应结果
     */
    @POST("cgi-bin/qrcode/create")
    Call<WeixinQrCodeRes> createQrcode(
            @Query("access_token") String accessToken,
            @Body WeixinQrCodeReq weixinQrCodeReq
    );

    /**
     * 发送模板消息
     * @param accessToken 获取token
     * @param weixinTemplateMessageVO 模板消息参数
     * @return 响应结果
     */
    @POST("cgi-bin/message/template/send")
    Call<Void> sendMessage(
            @Query("access_token") String accessToken,
            @Body WeixinTemplateMessageVO weixinTemplateMessageVO
            );
}
