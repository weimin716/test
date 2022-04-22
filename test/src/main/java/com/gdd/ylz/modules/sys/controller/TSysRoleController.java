package com.gdd.ylz.modules.sys.controller;


import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.sys.dto.RoleAddDTO;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.service.ITSysRoleService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-10-13
 */
@RestController
@RequestMapping("/sys/t-sys-role")
@Api(tags={"sys"})
public class TSysRoleController extends UserController {
    @Autowired
    private ITSysRoleService sysRoleService;


    @ApiOperation(value="获得系统角色列表不分页",notes= "获得系统角色列表不分页")
    @GetMapping("/getrolelists")
    public DataResult getRoleLists(){
        return DataResult.success(sysRoleService.list());
    }


    @ApiOperation(value="获得系统角色列表",notes= "获得系统角色列表")
    @GetMapping("/getrolelist")
    public DataResult getRoleList(RoleQueryDTO roleQueryDTO){
        return sysRoleService.getRoleList(roleQueryDTO);
    }

    @ApiOperation(value="新增用户角色",notes= "新增用户角色")
    @PostMapping("/addrole")
    public DataResult addRole(@RequestBody RoleAddDTO roleAddDTO){
        return sysRoleService.addRole(roleAddDTO);
    }

    @ApiOperation(value="删除用户角色",notes= "删除用户角色")
    @DeleteMapping("/remove/{id}")
    public DataResult removeRole(@PathVariable("id")String id){
        //是否可删除
        //凡是已经被用户使用的角色不可删除
        sysRoleService.checkRoleDeleteable(id);
        return sysRoleService.removeRole(id);
    }

    @ApiOperation(value="修改用户角色",notes= "修改用户角色")
    @PutMapping("/update")
    public DataResult updateRole(@RequestBody RoleAddDTO roleAddDTO){
        return sysRoleService.updateSysRole(roleAddDTO);
    }

    @ApiOperation(value="获取权限详情",notes= "获取权限详情")
    @GetMapping("/getroledetail")
    public DataResult getRoleDetail(@RequestParam String id){
        return sysRoleService.getRoleDetail(id);
    }




}

