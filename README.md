# 小型支付商城

练手项目

## 环境

- jdk 1.8
- maven 3.8.*

## 结构

- data 日志信息存储
- common 通用类
    - constants
    	- Constants 常量类
    - exception
		- AppException 统一异常处理类
	- response
		- Response 统一返回结果
	- weixin
		- MessageTextEntity 微信消息实体类
		- SignatureUtil 签名工具类
		- XmlUtil XML工具类
- dao
- domain
	- po 
		- PayOrder 订单实体类
	- req
		- WeixinQrCodeReq 微信二维码请求实体类
		- ShopCartReq 购物车请求实体类
	- res
		- WeixinQrCodeRes 微信二维码响应实体类
		- WeixinTokenRes 获取微信公众号的AccessToken
		- PayOrderRes 订单响应实体类
    - vo
    	- ProductVO 商品信息类
  		- WeixinTemplateMessageVO 微信模板信息实体类
- service
	- impl
		- WeixinLoginServiceImpl 用于微信登录流程
		- OrderServiceImpl 订单服务实现
	- rpc
		- ProductRPC 商品RPC服务类
	- weixin
		- IWeixinApiService 调用微信API服务
	- ILoginService 登录服务统一接口
	- IOrderService 订单服务接口
- web
	- config
		- GuavaConfig 本地缓存配置类
		- Retrofit2Config retrofit请求配置
	- mybatis
		- PayOrderMapper.xml 订单库映射
	- controller
		- LoginController 微信登录响应
		- WeixinPortalController 微信信息回应