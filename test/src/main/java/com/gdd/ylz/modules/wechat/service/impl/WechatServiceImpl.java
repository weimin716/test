package com.gdd.ylz.modules.wechat.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.gdd.ylz.common.util.HttpClientUtils;
import com.gdd.ylz.config.WechatConfig;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.gdd.ylz.modules.sys.service.ITSysUserService;
import com.gdd.ylz.modules.wechat.dto.WechatLoginDTO;
import com.gdd.ylz.modules.wechat.dto.WechatUserInfoDTO;
import com.gdd.ylz.modules.wechat.service.WechatService;
import com.gdd.ylz.result.DataResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Service
public class WechatServiceImpl implements WechatService {
    @Autowired
    private ITSysUserService sysUserService;

    @Autowired
    private WechatConfig wechatConfig;

    @Override
    public String getQcodeUrl() {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +   //请使用urlEncode对链接进行处理
                "&scope=snsapi_login" +  //应用授权作用域
                "&state=%s" +   //原样传递
                "#wechat_redirect";
        //请使用urlEncode对链接进行处理
        String redirect= wechatConfig.getRedirectUrl();
        try {
            redirect = URLEncoder.encode(redirect, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //使用gson进行格式化，转换为json
        String onlineEduUrl = String.format(
                baseUrl,
                wechatConfig.getAppId(),
                redirect,//请使用urlEncode对链接进行处理
                "onlineEdu" //原样返回
        );
        return onlineEduUrl;
    }

    @Override
    public DataResult doCallback(String code, String state) {
        System.out.println("===="+code+"==="+state+"====");
        if(StringUtils.isNotEmpty(code)){
            StringBuilder url = new StringBuilder();
            url.append("https://api.weixin.qq.com/sns/oauth2/access_token?");
            url.append("appid=" + wechatConfig.getAppId());
            url.append("&secret=" + wechatConfig.getAppSecret());
            //这是微信回调给你的code
            url.append("&code=" + code);
            url.append("&grant_type=authorization_code");
            JSONObject jsonObject = HttpClientUtils.httpGet(url.toString(), "UTF-8");
            System.out.println("result:" + jsonObject.toString());
            return DataResult.success(jsonObject);
        }else{
            return DataResult.getResult(-1,"用户拒绝授权");
        }
    }

    @Override
    public DataResult getAndSaveUserInfo(WechatLoginDTO wechatLoginDTO) {
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+wechatLoginDTO.getAccessToken() +"&openid="+wechatLoginDTO.getOpenId();
        JSONObject jsonObject = HttpClientUtils.httpGet(infoUrl, "utf-8");
        WechatUserInfoDTO wechatUserInfoDTO = JSON.parseObject(jsonObject.toJSONString(), WechatUserInfoDTO.class);
        TSysUser sysUser=new TSysUser();
        sysUser.setUserType(wechatUserInfoDTO.getType());
        sysUser.setNickname(wechatUserInfoDTO.getNickname());
        /**
         * TODO 看传过来的是什么
         */
        sysUser.setSex(Integer.valueOf(wechatUserInfoDTO.getSex()));
        sysUser.setAvatar(wechatUserInfoDTO.getHeadimgurl());
        sysUser.setUpdateTime(wechatUserInfoDTO.getModified());
        sysUserService.save(sysUser);
        /**
         * TODO 登录成功后权限分配问题，签发jwt token,refresh token  怎么换取新的token
         */
        return DataResult.success(sysUser);
    }
}
