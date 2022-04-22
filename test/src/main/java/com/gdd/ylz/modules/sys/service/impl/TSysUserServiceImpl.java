package com.gdd.ylz.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.modules.sys.dto.SysUserDetailDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserUpdateDTO;
import com.gdd.ylz.modules.sys.entity.TSysRoleUser;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.gdd.ylz.modules.sys.dao.TSysUserMapper;
import com.gdd.ylz.modules.sys.service.ITSysRoleUserService;
import com.gdd.ylz.modules.sys.service.ITSysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.result.DataResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-10-10
 */
@Service
public class TSysUserServiceImpl extends ServiceImpl<TSysUserMapper, TSysUser> implements ITSysUserService {
   @Autowired
   private TSysUserMapper sysUserMapper;
   @Autowired
   private ITSysRoleUserService sysRoleUserService;


    @Override
    public IPage<TSysUser> getList(TSysUserQueryDTO tSysUserQueryDTO) {
        Page page=new Page(tSysUserQueryDTO.getStart(),tSysUserQueryDTO.getLength());
        return sysUserMapper.getList(page,tSysUserQueryDTO);
    }

    @Override
    public DataResult checkUpdateSysUserData(TSysUserUpdateDTO tSyUser) {
        if(!StringUtils.isEmpty(tSyUser.getUsername())){
            if(!StringUtils.isUserName(tSyUser.getUsername())){
                return DataResult.getResult(-1,"用户名必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）\n" +
                        "不能以数字开头");
            }
            TSysUser sysUserInfo=null;
            if(!StringUtils.isEmpty(tSyUser.getId())){
                sysUserInfo = this.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getUsername, tSyUser.getUsername()).ne(TSysUser::getId,tSyUser.getId()));
            }else{
                sysUserInfo = this.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getUsername, tSyUser.getUsername()));
            }
            if (null != sysUserInfo) {
                return new DataResult(1, "用户名已被占用");
            }
        }


        if(!StringUtils.isEmpty(tSyUser.getPhone())){
            if(!StringUtils.isPhone(tSyUser.getPhone())){
                return new DataResult(-1,"手机号码格式不正确");
            }

            if(!StringUtils.isEmpty(tSyUser.getId())){
                TSysUser sysuser = this.getById(tSyUser.getId());
                if(!sysuser.getPhone().equals(tSyUser.getPhone())){
                    TSysUser sysUserInfo=this.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getPhone,tSyUser.getPhone()));
                    if(null!=sysUserInfo){
                        return new DataResult(1,"该手机号已有人注册使用");
                    }
                }
            }else{
                TSysUser sysUserInfo=this.getOne(new QueryWrapper<TSysUser>().lambda().eq(TSysUser::getPhone,tSyUser.getPhone()));
                if(null!=sysUserInfo){
                    return new DataResult(1,"该手机号已有人注册使用");
                }
            }

        }
        if(!StringUtils.isEmpty(tSyUser.getPassword())){
            if(!StringUtils.isPassword(tSyUser.getPassword())){
                return new DataResult(1,"密码必须是6-20位的字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）");
            }
        }
        return DataResult.success();
    }

    @Override
    public boolean checkUserDeleteable(String id) {
        return true;
    }

    @Override
    public DataResult getDetail(String id) {
        TSysUser sysUser = this.getById(id);
        List<TSysRoleUser> list = sysRoleUserService.list(new QueryWrapper<TSysRoleUser>().lambda().eq(TSysRoleUser::getSysUserId, id).eq(TSysRoleUser::getiFlag, 1));
        List<String> roleIds = list.stream().map(o -> o.getSysRoleId()).collect(Collectors.toList());
        SysUserDetailDTO sysUserDetailDTO=new SysUserDetailDTO();
        BeanUtils.copyProperties(sysUser,sysUserDetailDTO);
        sysUserDetailDTO.setRoleIds(roleIds);
        return DataResult.success(sysUserDetailDTO);
    }
}
