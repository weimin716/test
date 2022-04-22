package com.gdd.ylz.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.common.util.StringUtils;

import com.gdd.ylz.modules.sys.dao.TPlattPermissionRoleMapper;
import com.gdd.ylz.modules.sys.dao.TPlattRoleMapper;
import com.gdd.ylz.modules.sys.dto.RoleAddDTO;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.dto.RoleReDTO;
import com.gdd.ylz.modules.sys.entity.TPlattPermissionRole;
import com.gdd.ylz.modules.sys.entity.TPlattRole;
import com.gdd.ylz.modules.sys.entity.TPlattRoleUser;
import com.gdd.ylz.modules.sys.service.ITPlattPermissionRoleService;
import com.gdd.ylz.modules.sys.service.ITPlattRoleService;
import com.gdd.ylz.modules.sys.service.ITPlattRoleUserService;
import com.gdd.ylz.result.DataResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台角色表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
@Service
public class TPlattRoleServiceImpl extends ServiceImpl<TPlattRoleMapper, TPlattRole> implements ITPlattRoleService {
    @Autowired
    private TPlattRoleMapper plattRoleMapper;
    @Autowired
    private TPlattPermissionRoleMapper plattPermissionRoleMapper;
    @Autowired
    private ITPlattPermissionRoleService plattPermissionRoleService;
    @Autowired
    private ITPlattRoleUserService plattRoleUserService;

    @Override
    public DataResult getRoleList(RoleQueryDTO roleQueryDTO) {
        Page page=new Page(roleQueryDTO.getStart(),roleQueryDTO.getLength());
        return DataResult.success(plattRoleMapper.getRoleList(page,roleQueryDTO));
    }

    @Override
    @Transactional
    public DataResult addRole(RoleAddDTO roleAddDTO) {
        TPlattRole PlattRole=new TPlattRole();
        BeanUtils.copyProperties(roleAddDTO,PlattRole);
        String roleId=StringUtils.getGUID();
        PlattRole.setId(roleId);
        List<TPlattPermissionRole> PlattPerRoleList=new ArrayList<>();
        roleAddDTO.getPerIds().forEach(o->{
            TPlattPermissionRole PlattPermissionRole=new TPlattPermissionRole();
            PlattPermissionRole.setRoleId(roleId);
            PlattPermissionRole.setPermissionId(o);
            PlattPerRoleList.add(PlattPermissionRole);
        });
        //插入操作
        //插入系统角色
        plattRoleMapper.insert(PlattRole);
        //插入系统权限角色关系
        plattPermissionRoleService.saveBatch(PlattPerRoleList);
        return DataResult.success();
    }

    @Override
    public void checkRoleDeleteable(String id) {
        List<TPlattRoleUser> list = plattRoleUserService.list(new QueryWrapper<TPlattRoleUser>().lambda().eq(TPlattRoleUser::getPlattRoleId, id).eq(TPlattRoleUser::getIFlag,1));
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException(-1,"该角色已用户被使用，请先删除相关用户");
        }

    }

    @Override
    @Transactional
    public DataResult removeRole(String id) {
        //删除角色
        this.update(new UpdateWrapper<TPlattRole>().lambda().set(TPlattRole::getiFlag,0).eq(TPlattRole::getId,id));
        //删除权限角色关系
        plattPermissionRoleService.update(new UpdateWrapper<TPlattPermissionRole>().lambda().set(TPlattPermissionRole::getiFlag,0).eq(TPlattPermissionRole::getRoleId,id));
        return DataResult.success();
    }


    @Override
    @Transactional
    public DataResult updatePlattRole(RoleAddDTO roleAddDTO) {
        TPlattRole plattRole=new TPlattRole();
        BeanUtils.copyProperties(roleAddDTO,plattRole);
        //查询出原来的权限角色id集合
        List<TPlattPermissionRole> list = plattPermissionRoleService.list(new QueryWrapper<TPlattPermissionRole>().lambda().eq(TPlattPermissionRole::getRoleId, roleAddDTO.getId()).eq(TPlattPermissionRole::getiFlag, 1));
        List<String> oldPerIds = list.stream().map(o -> o.getPermissionId()).collect(Collectors.toList());
        List<String> newPerIds = roleAddDTO.getPerIds();
        List<String> newPerIdsTemp=new ArrayList<>();
        List<TPlattPermissionRole> PlattPermissionRoleList=new ArrayList<>();
        newPerIds.forEach(o->{
            newPerIdsTemp.add(o);
        });
        if(!CollectionUtils.isEmpty(oldPerIds)){
            newPerIds.removeAll(oldPerIds);
            oldPerIds.removeAll(newPerIdsTemp);
        }
        newPerIds.forEach(o->{
            TPlattPermissionRole PlattPermissionRole=new TPlattPermissionRole();
            PlattPermissionRole.setRoleId(roleAddDTO.getId());
            PlattPermissionRole.setPermissionId(o);
            PlattPermissionRoleList.add(PlattPermissionRole);
        });
        //修改
        if(!CollectionUtils.isEmpty(oldPerIds)){
            plattPermissionRoleService.update(new UpdateWrapper<TPlattPermissionRole>().lambda().set(TPlattPermissionRole::getiFlag,0).eq(TPlattPermissionRole::getRoleId,roleAddDTO.getId()).in(TPlattPermissionRole::getPermissionId,oldPerIds));
        }
        plattPermissionRoleService.saveBatch(PlattPermissionRoleList);
        if(!StringUtils.isEmpty(plattRole.getName())){
            this.updateById(plattRole);
        }
        return DataResult.success();
    }

    @Override
    public DataResult getRoleDetail(String id) {
        List<TPlattPermissionRole> list = plattPermissionRoleService.list(new QueryWrapper<TPlattPermissionRole>().lambda().eq(TPlattPermissionRole::getRoleId, id).eq(TPlattPermissionRole::getiFlag, 1));
        List<String> perIds = list.stream().map(o -> o.getPermissionId()).collect(Collectors.toList());
        RoleReDTO roleDetail = plattRoleMapper.getRoleDetail(id);
        roleDetail.setPerIds(perIds);
        return DataResult.success(roleDetail);
    }
}
