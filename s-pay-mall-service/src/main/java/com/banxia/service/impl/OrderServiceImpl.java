package com.banxia.service.impl;

import com.banxia.common.constants.Constants;
import com.banxia.dao.IOrderDao;
import com.banxia.domain.po.PayOrder;
import com.banxia.domain.req.ShopCartReq;
import com.banxia.domain.res.PayOrderRes;
import com.banxia.domain.vo.ProductVO;
import com.banxia.service.IOrderService;
import com.banxia.service.rpc.ProductRPC;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Random;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/8/15 20:38
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Resource
    private IOrderDao orderDao;
    @Resource
    private ProductRPC productRPC;

    @Override
    public PayOrderRes createOrder(ShopCartReq shopCartReq) throws Exception {

        // 1. 查询是否有未支付订单或掉单订单
        PayOrder payOrderReq = PayOrder.builder()
                .userId(shopCartReq.getUserId())
                .productId(shopCartReq.getProductId())
                .build();

        PayOrder unPayOrder = orderDao.queryUnPayOrder(payOrderReq);
        if(null != unPayOrder && Constants.OrderStatusEnum.PAY_WAIT.getCode().equals(unPayOrder.getOrderId())){
            log.info("创建订单-存在，存在未支付的订单，userId:{} productId:{} orderId:{}",unPayOrder.getUserId(), unPayOrder.getProductId(), unPayOrder.getOrderId());
            return PayOrderRes.builder()
                    .orderId(unPayOrder.getOrderId())
                    .payUrl(unPayOrder.getPayUrl())
                    .build();
        } else if (null != unPayOrder && Constants.OrderStatusEnum.CREATE.getCode().equals(unPayOrder.getStatus())){
            // 我不到啊
        }

        // 2. 查询商品 & 创建订单
        ProductVO productVO = productRPC.queryProductByProductId(shopCartReq.getProductId());
        String orderId = RandomStringUtils.randomNumeric(16);
        orderDao.insert(PayOrder.builder()
                .userId(shopCartReq.getUserId())
                .productId(shopCartReq.getProductId())
                .productName(productVO.getProductName())
                .orderId(orderId)
                .totalAmount(productVO.getProductPrice())
                .orderTime(new Date())
                .status(Constants.OrderStatusEnum.CREATE.getCode())
                .build());

        // 创建订单


        return PayOrderRes.builder()
                .orderId(orderId)
                .payUrl("没写呢")
                .build();
    }
}
