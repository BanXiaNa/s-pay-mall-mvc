package com.banxia.test.service;

import com.alibaba.fastjson2.JSON;
import com.banxia.domain.req.ShopCartReq;
import com.banxia.domain.res.PayOrderRes;
import com.banxia.service.IOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author BanXia
 * @description: 订单服务测试
 * @Date 2025/8/15 21:29
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTest {

    @Resource
    private IOrderService orderService;

    @Test
    public void test_creatOrder() throws Exception {
        ShopCartReq banXia = ShopCartReq.builder()
                .userId("BanXia")
                .productId("1001")
                .build();
        PayOrderRes shopOrderRes = orderService.createOrder(banXia);
        log.info("请求参数：{}", JSON.toJSONString(banXia));
        log.info("测试结果：{}", JSON.toJSONString(shopOrderRes));
    }
}
