package com.gdd.ylz.modules.wechat.controller;

import com.gdd.ylz.modules.wechat.dto.WechatLoginDTO;
import com.gdd.ylz.modules.wechat.service.WechatService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(description = "微信接口",tags="微信接口")
@RequestMapping("/login/wechat")
public class WechatController {
    @Autowired
    private WechatService wechatService;

    /**
     * 1 生成自己账号生成的二维码
     */
     @ApiOperation(value="获取二维码",notes="获取二维码")
     @GetMapping("/getqcode")
     public DataResult getQcode(){
        return DataResult.success(wechatService.getQcodeUrl());
     }

     @ApiOperation(value="用户扫码后的回调函数",notes="用户扫码后的回调函数")
     @GetMapping("/callback")
     public DataResult doCallback(String code,String state) throws Exception{
          return DataResult.success(wechatService.doCallback(code,state));
     }
     @ApiOperation(value="获取用户信息，并保存，签发jwt token授权",notes="获取用户信息，并保存，签发jwt token授权")
     @PostMapping("/getandsaveuserinfo")
     public DataResult getAndSaveUserInfo(@RequestBody WechatLoginDTO wechatLoginDTO){
         return DataResult.success(wechatService.getAndSaveUserInfo(wechatLoginDTO));
     }

}
