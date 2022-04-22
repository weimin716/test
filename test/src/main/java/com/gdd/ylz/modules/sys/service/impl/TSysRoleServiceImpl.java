package com.gdd.ylz.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.common.exception.BusinessException;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.modules.sys.dao.TSysPermissionRoleMapper;
import com.gdd.ylz.modules.sys.dto.RoleAddDTO;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.dto.RoleReDTO;
import com.gdd.ylz.modules.sys.entity.TSysPermissionRole;
import com.gdd.ylz.modules.sys.entity.TSysRole;
import com.gdd.ylz.modules.sys.dao.TSysRoleMapper;
import com.gdd.ylz.modules.sys.entity.TSysRoleUser;
import com.gdd.ylz.modules.sys.service.ITSysPermissionRoleService;
import com.gdd.ylz.modules.sys.service.ITSysPermissionService;
import com.gdd.ylz.modules.sys.service.ITSysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.modules.sys.service.ITSysRoleUserService;
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
 * 角色表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
@Service
public class TSysRoleServiceImpl extends ServiceImpl<TSysRoleMapper, TSysRole> implements ITSysRoleService {
    @Autowired
    private TSysRoleMapper sysRoleMapper;
    @Autowired
    private TSysPermissionRoleMapper sysPermissionRoleMapper;
    @Autowired
    private ITSysPermissionRoleService sysPermissionRoleService;
    @Autowired
    private ITSysRoleUserService sysRoleUserService;

    @Override
    public DataResult getRoleList(RoleQueryDTO roleQueryDTO) {
        Page page=new Page(roleQueryDTO.getStart(),roleQueryDTO.getLength());
        return DataResult.success(sysRoleMapper.getRoleList(page,roleQueryDTO));
    }

    @Override
    @Transactional
    public DataResult addRole(RoleAddDTO roleAddDTO) {
        TSysRole sysRole=new TSysRole();
        BeanUtils.copyProperties(roleAddDTO,sysRole);
        String roleId=StringUtils.getGUID();
        sysRole.setId(roleId);
        List<TSysPermissionRole> sysPerRoleList=new ArrayList<>();
        roleAddDTO.getPerIds().forEach(o->{
            TSysPermissionRole sysPermissionRole=new TSysPermissionRole();
            sysPermissionRole.setRoleId(roleId);
            sysPermissionRole.setPermissionId(o);
            sysPerRoleList.add(sysPermissionRole);
        });
        //插入操作
        //插入系统角色
        sysRoleMapper.insert(sysRole);
        //插入系统权限角色关系
        sysPermissionRoleService.saveBatch(sysPerRoleList);
        return DataResult.success();
    }

    @Override
    public void checkRoleDeleteable(String id) {
        List<TSysRoleUser> list = sysRoleUserService.list(new QueryWrapper<TSysRoleUser>().lambda().eq(TSysRoleUser::getSysRoleId, id).eq(TSysRoleUser::getiFlag,1));
        if(!CollectionUtils.isEmpty(list)){
            throw new BusinessException(-1,"该角色已用户被使用，请先删除相关用户");
        }

    }

    @Override
    @Transactional
    public DataResult removeRole(String id) {
        //删除角色
        this.update(new UpdateWrapper<TSysRole>().lambda().set(TSysRole::getiFlag,0).eq(TSysRole::getId,id));
        //删除权限角色关系
        sysPermissionRoleService.update(new UpdateWrapper<TSysPermissionRole>().lambda().set(TSysPermissionRole::getiFlag,0).eq(TSysPermissionRole::getRoleId,id));
        return DataResult.success();
    }

    @Override
    @Transactional
    public DataResult updateSysRole(RoleAddDTO roleAddDTO) {
        TSysRole sysRole=new TSysRole();
        BeanUtils.copyProperties(roleAddDTO,sysRole);
        //查询出原来的权限角色id集合
        List<TSysPermissionRole> list = sysPermissionRoleService.list(new QueryWrapper<TSysPermissionRole>().lambda().eq(TSysPermissionRole::getRoleId, roleAddDTO.getId()).eq(TSysPermissionRole::getiFlag, 1));
        List<String> oldPerIds = list.stream().map(o -> o.getPermissionId()).collect(Collectors.toList());
        List<String> newPerIds = roleAddDTO.getPerIds();
        List<String> newPerIdsTemp=new ArrayList<>();
        List<TSysPermissionRole> sysPermissionRoleList=new ArrayList<>();
        newPerIds.forEach(o->{
            newPerIdsTemp.add(o);
        });
        if(!CollectionUtils.isEmpty(oldPerIds)&&!CollectionUtils.isEmpty(newPerIds)){
            newPerIds.removeAll(oldPerIds);
            oldPerIds.removeAll(newPerIdsTemp);
        }
        newPerIds.forEach(o->{
            TSysPermissionRole sysPermissionRole=new TSysPermissionRole();
            sysPermissionRole.setRoleId(roleAddDTO.getId());
            sysPermissionRole.setPermissionId(o);
            sysPermissionRoleList.add(sysPermissionRole);
        });
        //修改
        if(!CollectionUtils.isEmpty(oldPerIds)){
            sysPermissionRoleService.update(new UpdateWrapper<TSysPermissionRole>().lambda().set(TSysPermissionRole::getiFlag,0).eq(TSysPermissionRole::getRoleId,roleAddDTO.getId()).in(TSysPermissionRole::getPermissionId,oldPerIds));
        }
        sysPermissionRoleService.saveBatch(sysPermissionRoleList);
        if(!StringUtils.isEmpty(sysRole.getName())){
            this.updateById(sysRole);
        }
        return DataResult.success();
    }

    @Override
    public DataResult getRoleDetail(String id) {
        List<TSysPermissionRole> list = sysPermissionRoleService.list(new QueryWrapper<TSysPermissionRole>().lambda().eq(TSysPermissionRole::getRoleId, id).eq(TSysPermissionRole::getiFlag, 1));
        List<String> perIds = list.stream().map(o -> o.getPermissionId()).collect(Collectors.toList());
        RoleReDTO roleDetail = sysRoleMapper.getRoleDetail(id);
        roleDetail.setPerIds(perIds);
        return DataResult.success(roleDetail);
    }
}
