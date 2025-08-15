package com.banxia.domain.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author BanXia
 * @description: 购物车请求实体类
 * @Date 2025/8/15 20:30
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopCartReq {

    private String userId;

    private String productId;
}
