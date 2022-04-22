package com.gdd.ylz.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gdd.ylz.modules.sys.dao.*;
import com.gdd.ylz.modules.sys.dto.MenuResultDTO;
import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.entity.*;
import com.gdd.ylz.modules.sys.service.ITPlattPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gdd.ylz.result.DataResult;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
@Service
public class TPlattPermissionServiceImpl extends ServiceImpl<TPlattPermissionMapper, TPlattPermission> implements ITPlattPermissionService {

    @Autowired
    private TPlattPermissionMapper plattPermissionMapper;
    @Autowired
    private TPlattRoleUserMapper plattRoleUserMapper;
    @Autowired
    private TPlattUserMapper plattUserMapper;
    @Autowired
    private TPlattPermissionRoleMapper plattPermissionRoleMapper;

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
        TPlattUser user = plattUserMapper.selectById(userId);
        List<TPlattRoleUser> tPlattRoleUsers = plattRoleUserMapper.selectList(new QueryWrapper<TPlattRoleUser>().lambda().eq(TPlattRoleUser::getPlattUserId, userId));
        List<String> roleids = tPlattRoleUsers.stream().map(o -> o.getPlattRoleId()).collect(Collectors.toList());
        List<TPlattPermissionRole> tPlattPermissionRoles = plattPermissionRoleMapper.selectList(new QueryWrapper<TPlattPermissionRole>().lambda().in(TPlattPermissionRole::getRoleId, roleids));
        List<String> permissionids = tPlattPermissionRoles.stream().map(o -> o.getPermissionId()).distinct().collect(Collectors.toList());
        List<TPlattPermission> permissionList = plattPermissionMapper.selectList(new QueryWrapper<TPlattPermission>().lambda().in(TPlattPermission::getId, permissionids));
        List<TPlattPermission> finalPermissionList = permissionList.stream().filter(o -> {
            if (o.getType() == 0 || o.getType() == 1) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        List<MenuResultDTO> menuResultDTOList=new ArrayList<>();
        for(TPlattPermission tPlattPermission:finalPermissionList){
            MenuResultDTO menuResultDTO=new MenuResultDTO();
            BeanUtils.copyProperties(tPlattPermission,menuResultDTO);
            menuResultDTOList.add(menuResultDTO);
        }
        //首先找出一级菜单
        List<MenuResultDTO> firstMenu = menuResultDTOList.stream().filter(o -> o.getPid() .equals ("0")).sorted(Comparator.comparing(o->(int)o.getOrderNum())).collect(Collectors.toList());
        dogetSonMenu(firstMenu,menuResultDTOList);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("user",user);
        map.put("menu",firstMenu);

        return new DataResult(200,"查询成功",map);
    }

    @Override
    public DataResult setPermissionOrd(List<TPlattPermission> plattPermissionList) {
        this.updateBatchById(plattPermissionList);
        return DataResult.success();
    }

    @Override
    public DataResult getPermissionsViewTree() {
        List<CheckArr> checkArrList=new ArrayList<>();
        checkArrList.add(new CheckArr("0","0"));
        List<DTree> dTreeList=new ArrayList<>();
        List<TPlattPermission> permissionList = plattPermissionMapper.selectList(new QueryWrapper<TPlattPermission>().lambda().eq(TPlattPermission::getVisible,0).eq(TPlattPermission::getiFlag,1));
        // List<TSysPermission> permissionFinalList = permissionList.stream().sorted(Comparator.comparing(TSysPermission::getOrderNum)).collect(Collectors.toList());
        for(TPlattPermission plattPermission:permissionList){
            DTree dTree=new DTree();
            dTree.setId(plattPermission.getId());
            dTree.setTitle(plattPermission.getName());
            dTree.setParentId(plattPermission.getPid());
            dTree.setIconClass(plattPermission.getIcon());
            dTree.setCheckArr(checkArrList);
            dTree.setBasicData(plattPermission.getOrderNum());
            dTreeList.add(dTree);
        }
        List<DTree> firstDtreeList = dTreeList.stream().filter(o -> o.getParentId().equals("0")).sorted(Comparator.comparing(o->(int)o.getBasicData())).collect(Collectors.toList());
        dogetSonDtree(firstDtreeList,dTreeList);
        return DataResult.success(firstDtreeList);
    }

    @Override
    public DataResult getPerList(PermissionQueryDTO permissionQueryDTO) {
        Page page=new Page(permissionQueryDTO.getStart(),permissionQueryDTO.getLength());
        return DataResult.success(plattPermissionMapper.getPerList(permissionQueryDTO,page));
    }

    @Override
    @Transactional
    public DataResult deletePerandSonper(String id) {
        List<TPlattPermission> list = this.list(new QueryWrapper<TPlattPermission>().lambda().eq(TPlattPermission::getPid, id).eq(TPlattPermission::getiFlag, 1));
        list.stream().forEach(o->{
            o.setiFlag(0);
        });
        this.update(new UpdateWrapper<TPlattPermission>().lambda().set(TPlattPermission::getiFlag,0).eq(TPlattPermission::getId,id));
        this.updateBatchById(list);
        return DataResult.success();
    }

    @Override
    public DataResult getPerDetail(String id) {
        return DataResult.success(plattPermissionMapper.getPerDetail(id));
    }

    private void dogetSonMenu(List<MenuResultDTO> menu,List<MenuResultDTO> menuResultDTOList) {
        for(MenuResultDTO menuex:menu){
            List<MenuResultDTO> sonMenu = menuResultDTOList.stream().filter(o -> o.getPid().equals(menuex.getId())).sorted(Comparator.comparing(o->(int)o.getOrderNum())).collect(Collectors.toList());
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
