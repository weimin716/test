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
import com.gdd.ylz.modules.sys.entity.TPlattPermission;
import com.gdd.ylz.modules.sys.entity.TSysPermission;
import com.gdd.ylz.modules.sys.service.ITPlattPermissionService;
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
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/sys/t-platt-permission")
@Api(tags={"sys"})
public class TPlattPermissionController extends UserController {

    @Autowired
    private ITPlattPermissionService plattPermissionService;


    @ApiOperation(value="用户权限排序",notes= "用户权限排序")
    @PostMapping("/setpermissionord")
    public DataResult setPermissionOrd(@RequestBody List<OrderDtreeDTO> dTreeList){
        List<TPlattPermission> plattPermissionList=new ArrayList<>();
        for(OrderDtreeDTO dtree:dTreeList){
            TPlattPermission palttPermission=new TPlattPermission();
            palttPermission.setId(dtree.getNodeId());
            palttPermission.setOrderNum(dtree.getBasicData());
            plattPermissionList.add(palttPermission);
        }
        return plattPermissionService.setPermissionOrd(plattPermissionList);
    }

    @ApiOperation(value="获得用户权限",notes= "获得用户权限")
    @GetMapping("/getPermissions")
    public DataResult getPermissions(){
        return plattPermissionService.getPermissions(getUserId());
    }

    @ApiOperation(value="获得用户权限预览",notes= "获得用户权限预览")
    @GetMapping("/getPermissionsViewTree")
    public DataResult getPermissionsViewTree(){
        return plattPermissionService.getPermissionsViewTree();
    }

    @ApiOperation(value="获得用户权限列表",notes= "获得用户权限列表")
    @GetMapping("/getPermissionsList")
    public DataResult getPermissionsList(PermissionQueryDTO permissionQueryDTO){
        return plattPermissionService.getPerList(permissionQueryDTO);
    }

    @ApiOperation(value="更新用户权限",notes= "更新用户权限")
    @PostMapping("/updatepermissionvisible")
    public DataResult updatePermission(@RequestBody PermissionVisibleDTO permissionVisibleDTO){
        TPlattPermission tPlattPermission=new TPlattPermission();
        BeanUtils.copyProperties(permissionVisibleDTO,tPlattPermission);
        if(plattPermissionService.updateById(tPlattPermission)){
            return DataResult.success();
        }else{
            return DataResult.getResult(-1,"更新是否可见失败");
        }

    }

    @ApiOperation(value="增加用户权限",notes= "增加用户权限")
    @PostMapping("/addorupdatepermission")
    public DataResult updateOrupdatePermission(@RequestBody PermissionAddDTO permissionAddDTO){
        TPlattPermission tPlattPermission=new TPlattPermission();
        BeanUtils.copyProperties(permissionAddDTO,tPlattPermission);
        if(StringUtils.isEmpty(tPlattPermission.getPid())){
            tPlattPermission.setPid("0");
        }
        if(plattPermissionService.saveOrUpdate(tPlattPermission)){
            return DataResult.success();
        }else{
            return DataResult.error();
        }

    }
    @ApiOperation(value="删除用户权限",notes= "删除用户权限")
    @DeleteMapping("/remove/{id}")
    public DataResult removePermission(@PathVariable String id){
        List<TPlattPermission> list = plattPermissionService.list(new QueryWrapper<TPlattPermission>().lambda().eq(TPlattPermission::getPid, id).eq(TPlattPermission::getiFlag, 1));
        if(!CollectionUtils.isEmpty(list)){
            return DataResult.error(BaseResponseCode.PER_HAS_NEXT);
        }
        if(plattPermissionService.update(new UpdateWrapper<TPlattPermission>().lambda().set(TPlattPermission::getiFlag,0).eq(TPlattPermission::getId,id))){
            return  DataResult.success();
        }else{
            return DataResult.error();
        }
    }

    @ApiOperation(value="删除权限及其子权限",notes= "删除权限及其子权限")
    @DeleteMapping("/deleteperandsonper/{id}")
    public DataResult deletePerandSonper(@PathVariable String id){
        return plattPermissionService.deletePerandSonper(id);
    }

    @ApiOperation(value="获取权限详情",notes= "获取权限详情")
    @GetMapping("/getperdetail")
    public DataResult getPerDetail(@RequestParam String id){
        return plattPermissionService.getPerDetail(id);
    }





}

