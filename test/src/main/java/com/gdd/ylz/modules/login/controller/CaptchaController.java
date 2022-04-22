package com.gdd.ylz.modules.login.controller;

import com.gdd.ylz.config.redis.RedisService;
import com.gdd.ylz.modules.sys.dto.CaptchaDTO;
import com.gdd.ylz.result.DataResult;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.base.Captcha;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = {"login"},description = "图形验证码模块")
@RestController
@RequestMapping("/captcha")
@CrossOrigin
public class CaptchaController {
    @Autowired
    private RedisService redisService;
    private static final Logger logger= LoggerFactory.getLogger(CaptchaController.class);

    /**
     * 验证码生成  使用session
     * @param request 请求报文
     * @param response 响应报文
     * */
    @GetMapping("/captchaImage")
    @ApiOperation(value="获取图形验证码",notes="获取图形验证码")
    public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130,48,4);
        //静态验证码
        //SpecCaptcha gifCaptcha=new SpecCaptcha(130,48,4);

        CaptchaUtil.out(gifCaptcha, request, response);
    }

    /**
     * 异步验证 使用session
     * @param request 请求报文
     * @return 验证结果
     * */
    @PostMapping("/verify")
    @ApiOperation(value="图形验证码校验",notes="图形验证码校验")
    public DataResult verify(HttpServletRequest request, @RequestBody CaptchaDTO captchaDTO){
        logger.info("传过来的图形验证码是："+captchaDTO.getCaptcha()+"session中存的验证码是"+(String)request.getSession().getAttribute("captcha"));
        if(CaptchaUtil.ver(captchaDTO.getCaptcha(), request)){
            logger.info("一样");
            return DataResult.success();
        }
        return DataResult.getResult(-1,"验证码错误");
    }
    /**
     * 验证码生成  使用Redis
     * */
    @GetMapping("/captchaImageRedis")
    @ApiOperation(value="获取图形验证码Redis",notes="获取图形验证码Redis")
    public DataResult generateByredis() throws Exception {
        GifCaptcha gifCaptcha = new GifCaptcha(130,48,4);
        //静态验证码
        //SpecCaptcha gifCaptcha=new SpecCaptcha(130,48,4);
        // gif类型
        //GifCaptcha captcha = new GifCaptcha(130, 48);

        // 中文类型
        //ChineseCaptcha captcha = new ChineseCaptcha(130, 48);

        // 中文gif类型
        //ChineseGifCaptcha captcha = new ChineseGifCaptcha(130, 48);
        // 算术类型
        //ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48);
        // png类型
//        SpecCaptcha captcha = new SpecCaptcha(130, 48, 4);
        GifCaptcha captcha = new GifCaptcha(130, 48,4);
        /**验证码字符类型
         TYPE_DEFAULT 数字和字母混合
         TYPE_ONLY_NUMBER 纯数字
         TYPE_ONLY_CHAR 纯字母
         TYPE_ONLY_UPPER 纯大写字母
         TYPE_ONLY_LOWER 纯小写字母
         TYPE_NUM_AND_UPPER  数字和大写字母
         TYPE_ONLY_NUMBER   设置纯数字验证码
         **/
        captcha.setCharType(Captcha.TYPE_DEFAULT);//数字和字母混合
        // 设置内置字体
//        captcha.setFont(Captcha.FONT_1);
        // 设置系统字体
//        captcha.setFont(new Font("楷体", Font.PLAIN, 28));
        //验证码 VUL
        String verCode = captcha.text().toLowerCase();
        //uuid KEY
        String key =UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redisService.set(key, verCode,30,TimeUnit.MINUTES);
        // 将key和base64返回给前端
        Map<String,String> map=new HashMap<>();
        map.put("key", key);
        map.put("image", captcha.toBase64());
        return new DataResult(200,"success",map);
    }

    /**
     * 异步验证 使用Redis
     * @return 验证结果
     * */
    @PostMapping("/verifyRedis")
    @ApiOperation(value="图形验证码校验Redis",notes="图形验证码校验Redis")
    public DataResult verifyRedis(@RequestBody CaptchaDTO captchaDTO){
        logger.info("传过来的图形验证码是："+captchaDTO.getCaptcha()+"redis中存的验证码是"+redisService.get(captchaDTO.getKey()));
        if(Objects.equals(captchaDTO.getCaptcha(),redisService.get(captchaDTO.getKey()))){
            logger.info("一样");
            return DataResult.success();
        }
        return DataResult.getResult(-1,"验证码错误");
    }
}
