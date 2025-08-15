package com.banxia.service.rpc;

import com.banxia.domain.vo.ProductVO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author BanXia
 * @description: 商品RPC服务类
 * @Date 2025/8/15 21:04
 */
@Service
public class ProductRPC {

    public ProductVO queryProductByProductId(String productId) {
        return ProductVO.builder()
                .productId(productId)
                .productName("测试商品")
                .productDesc("这是一个测试商品")
                .productPrice(new BigDecimal("1.68"))
                .build();
    }
}
