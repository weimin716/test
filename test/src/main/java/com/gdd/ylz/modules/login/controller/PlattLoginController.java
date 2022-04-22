package com.gdd.ylz.modules.login.controller;


import com.gdd.ylz.config.redis.RedisService;
import com.gdd.ylz.modules.login.dto.LoginPassWordDTO;
import com.gdd.ylz.modules.login.dto.PasswordBackDTO;
import com.gdd.ylz.modules.login.dto.RegisterDTO;
import com.gdd.ylz.modules.login.service.LoginService;
import com.gdd.ylz.modules.login.service.PlattLoginService;
import com.gdd.ylz.result.DataResult;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@RestController
@RequestMapping("/login/platt/t-login")
@Api(tags = {"login"})
@CrossOrigin
@Slf4j
public class PlattLoginController {
    @Autowired
    private PlattLoginService loginService;
    @Autowired
    private RedisService redisService;

    private static final Logger logger= LoggerFactory.getLogger(PlattLoginController.class);

    @PostMapping("/dologin")
    @ApiOperation(value="密码或手机号登录",notes="密码或手机号登录")
    public DataResult doLogin(@RequestBody LoginPassWordDTO loginPassWordDTO,HttpServletRequest request){
        Boolean yz = false;
        // 判断验证码
        if (CaptchaUtil.ver(loginPassWordDTO.getCaptcha(),request)) {
            //清除session中的验证码
            CaptchaUtil.clear(request);
            yz = true;
        }
        if(yz){
            return DataResult.success(loginService.login(loginPassWordDTO));
        }else{
            return DataResult.getResult(-1, "验证码不正确!");
        }
    }
    @PostMapping("/dologinredis")
    @ApiOperation(value="密码或手机号登录",notes="密码或手机号登录")
    public DataResult doLogin(@RequestBody LoginPassWordDTO loginPassWordDTO){
        Boolean yz = false;
        // 判断验证码
        if (Objects.equals(loginPassWordDTO.getCaptcha().toLowerCase(),redisService.get(loginPassWordDTO.getKey()))) {
            //清除session中的验证码
            redisService.delete(loginPassWordDTO.getKey());
            yz = true;
        }
        if(yz){
            return DataResult.success(loginService.login(loginPassWordDTO));
        }else{
            return DataResult.getResult(-1, "验证码不正确!");
        }
    }
    @PostMapping("/register")
    @ApiOperation(value="注册",notes = "注册")
    public DataResult doRegister(@RequestBody RegisterDTO registerDTO){
        DataResult dataResult = loginService.checkRegisterData(registerDTO);
        if(dataResult.hasError()){
            return dataResult;
        }
        return loginService.doRegister(registerDTO);
    }

    @PostMapping("/backpassword")
    @ApiOperation(value="找回密码",notes="找回密码")
    public DataResult doBackPassword(@RequestBody PasswordBackDTO passwordBackDTO,HttpServletRequest request){
        if (!CaptchaUtil.ver(passwordBackDTO.getCaptcha(),request)) {
            //清除session中的验证码
            CaptchaUtil.clear(request);
            return DataResult.getResult(-1,"图形验证码不正确");
        }
        DataResult dataResult=loginService.checkNewpasswordData(passwordBackDTO);
        if(dataResult.hasError()){
            return dataResult;
        }
        return loginService.doNewPassword(passwordBackDTO);

    }
    @GetMapping("/logout")
    @ApiOperation(value="用户退出",notes="用户退出")
    public DataResult doLogout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return DataResult.success();

    }


}
