package com.gdd.ylz.modules.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.common.exception.code.BaseResponseCode;
import com.gdd.ylz.common.util.RadomNameUtils;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.config.jwt.JwtTokenUtil;
import com.gdd.ylz.config.jwt.PasswordUtils;
import com.gdd.ylz.config.redis.RedisService;
import com.gdd.ylz.constants.Constant;
import com.gdd.ylz.modules.login.dto.LoginPassWordDTO;
import com.gdd.ylz.modules.login.dto.LoginRespDTO;
import com.gdd.ylz.modules.login.dto.PasswordBackDTO;
import com.gdd.ylz.modules.login.dto.RegisterDTO;
import com.gdd.ylz.modules.login.service.LoginService;
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
public class LoginServiceImpl implements LoginService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private ITSysUserService sysUserService;
    @Autowired
    private ITSysRoleService sysRoleService;
    @Autowired
    private ITSysRoleUserService itSysRoleUserService;
    @Autowired
    private ITSysPermissionRoleService itSysPermissionRoleService;
    @Autowired
    private ITSysPermissionService itSysPermissionService;
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
        TSysUser sysUserInfo = sysUserService.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getUsername, registerDTO.getUsername()));
        if (null != sysUserInfo) {
            return new DataResult(1, "用户名已被占用");
        }
        sysUserInfo=sysUserService.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getPhone,registerDTO.getPhone()));
        if(null!=sysUserInfo){
            return new DataResult(1,"该手机号已有人注册使用");
        }
        if(!StringUtils.isPassword(registerDTO.getPassword())){
            return new DataResult(1,"密码必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）");
        }
        return DataResult.success();
    }

    @Override
    public DataResult doRegister(RegisterDTO registerDTO) {
        TSysUser sysUser = new TSysUser();
        BeanUtils.copyProperties(registerDTO, sysUser);


        String salt = PasswordUtils.getSalt();
        sysUser.setSalt(salt);
        String encode = PasswordUtils.encode(sysUser.getPassword(), salt);
        sysUser.setPassword(encode);
        //sysUser.setNickname(RadomNameUtils.getRandomJianHan(6));
        //删除redis中的验证码
        sysUser.setUserType("01");
        List<Filestore> list = filestoreService.list(new QueryWrapper<Filestore>().lambda().eq(Filestore::getIsAvatar, 1));
        Random random = new Random();
        int n = random.nextInt(list.size());
        sysUser.setAvatar(list.get(n).getFilePath());
        sysUserService.save(sysUser);
        return DataResult.success();
    }

    @Override
    public LoginRespDTO login(LoginPassWordDTO loginPassWordDTO) {
        TSysUser sysUser=null;
        if(StringUtils.isPhone(loginPassWordDTO.getUsernameorphone())){
            sysUser=sysUserService.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getPhone,loginPassWordDTO.getUsernameorphone()));
            if (null == sysUser) {
                throw new BusinessException(BaseResponseCode.PHONE_ERROR);
            }
        }else{
            sysUser=sysUserService.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getUsername,loginPassWordDTO.getUsernameorphone()));
            if (null == sysUser) {
                throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
            }
        }

        if (!PasswordUtils.matches(sysUser.getSalt(),loginPassWordDTO.getPassword(),sysUser.getPassword())) {
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        LoginRespDTO respVO = new LoginRespDTO();
        BeanUtils.copyProperties(sysUser, respVO);
        Map<String, Object> claims = new HashMap<>();
        List<String> roleIds = itSysRoleUserService.list(new QueryWrapper<TSysRoleUser>().lambda().eq(TSysRoleUser::getSysUserId, sysUser.getId())).stream().map(o -> o.getSysRoleId()).collect(Collectors.toList());
        List<TSysRole> roleList=null;
        List<TSysPermission> permissionList=null;
        if(!roleIds.isEmpty()){
            roleList = sysRoleService.listByIds(roleIds);
            List<String> permissionIds = itSysPermissionRoleService.list(new QueryWrapper<TSysPermissionRole>().lambda().in(TSysPermissionRole::getRoleId, roleIds)).stream().map(o -> o.getPermissionId()).collect(Collectors.toList());
            if(!permissionIds.isEmpty()){
                permissionList = itSysPermissionService.listByIds(permissionIds);
            }

        }
        claims.put(Constant.ROLES_INFOS_KEY, roleList);
        claims.put(Constant.PERMISSIONS_INFOS_KEY, permissionList);
        claims.put(Constant.JWT_USER_NAME, sysUser.getUsername());
        String access_token = JwtTokenUtil.getAccessToken(sysUser.getId().toString(), claims);
        String refresh_token;
        Map<String, Object> refreshTokenClaims = new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME, sysUser.getUsername());
        refresh_token = JwtTokenUtil.getRefreshToken(sysUser.getId().toString(), refreshTokenClaims);
        respVO.setAccessToken(access_token);
        respVO.setRefreshToken(refresh_token);
        return respVO;
    }

    @Override
    public DataResult checkNewpasswordData(PasswordBackDTO passwordBackDTO) {
        TSysUser sysUserInfo=sysUserService.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getPhone,passwordBackDTO.getPhone()));
        if(null==sysUserInfo){
            return new DataResult(1,"该手机号未绑定任何账号");
        }
        if (!redisService.get(passwordBackDTO.getPhone()).equals(passwordBackDTO.getVcode())) {
            return new DataResult(1, "手机验证码不正确");
        }
        return DataResult.success();
    }

    @Override
    public DataResult doNewPassword(PasswordBackDTO passwordBackDTO) {
        String newPassword = PasswordUtils.encode(passwordBackDTO.getNewPassword(), sysUserService.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getPhone, passwordBackDTO.getPhone())).getSalt());
        sysUserService.update(new UpdateWrapper<TSysUser>().lambda().set(TSysUser::getPassword,newPassword).eq(TSysUser::getPhone,passwordBackDTO.getPhone()));
        return DataResult.success();
    }
}
