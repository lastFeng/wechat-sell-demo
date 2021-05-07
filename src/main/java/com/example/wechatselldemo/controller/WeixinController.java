package com.example.wechatselldemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : guoweifeng
 * @date : 2021/5/7
 */
@RestController
@RequestMapping("/weixin")
public class WeixinController {

    /**开发文档地址*/
    private static final String WIKI_URL = "https://developers.weixin.qq.com/doc/offiaccount/OA_Web_Apps/Wechat_webpage_authorization.html";
    /**获取code地址*/
    private static final String CODE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}" +
            "&redirect_uri={}&response_type=code&scope={}#wechat_redirect";
    /**获取access_token地址*/
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}" +
            "&secret=SECRET&code={}&grant_type=authorization_code";
    /**刷新access_token地址*/
    private static final String REFRESH_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/refresh_token?" +
            "appid={}&grant_type=refresh_token&refresh_token={}";
    /**校验access_token地址*/
    private static final String VALIDATE_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/auth?" +
            "access_token={}&openid={}";

    @GetMapping("/auth")
    public void auth(@RequestParam("code") String code) {
        // 拿到code进行网页获取access_token
    }
}
