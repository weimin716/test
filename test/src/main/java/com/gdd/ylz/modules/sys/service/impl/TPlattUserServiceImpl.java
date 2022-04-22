package com.gdd.ylz.modules.platt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.modules.sys.dao.TPlattUserMapper;
import com.gdd.ylz.modules.sys.dto.SysUserDetailDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserUpdateDTO;
import com.gdd.ylz.modules.sys.entity.TPlattRoleUser;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.gdd.ylz.modules.sys.service.ITPlattRoleUserService;
import com.gdd.ylz.modules.sys.service.ITPlattUserService;
import com.gdd.ylz.result.DataResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台用户表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
@Service
public class TPlattUserServiceImpl extends ServiceImpl<TPlattUserMapper, TPlattUser> implements ITPlattUserService {
    @Autowired
    private TPlattUserMapper plattUserMapper;
    @Autowired
    private ITPlattRoleUserService plattRoleUserService;


    @Override
    public IPage<TPlattUser> getList(TSysUserQueryDTO tSysUserQueryDTO) {
        Page page=new Page(tSysUserQueryDTO.getStart(),tSysUserQueryDTO.getLength());
        return plattUserMapper.getList(page,tSysUserQueryDTO);
    }

    @Override
    public DataResult checkUpdateplattUserData(TSysUserUpdateDTO tSyUser) {
        if(!StringUtils.isEmpty(tSyUser.getUsername())){
            if(!StringUtils.isUserName(tSyUser.getUsername())){
                return DataResult.getResult(-1,"用户名必须是6-10位字母、数字、下划线（这里字母、数字、下划线是指任意组合，没有必须三类均包含）\n" +
                        "不能以数字开头");
            }
            TPlattUser plattUserInfo=null;
            if(!StringUtils.isEmpty(tSyUser.getId())){
                plattUserInfo = this.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getUsername, tSyUser.getUsername()).ne(TPlattUser::getId,tSyUser.getId()));
            }else{
                plattUserInfo = this.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getUsername, tSyUser.getUsername()));
            }
            if (null != plattUserInfo) {
                return new DataResult(1, "用户名已被占用");
            }
        }


        if(!StringUtils.isEmpty(tSyUser.getPhone())){
            if(!StringUtils.isPhone(tSyUser.getPhone())){
                return new DataResult(-1,"手机号码格式不正确");
            }

            if(!StringUtils.isEmpty(tSyUser.getId())){
                TPlattUser plattuser = this.getById(tSyUser.getId());
                if(!plattuser.getPhone().equals(tSyUser.getPhone())){
                    TPlattUser plattUserInfo=this.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getPhone,tSyUser.getPhone()));
                    if(null!=plattUserInfo){
                        return new DataResult(1,"该手机号已有人注册使用");
                    }
                }
            }else{
                TPlattUser plattUserInfo=this.getOne(new QueryWrapper<TPlattUser>().lambda().eq(TPlattUser::getPhone,tSyUser.getPhone()));
                if(null!=plattUserInfo){
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
        TPlattUser plattUser = this.getById(id);
        List<TPlattRoleUser> list = plattRoleUserService.list(new QueryWrapper<TPlattRoleUser>().lambda().eq(TPlattRoleUser::getPlattUserId, id).eq(TPlattRoleUser::getIFlag, 1));
        List<String> roleIds = list.stream().map(o -> o.getPlattRoleId()).collect(Collectors.toList());
        SysUserDetailDTO plattUserDetailDTO=new SysUserDetailDTO();
        BeanUtils.copyProperties(plattUser,plattUserDetailDTO);
        plattUserDetailDTO.setRoleIds(roleIds);
        return DataResult.success(plattUserDetailDTO);
    }
}
