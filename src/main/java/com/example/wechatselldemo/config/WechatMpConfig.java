//package com.example.wechatselldemo.config;
//
//import me.chanjar.weixin.mp.api.WxMpService;
//import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
//import me.chanjar.weixin.mp.config.WxMpConfigStorage;
//import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//
///**
// * @author : guoweifeng
// * @date : 2021/5/7
// * 微信支付第三方sdk： https://github.com/Wechat-Group/WxJava
// */
//@Component
//public class WechatMpConfig {
////    @Value("${wechat.mpAppId}")
////    private String appId;
////
////    @Value("${wechat.mpAppSecrete}")
////    private String appSecret;
//
//    @Autowired
//    private WechatAccountConfig accountConfig;
//
//    /**
//     * 微信公众号支付服务bean
//     * @return
//     */
//    @Bean
//    public WxMpService wxMpService() {
//        WxMpService wxMpService = new WxMpServiceImpl();
//        wxMpService.setWxMpConfigStorage(wxMpDefaultConfig());
//        return wxMpService;
//    }
//
//    /**
//     * 微信配置bean
//     */
//    @Bean
//    public WxMpDefaultConfigImpl wxMpDefaultConfig() {
//        WxMpDefaultConfigImpl wxMpConfigStorage = new WxMpDefaultConfigImpl();
//        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
//        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecrete());
//        return wxMpConfigStorage;
//    }
//}
