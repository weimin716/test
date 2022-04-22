package com.gdd.ylz.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.common.exception.code.BaseResponseCode;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.modules.sys.dto.OrderDtreeDTO;
import com.gdd.ylz.modules.sys.dto.PermissionAddDTO;
import com.gdd.ylz.modules.sys.dto.PermissionQueryDTO;
import com.gdd.ylz.modules.sys.dto.PermissionVisibleDTO;
import com.gdd.ylz.modules.sys.entity.TSysPermission;
import com.gdd.ylz.modules.sys.entity.TSysPermissionRole;
import com.gdd.ylz.modules.sys.service.ITSysPermissionRoleService;
import com.gdd.ylz.modules.sys.service.ITSysPermissionService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/sys/t-sys-permission")
@Api(tags={"sys"})
public class TSysPermissionController extends UserController {
    @Autowired
    private ITSysPermissionService sysPermissionService;
    @Autowired
    private ITSysPermissionRoleService sysPermissionRoleService;

    @ApiOperation(value="用户权限排序",notes= "用户权限排序")
    @PostMapping("/setpermissionord")
    public DataResult setPermissionOrd(@RequestBody List<OrderDtreeDTO> dTreeList){
        List<TSysPermission> sysPermissionList=new ArrayList<>();
        for(OrderDtreeDTO dtree:dTreeList){
            TSysPermission sysPermission=new TSysPermission();
            sysPermission.setId(dtree.getNodeId());
            sysPermission.setOrderNum(dtree.getBasicData());
            sysPermissionList.add(sysPermission);
        }
        return sysPermissionService.setPermissionOrd(sysPermissionList);
    }


    @ApiOperation(value="获得用户权限",notes= "获得用户权限")
    @GetMapping("/getPermissions")
    public DataResult getPermissions(){
        return sysPermissionService.getPermissions(getUserId());
    }

    @ApiOperation(value="获得用户权限预览",notes= "获得用户权限预览")
    @GetMapping("/getPermissionsViewTree")
    public DataResult getPermissionsViewTree(){
        return sysPermissionService.getPermissionsViewTree();
    }

    @ApiOperation(value="获得用户权限列表",notes= "获得用户权限列表")
    @GetMapping("/getPermissionsList")
    public DataResult getPermissionsList(PermissionQueryDTO permissionQueryDTO){
        return sysPermissionService.getPerList(permissionQueryDTO);
    }

    @ApiOperation(value="更新用户权限",notes= "更新用户权限")
    @PostMapping("/updatepermissionvisible")
    public DataResult updatePermission(@RequestBody PermissionVisibleDTO permissionVisibleDTO){
        TSysPermission sysPermission=new TSysPermission();
        BeanUtils.copyProperties(permissionVisibleDTO,sysPermission);
        if(sysPermissionService.updateById(sysPermission)){
            return DataResult.success();
        }else{
            return DataResult.getResult(-1,"更新是否可见失败");
        }

    }

    @ApiOperation(value="增加用户权限",notes= "增加用户权限")
    @PostMapping("/addorupdatepermission")
    public DataResult updateOrupdatePermission(@RequestBody PermissionAddDTO permissionAddDTO){
        TSysPermission sysPermission=new TSysPermission();
        BeanUtils.copyProperties(permissionAddDTO,sysPermission);
        if(StringUtils.isEmpty(sysPermission.getPid())){
            sysPermission.setPid("0");
        }
        if(sysPermissionService.saveOrUpdate(sysPermission)){
            return DataResult.success();
        }else{
            return DataResult.error();
        }

    }
    @ApiOperation(value="删除用户权限",notes= "删除用户权限")
    @DeleteMapping("/remove/{id}")
    public DataResult removePermission(@PathVariable String id){
        List<TSysPermission> list = sysPermissionService.list(new QueryWrapper<TSysPermission>().lambda().eq(TSysPermission::getPid, id).eq(TSysPermission::getiFlag, 1));
        if(!CollectionUtils.isEmpty(list)){
            return DataResult.error(BaseResponseCode.PER_HAS_NEXT);
        }
        List<TSysPermissionRole> sysPermissionRoles = sysPermissionRoleService.list(new QueryWrapper<TSysPermissionRole>().lambda().eq(TSysPermissionRole::getPermissionId, id).eq(TSysPermissionRole::getiFlag, 1));
        if(!CollectionUtils.isEmpty(sysPermissionRoles)){
            return DataResult.getResult(-1,"该权限已被角色使用，请重新设置该角色的权限");
        }
        if(sysPermissionService.update(new UpdateWrapper<TSysPermission>().lambda().set(TSysPermission::getiFlag,0).eq(TSysPermission::getId,id))){
            return  DataResult.success();
        }else{
            return DataResult.error();
        }
    }

    @ApiOperation(value="删除权限及其子权限",notes= "删除权限及其子权限")
    @DeleteMapping("/deleteperandsonper/{id}")
    public DataResult deletePerandSonper(@PathVariable String id){
        return sysPermissionService.deletePerandSonper(id);
    }

    @ApiOperation(value="获取权限详情",notes= "获取权限详情")
    @GetMapping("/getperdetail")
    public DataResult getPerDetail(@RequestParam String id){
        return sysPermissionService.getPerDetail(id);
    }
}

