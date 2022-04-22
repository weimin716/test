package com.gdd.ylz.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dao.TSysPermissionRoleMapper;
import com.gdd.ylz.modules.sys.dao.TSysRoleUserMapper;
import com.gdd.ylz.modules.sys.dao.TSysUserMapper;
import com.gdd.ylz.modules.sys.dto.MenuResultDTO;
import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.dto.PermissionResultDTO;
import com.gdd.ylz.modules.sys.entity.*;
import com.gdd.ylz.modules.sys.dao.TSysPermissionMapper;
import com.gdd.ylz.modules.sys.service.ITSysPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.result.DataResult;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
@Service
public class TSysPermissionServiceImpl extends ServiceImpl<TSysPermissionMapper, TSysPermission> implements ITSysPermissionService {
    @Autowired
    private TSysPermissionMapper sysPermissionMapper;
    @Autowired
    private TSysRoleUserMapper tSysRoleUserMapper;
    @Autowired
    private TSysPermissionRoleMapper sysPermissionRoleMapper;
    @Autowired
    private TSysUserMapper sysUserMapper;

    @Override
    public DataResult getPermissions(String userId) {
        List<TSysPermission> permissionList=new ArrayList<>();
        List<TSysPermission> firstPermission = permissionList.stream().filter(o -> o.getPid().equals("0")).collect(Collectors.toList());
        List<String> firstPermissionIds = firstPermission.stream().map(o -> o.getId()).collect(Collectors.toList());

        //根据userId查出最顶级权限为菜单权限
        List<String> parentIds=new ArrayList<>();
        parentIds.add("0");
        while(!CollectionUtils.isEmpty(parentIds)){

        }

        return null;
    }

    @Override
    public DataResult getMenu(String userId) {
        TSysUser user = sysUserMapper.selectById(userId);
        List<TSysRoleUser> tSysRoleUsers = tSysRoleUserMapper.selectList(new QueryWrapper<TSysRoleUser>().lambda().eq(TSysRoleUser::getSysUserId, userId).eq(TSysRoleUser::getiFlag,1));
        List<String> roleids = tSysRoleUsers.stream().map(o -> o.getSysRoleId()).collect(Collectors.toList());
        List<TSysPermissionRole> tSysPermissionRoles = sysPermissionRoleMapper.selectList(new QueryWrapper<TSysPermissionRole>().lambda().eq(TSysPermissionRole::getiFlag,1).in(TSysPermissionRole::getRoleId, roleids));
        List<String> permissionids = tSysPermissionRoles.stream().map(o -> o.getPermissionId()).distinct().collect(Collectors.toList());
        List<TSysPermission> permissionList = sysPermissionMapper.selectList(new QueryWrapper<TSysPermission>().lambda().in(TSysPermission::getId, permissionids));
        List<TSysPermission> finalPermissionList = permissionList.stream().filter(o -> {
            if (o.getType() == 0 || o.getType() == 1) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<MenuResultDTO> menuResultDTOList=new ArrayList<>();
        for(TSysPermission tSysPermission:finalPermissionList){
            MenuResultDTO menuResultDTO=new MenuResultDTO();
            BeanUtils.copyProperties(tSysPermission,menuResultDTO);
            menuResultDTOList.add(menuResultDTO);
        }
        //首先找出一级菜单
        List<MenuResultDTO> firstMenu = menuResultDTOList.stream().filter(o -> o.getPid() .equals ("0")).sorted(Comparator.comparing(o->o.getOrderNum())).collect(Collectors.toList());
        dogetSonMenu(firstMenu,menuResultDTOList);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("user",user);
        map.put("menu",firstMenu);

        return new DataResult(200,"查询成功",map);
    }

    @Override
    public DataResult getPermissionsViewTree() {
        List<CheckArr> checkArrList=new ArrayList<>();
        checkArrList.add(new CheckArr("0","0"));
        List<DTree> dTreeList=new ArrayList<>();
        List<TSysPermission> permissionList = sysPermissionMapper.selectList(new QueryWrapper<TSysPermission>().lambda().eq(TSysPermission::getVisible,0).eq(TSysPermission::getiFlag,1));
       // List<TSysPermission> permissionFinalList = permissionList.stream().sorted(Comparator.comparing(TSysPermission::getOrderNum)).collect(Collectors.toList());
        for(TSysPermission sysPermission:permissionList){
            DTree dTree=new DTree();
            dTree.setId(sysPermission.getId());
            dTree.setTitle(sysPermission.getName());
            dTree.setParentId(sysPermission.getPid());
            dTree.setIconClass(sysPermission.getIcon());
            dTree.setBasicData(sysPermission.getOrderNum());
            dTree.setCheckArr(checkArrList);
            dTreeList.add(dTree);
        }
        List<DTree> firstDtreeList = dTreeList.stream().filter(o -> o.getParentId().equals("0")).sorted(Comparator.comparing(o->(int)o.getBasicData())).collect(Collectors.toList());
        dogetSonDtree(firstDtreeList,dTreeList);
        return DataResult.success(firstDtreeList);
    }

    @Override
    public DataResult getPerList(PermissionQueryDTO permissionQueryDTO) {
        Page page=new Page(permissionQueryDTO.getStart(),permissionQueryDTO.getLength());
        return DataResult.success(sysPermissionMapper.getPerList(permissionQueryDTO,page));
    }

    @Override
    @Transactional
    public DataResult deletePerandSonper(String id) {
        List<TSysPermission> list = this.list(new QueryWrapper<TSysPermission>().lambda().eq(TSysPermission::getPid, id).eq(TSysPermission::getiFlag, 1));
        list.stream().forEach(o->{
            o.setiFlag(0);
        });
        this.update(new UpdateWrapper<TSysPermission>().lambda().set(TSysPermission::getiFlag,0).eq(TSysPermission::getId,id));
        this.updateBatchById(list);
        return DataResult.success();
    }

    @Override
    public DataResult getPerDetail(String id) {
        return DataResult.success(sysPermissionMapper.getPerDetail(id));
    }

    @Override
    public DataResult getFirstPermission() {
        List<TSysPermission> sysPermissionList = this.list(new QueryWrapper<TSysPermission>().lambda().eq(TSysPermission::getPid, 0).eq(TSysPermission::getiFlag, 1));
        return DataResult.success(sysPermissionList);
    }

    @Override
    public DataResult setPermissionOrd(List<TSysPermission> sysPermissionList) {
        this.updateBatchById(sysPermissionList);
        return DataResult.success();
    }

    private void dogetSonMenu(List<MenuResultDTO> menu,List<MenuResultDTO> menuResultDTOList) {
        for(MenuResultDTO menuex:menu){
            List<MenuResultDTO> sonMenu = menuResultDTOList.stream().filter(o -> o.getPid().equals(menuex.getId())).sorted(Comparator.comparing(o->o.getOrderNum())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(sonMenu)){
                menuex.setChildren(sonMenu);
                dogetSonMenu(sonMenu,menuResultDTOList);
            }

        }
    }
    private void dogetSonDtree(List<DTree> dTrees,List<DTree> dTreeList) {
        for(DTree dtree:dTrees){
            List<DTree> sonDtree = dTreeList.stream().filter(o -> o.getParentId().equals(dtree.getId())).sorted(Comparator.comparing(o->(int)o.getBasicData())).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(sonDtree)){
                dtree.setChildren(sonDtree);
                dogetSonDtree(sonDtree,dTreeList);
                dtree.setIsLast(false);
            }else{
                dtree.setIsLast(true);
            }
        }
    }
}