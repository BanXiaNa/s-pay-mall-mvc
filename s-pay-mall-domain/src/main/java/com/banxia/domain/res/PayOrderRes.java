package com.banxia.domain.res;

import com.banxia.common.constants.Constants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author BanXia
 * @description: 订单响应实体类
 * @Date 2025/8/15 20:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PayOrderRes {

    private String userId;

    private String orderId;

    private String payUrl;

    private Constants.OrderStatusEnum OrderStatusEnum;
}
