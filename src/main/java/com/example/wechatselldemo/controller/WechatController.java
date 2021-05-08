package com.example.wechatselldemo.controller;

import com.example.wechatselldemo.enums.ResultEnum;
import com.example.wechatselldemo.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * @author : guoweifeng
 * @date : 2021/5/7
 * 微信支付第三方sdk： https://github.com/Wechat-Group/WxJava
 * https://github.com/Wechat-Group/WxJava/wiki
 */
@Controller
@RequestMapping("/sell/wechat")
@Slf4j
public class WechatController {

    @Autowired
    private WxMpService wxMpService;

    /**
     * 设置授权跳转url，会跳转至指定的url，同时带上code-换取access_token与state-url内容
     * @param returnUrl
     * @return
     */
    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl") String returnUrl) {
//        wxMpService.
        String url = "http://proxy.com/sell/wechat/userInfo";
        String redirectUrl = wxMpService.getOAuth2Service().buildAuthorizationUrl(url, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);
        return "redirect:" + redirectUrl;
    }

    /**
     * 用code去换取accessToken，并根据state的请求，来在自己的controller中进行处理
     * @param code
     * @param returnUrl
     * @return
     */
    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code") String code,
                           @RequestParam("state") String returnUrl) {
        WxOAuth2AccessToken accessToken;
        try {
            accessToken = wxMpService.getOAuth2Service().getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("【微信网页授权】授权失败{}", e);
            throw new SellException(ResultEnum.WECHAT_MP_ERROR);
        }

        String openId = accessToken.getOpenId();
        return "redirect:" + returnUrl + "?openId=" + openId;
    }

    @GetMapping("/userInfo")
    public void userInfo(@RequestParam("openId") String openId) {
        // get openId, doSomething
    }
}
