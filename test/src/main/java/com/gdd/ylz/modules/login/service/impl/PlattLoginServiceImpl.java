package com.gdd.ylz.modules.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.common.exception.code.BaseResponseCode;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.config.jwt.JwtTokenUtil;
import com.gdd.ylz.config.jwt.PasswordUtils;
import com.gdd.ylz.config.redis.RedisService;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.login.dto.LoginPassWordDTO;
import com.gdd.ylz.modules.login.dto.LoginRespDTO;
import com.gdd.ylz.modules.login.dto.PasswordBackDTO;
import com.gdd.ylz.modules.login.dto.RegisterDTO;
import com.gdd.ylz.modules.login.service.PlattLoginService;
import com.gdd.ylz.modules.sys.entity.*;
import com.gdd.ylz.modules.sys.service.*;
import com.gdd.ylz.result.DataResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class PlattLoginServiceImpl implements PlattLoginService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ITPlattUserService plattUserService;
    @Autowired
    private ITPlattRoleService plattRoleService;
    @Autowired
    private ITPlattRoleUserService itplattRoleUserService;
    @Autowired
    private ITPlattPermissionRoleService itplattPermissionRoleService;
    @Autowired
    private ITPlattPermissionService itplattPermissionService;
    @Autowired
    private IFilestoreService filestoreService;
    
    @Override
    public DataResult checkRegisterData(RegisterDTO registerDTO) {
        if (!redisService.hasKey( registerDTO.getPhone())) {
            return new DataResult(1, "验证码已失效");
        }
        if (!redisService.get(registerDTO.getPhone()).equals(registerDTO.getVcode())) {
            return new DataResult(1, "验证码不正确");
        }
        if(!StringUtils.isUserName(registerDTO.getUsername())){
            return new DataResult(1,"用户名必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）\n" +
                    "不能以数字开头");
        }
        TPlattUser plattUserInfo = plattUserService.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getUsername, registerDTO.getUsername()));
        if (null != plattUserInfo) {
            return new DataResult(1, "用户名已被占用");
        }
        plattUserInfo=plattUserService.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getPhone,registerDTO.getPhone()));
        if(null!=plattUserInfo){
            return new DataResult(1,"该手机号已有人注册使用");
        }
        if(!StringUtils.isPassword(registerDTO.getPassword())){
            return new DataResult(1,"密码必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）");
        }
        return DataResult.success();
    }

    @Override
    public DataResult doRegister(RegisterDTO registerDTO) {
        TPlattUser plattUser = new TPlattUser();
        BeanUtils.copyProperties(registerDTO, plattUser);


        String salt = PasswordUtils.getSalt();
        plattUser.setSalt(salt);
        String encode = PasswordUtils.encode(plattUser.getPassword(), salt);
        plattUser.setPassword(encode);
        //plattUser.setNickname(RadomNameUtils.getRandomJianHan(6));
        //删除redis中的验证码
        plattUser.setUserType("01");
        List<Filestore> list = filestoreService.list(new QueryWrapper<Filestore>().lambda().eq(Filestore::getIsAvatar, 1));
        Random random = new Random();
        int n = random.nextInt(list.size());
        plattUser.setAvatar(list.get(n).getFilePath());
        plattUserService.save(plattUser);
        return DataResult.success();
    }

    @Override
    public LoginRespDTO login(LoginPassWordDTO loginPassWordDTO) {
        TPlattUser plattUser=null;
        if(StringUtils.isPhone(loginPassWordDTO.getUsernameorphone())){
            plattUser=plattUserService.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getPhone,loginPassWordDTO.getUsernameorphone()));
            if (null == plattUser) {
                throw new BusinessException(BaseResponseCode.PHONE_ERROR);
            }
        }else{
            plattUser=plattUserService.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getUsername,loginPassWordDTO.getUsernameorphone()));
            if (null == plattUser) {
                throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
            }
        }

        if (!PasswordUtils.matches(plattUser.getSalt(),loginPassWordDTO.getPassword(),plattUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        LoginRespDTO respVO = new LoginRespDTO();
        BeanUtils.copyProperties(plattUser, respVO);
        Map<String, Object> claims = new HashMap<>();
        List<String> roleIds = itplattRoleUserService.list(new QueryWrapper<TPlattRoleUser>().lambda().eq(TPlattRoleUser::getPlattUserId, plattUser.getId())).stream().map(o -> o.getPlattRoleId()).collect(Collectors.toList());
        List<TPlattRole> roleList=null;
        List<TPlattPermission> permissionList=null;
        if(!roleIds.isEmpty()){
            roleList = plattRoleService.listByIds(roleIds);
            List<String> permissionIds = itplattPermissionRoleService.list(new QueryWrapper<TPlattPermissionRole>().lambda().in(TPlattPermissionRole::getRoleId, roleIds)).stream().map(o -> o.getPermissionId()).collect(Collectors.toList());
            if(!permissionIds.isEmpty()){
                permissionList = itplattPermissionService.listByIds(permissionIds);
            }

        }
        claims.put(Constant.ROLES_INFOS_KEY, roleList);
        claims.put(Constant.PERMISSIONS_INFOS_KEY, permissionList);
        claims.put(Constant.JWT_USER_NAME, plattUser.getUsername());
        String access_token = JwtTokenUtil.getAccessToken(plattUser.getId().toString(), claims);
        String refresh_token;
        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, plattUser.getUsername());
        refresh_token = JwtTokenUtil.getRefreshToken(plattUser.getId().toString(), refreshTokenClaims);
        respVO.setAccessToken(access_token);
        respVO.setRefreshToken(refresh_token);
        return respVO;
    }

    @Override
    public DataResult checkNewpasswordData(PasswordBackDTO passwordBackDTO) {
        TPlattUser plattUserInfo=plattUserService.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getPhone,passwordBackDTO.getPhone()));
        if(null==plattUserInfo){
            return new DataResult(1,"该手机号未绑定任何账号");
        }
        if (!redisService.get(passwordBackDTO.getPhone()).equals(passwordBackDTO.getVcode())) {
            return new DataResult(1, "手机验证码不正确");
        }
        return DataResult.success();
    }

    @Override
    public DataResult doNewPassword(PasswordBackDTO passwordBackDTO) {
        String newPassword = PasswordUtils.encode(passwordBackDTO.getNewPassword(), plattUserService.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getPhone, passwordBackDTO.getPhone())).getSalt());
        plattUserService.update(new UpdateWrapper<TPlattUser>().lambda().set(TPlattUser::getPassword,newPassword).eq(TPlattUser::getPhone,passwordBackDTO.getPhone()));
        return DataResult.success();
    }
}
