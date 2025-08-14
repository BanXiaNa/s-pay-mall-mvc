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
		- WeixinTemplateMessageVO 实体类
	- req
		- WeixinQrCodeReq 微信二维码请求实体类
	- res
		- WeixinQrCodeRes 微信二维码响应实体类
		- WeixinTokenRes 获取微信公众号的AccessToken
- service
	- impl
		- WeixinLoginServiceImpl 继承 ILoginService 用于微信登录流程
	- weixin
		- IWeixinApiService 调用微信API服务
	- ILoginService 登录服务统一接口
- web
	- config
		- GuavaConfig 本地缓存配置类
		- Retrofit2Config retrofit请求配置
	- controller
		- LoginController 微信登录响应
		- WeixinPortalController 微信信息回应
	