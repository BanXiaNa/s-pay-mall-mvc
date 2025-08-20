package com.banxia.service;

import com.banxia.domain.req.ShopCartReq;
import com.banxia.domain.res.PayOrderRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author BanXia
 * @description: 订单服务接口
 * @Date 2025/8/15 20:28
 */
public interface IOrderService {

    PayOrderRes createOrder(ShopCartReq shopCart) throws Exception;

    void changeOrderPaySuccess(String orderId);

    List<String> queryNoPayNotifyOrderList();

    List<String> queryTimeOutOrderList();

    boolean changeOrderPayClose(String orderId);
}
