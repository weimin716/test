package com.gdd.ylz.common.base;

import com.gdd.ylz.config.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class UserController {
    @Autowired
    private HttpServletRequest request;

    public String getUserId(){
        return JwtTokenUtil.getUserId(request.getHeader("authorization"));
    }
}
