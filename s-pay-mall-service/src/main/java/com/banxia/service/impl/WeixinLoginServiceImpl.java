package com.banxia.service.impl;

import com.banxia.domain.po.WeixinTemplateMessageVO;
import com.banxia.domain.req.WeixinQrCodeReq;
import com.banxia.domain.res.WeixinQrCodeRes;
import com.banxia.domain.res.WeixinTokenRes;
import com.banxia.service.ILoginService;
import com.banxia.service.weixin.IWeixinApiService;
import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author BanXia
 * @description: 微信登录服务
 * @Date 2025/8/13 23:22
 */
@Service
public class WeixinLoginServiceImpl implements ILoginService {

    @Value("${weixin.config.app-id}")
    private String appId;
    @Value("${weixin.config.app-secret}")
    private String appSecret;
    @Value("${weixin.config.template-id}")
    private String templateId;

    // 缓存access_token
    @Resource
    private Cache<String , String> weixinAccessToken;
    // 缓存openid
    @Resource
    private Cache<String , String> openidToken;
    // 微信API服务
    @Resource
    private IWeixinApiService weixinApiService;

    /**
     * 创建二维码
     * @return 二维码ticket
     * @throws Exception 创建二维码失败
     */
    @Override
    public String createQrCodeTicket() throws Exception {
        // 获取AccessToken
        // 首先检测缓存
        String accessToken = weixinAccessToken.getIfPresent(appId);
        if(null == accessToken){
            // 没有，就请求
            Call<WeixinTokenRes> callWeixinToken = weixinApiService.getToken("client_credential", appId, appSecret);
            WeixinTokenRes weixinTokenRes = callWeixinToken.execute().body();
            assert weixinTokenRes != null;
            accessToken = weixinTokenRes.getAccess_token();
            weixinAccessToken.put(appId, accessToken);
        }

//      // 现在获取二维码
        WeixinQrCodeReq weixinQrCodeReq = WeixinQrCodeReq.builder()
                .expire_seconds(2592000)
                .action_name(WeixinQrCodeReq.ActionNameTypeVO.QR_SCENE.getCode())
                .action_info(WeixinQrCodeReq.ActionInfo.builder()
                        .scene(WeixinQrCodeReq.ActionInfo.Scene.builder()
                                .scene_id(100601)
                                .build())
                        .build())
                .build();

        Call<WeixinQrCodeRes> callWeixinQrCode = weixinApiService.createQrcode(accessToken, weixinQrCodeReq);
        WeixinQrCodeRes weixinQrCodeRes = callWeixinQrCode.execute().body();
        assert weixinQrCodeRes != null;

        return weixinQrCodeRes.getTicket();
    }

    /**
     * 检测登录
     * @param ticket 提交ticket
     * @return openid
     * @throws Exception 检测登录失败
     */
    @Override
    public String checkLogin(String ticket) throws Exception {
        return openidToken.getIfPresent(ticket);
    }

    /**
     * 通过ticket和openId保存登录状态
     * @param ticket 提交ticket
     * @param openId 提交openId
     * @throws Exception
     */
    @Override
    public void saveLoginState(String ticket, String openId) throws Exception {
        openidToken.put(ticket, openId);

        String accessToken = weixinAccessToken.getIfPresent(appId);


        if(null == accessToken){
            // 没有，就请求
            Call<WeixinTokenRes> callWeixinToken = weixinApiService.getToken("client_credential", appId, appSecret);
            WeixinTokenRes weixinTokenRes = callWeixinToken.execute().body();
            assert weixinTokenRes != null;
            accessToken = weixinTokenRes.getAccess_token();
            weixinAccessToken.put(appId, accessToken);
        }

        // 发送模板消息
        Map<String, Map<String, String>> data = new HashMap<>();
        WeixinTemplateMessageVO.put(data, WeixinTemplateMessageVO.TemplateKey.USER, openId);

        WeixinTemplateMessageVO templateMessageDTO = new WeixinTemplateMessageVO(openId, templateId);
        templateMessageDTO.setUrl("https://gaga.plus");
        templateMessageDTO.setData(data);

        Call<Void> call = weixinApiService.sendMessage(accessToken, templateMessageDTO);
        call.execute();


    }
}
