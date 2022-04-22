package com.gdd.ylz.modules.sys.controller;


import com.gdd.ylz.modules.sys.dto.RoleAddDTO;
import com.gdd.ylz.modules.sys.dto.RoleQueryDTO;
import com.gdd.ylz.modules.sys.service.ITPlattRoleService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 平台角色表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/sys/t-platt-role")
@Api(tags = {"sys"})
public class TPlattRoleController {

    @Autowired
    private ITPlattRoleService plattRoleService;


    @ApiOperation(value = "获得系统角色列表不分页", notes = "获得系统角色列表不分页")
    @GetMapping("/getrolelists")
    public DataResult getRoleLists() {
        return DataResult.success(plattRoleService.list());
    }

    @ApiOperation(value = "获得系统角色列表", notes = "获得系统角色列表")
    @GetMapping("/getrolelist")
    public DataResult getRoleList(RoleQueryDTO roleQueryDTO) {
        return plattRoleService.getRoleList(roleQueryDTO);
    }

    @ApiOperation(value = "新增用户角色", notes = "新增用户角色")
    @PostMapping("/addrole")
    public DataResult addRole(@RequestBody RoleAddDTO roleAddDTO) {
        return plattRoleService.addRole(roleAddDTO);
    }

    @ApiOperation(value = "删除用户角色", notes = "删除用户角色")
    @DeleteMapping("/remove/{id}")
    public DataResult removeRole(@PathVariable("id") String id) {
        //是否可删除
        //凡是已经被用户使用的角色不可删除
        plattRoleService.checkRoleDeleteable(id);
        return plattRoleService.removeRole(id);
    }

    @ApiOperation(value = "修改用户角色", notes = "修改用户角色")
    @PutMapping("/update")
    public DataResult updateRole(@RequestBody RoleAddDTO roleAddDTO) {
        return plattRoleService.updatePlattRole(roleAddDTO);
    }

    @ApiOperation(value = "获取权限详情", notes = "获取权限详情")
    @GetMapping("/getroledetail")
    public DataResult getRoleDetail(@RequestParam String id) {
        return plattRoleService.getRoleDetail(id);
    }


}

