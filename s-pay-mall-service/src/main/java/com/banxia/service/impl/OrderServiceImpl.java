package com.banxia.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.banxia.common.constants.Constants;
import com.banxia.dao.IOrderDao;
import com.banxia.domain.po.PayOrder;
import com.banxia.domain.req.ShopCartReq;
import com.banxia.domain.res.PayOrderRes;
import com.banxia.domain.vo.ProductVO;
import com.banxia.service.IOrderService;
import com.banxia.service.rpc.ProductRPC;
import com.google.common.eventbus.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/8/15 20:38
 */
@Slf4j
@Service
public class OrderServiceImpl implements IOrderService {

    @Value("${alipay.notify_url}")
    private String notifyUrl;
    @Value("${alipay.return_url}")
    private String returnUrl;

    @Resource
    private IOrderDao orderDao;
    @Resource
    private ProductRPC productRPC;
    @Resource
    private AlipayClient alipayClient;
    @Resource
    private EventBus eventBus;


    /**
     * 创建订单
     * @param shopCartReq 购物车请求参数
     * @return 订单信息
     * @throws Exception
     */
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
            log.info("创建订单-存在，存在未创建支付单订单，创建支付单开始 userId:{} productId:{} orderId:{}", unPayOrder.getUserId(), unPayOrder.getProductId(), unPayOrder.getOrderId());
            // 掉单订单
            PayOrder payOrder = doPayOrder(
                    unPayOrder.getProductId(),
                    unPayOrder.getProductName(),
                    unPayOrder.getOrderId(),
                    unPayOrder.getTotalAmount()
            );
            return PayOrderRes.builder()
                    .orderId(payOrder.getOrderId())
                    .payUrl(payOrder.getPayUrl())
                    .build();
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

        PayOrder payOrder = doPayOrder(
                productVO.getProductId(),
                productVO.getProductName(),
                orderId,
                productVO.getProductPrice()
        );

        return PayOrderRes.builder()
                .orderId(orderId)
                .payUrl(payOrder.getPayUrl())
                .build();
    }

    @Override
    public void changeOrderPaySuccess(String orderId) {
        PayOrder payOrderReq = PayOrder.builder()
                .orderId(orderId)
                .status(Constants.OrderStatusEnum.PAY_SUCCESS.getCode())
                .build();
        orderDao.changeOrderPaySuccess(payOrderReq);
        // 发布支付成功事件
        eventBus.post(JSON.toJSONString(payOrderReq));
    }

    @Override
    public List<String> queryNoPayNotifyOrderList() {
        return orderDao.queryNoPayNotifyOrder();
    }

    @Override
    public List<String> queryTimeOutOrderList() {
        return orderDao.queryTimeoutOrderList();
    }

    @Override
    public boolean changeOrderPayClose(String orderId){
        return orderDao.changeOrderPayClose(orderId);
    }


    private PayOrder doPayOrder(String productId, String productName, String orderId, BigDecimal totalAmount) throws AlipayApiException {

        AlipayTradePagePayRequest request = new AlipayTradePagePayRequest();
        request.setNotifyUrl(notifyUrl);
        request.setReturnUrl(returnUrl);

        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", orderId);
        bizContent.put("total_amount", totalAmount.toString());
        bizContent.put("subject", productName);
        bizContent.put("product_code", "FAST_INSTANT_TRADE_PAY");
        request.setBizContent(bizContent.toString());

        String form = alipayClient.pageExecute(request).getBody();

        PayOrder payOrder = new PayOrder();
        payOrder.setOrderId(orderId);
        payOrder.setPayUrl(form);
        payOrder.setStatus(Constants.OrderStatusEnum.PAY_WAIT.getCode());

        orderDao.updatePayOrderInfo(payOrder);

        return payOrder;
    }
}
