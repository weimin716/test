package com.gdd.ylz.modules.sms;

import com.gdd.ylz.common.exception.code.BaseResponseCode;
import com.gdd.ylz.common.sms.HttpArg;
import com.gdd.ylz.common.sms.SmsUtils;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.config.redis.RedisService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sms/t-sms")
@Api(tags = {"sms"})
@CrossOrigin
@Slf4j
public class SmsController {
    @Autowired
    private HttpArg httpArg;
    @Autowired
    private RedisService redisService;
    @Value("${sms.content}")
    private String content;

    private static final Logger logger = LoggerFactory.getLogger(SmsController.class);

    @GetMapping("/getsmscode")
    @ApiOperation(value = "获取短信验证码",notes = "获取短信验证码")
    public DataResult getSmscode(String phone){
        if(!StringUtils.isPhone(phone)){
            return DataResult.getResult(-1,"不是手机号码");
        }
        String vcode=SmsUtils.createRandomVcode();
        redisService.set(phone,vcode,60,TimeUnit.SECONDS);
        /**
         * 模拟发送验证码到前端
         */
         return DataResult.getResult(200,"获取验证码成功",vcode);


/*        httpArg.setContent(httpArg.getContent().replace("{code}",vcode));
        String result = SmsUtils.sendCode(httpArg, phone);
        if(result.equals("0")){
            logger.info("短信验证码发送成功，短信验证码为："+vcode);
            logger.info("短信内容是:"+httpArg.getContent());
            redisService.set(phone,vcode,60,TimeUnit.SECONDS);
            httpArg.setContent(content);
            return DataResult.success(result);
        }
        return DataResult.getResult(BaseResponseCode.SMS_GETCODE_ERROR);*/

    }
}
