package com.banxia.domain.req;

import lombok.*;

/**
 * @Author BanXia
 * @description: 微信二维码请求实体类
 * @Date 2025/8/12 22:36
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeixinQrCodeReq {

    // 二维码有效时间，单位秒。最大不超过2592000（即30天）。
    private int expire_seconds;
    // 二维码类型，QR_SCENE为临时的整型参数值，
    // QR_STR_SCENE为临时的字符串参数值，
    // QR_LIMIT_SCENE为永久的整型参数值，
    // QR_LIMIT_STR_SCENE为永久的字符串参数值。
    private String action_name;
    // 二维码详细信息
    private ActionInfo action_info;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActionInfo {
        // 二维码场景信息
        Scene scene;

        @Data
        @Builder
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Scene {
            // 场景值ID，
            // 临时二维码时为32位非0整型，
            // 永久二维码时最大值为100000（目前参数只支持1--100000）
            int scene_id;
            // 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
            String scene_str;
        }
    }

    /**
     * 二维码类型枚举
     */
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public enum ActionNameTypeVO {
        QR_SCENE("QR_SCENE", "临时的整型参数值"),
        QR_STR_SCENE("QR_STR_SCENE", "临时的字符串参数值"),
        QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久的整型参数值"),
        QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久的字符串参数值");

        private String code;
        private String info;
    }
}
