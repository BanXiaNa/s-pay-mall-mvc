package com.banxia.test;

import com.alibaba.fastjson.JSON;
import com.banxia.config.AliPayConfigProperties;
import com.banxia.service.IOrderService;
import com.google.common.eventbus.EventBus;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @Author BanXia
 * @description:
 * @Date 2025/8/11 22:42
 */
@SpringBootTest
@EnableConfigurationProperties(AliPayConfigProperties.class)
public class ApiTest {

    @Resource
    private IOrderService iOrderService;
    
    
    @Test

    public void doGet1() throws IOException {


        iOrderService.changeOrderPaySuccess("7773742227072607");
    }
}


