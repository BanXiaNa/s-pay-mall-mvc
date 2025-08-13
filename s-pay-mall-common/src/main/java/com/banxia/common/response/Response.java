package com.banxia.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author BanXia
 * @description: 统一返回结果
 * @Date 2025/8/14 00:22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response<T> implements Serializable {

    // 序列化版本号
    private static final long serialVersionUID = 7000723935764546321L;
    // 响应码
    private String code;
    // 响应信息
    private String info;
    // 响应数据
    private T data;

}
