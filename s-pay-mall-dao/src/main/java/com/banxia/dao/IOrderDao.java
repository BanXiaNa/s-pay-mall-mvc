package com.banxia.dao;

import com.banxia.domain.po.PayOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/8/15 20:13
 */
@Mapper
public interface IOrderDao {

    void insert (PayOrder payOrder);

    PayOrder queryUnPayOrder(PayOrder payOrderReq);

    void updatePayOrderInfo(PayOrder payOrder);

    void changeOrderPaySuccess(PayOrder order);
}
