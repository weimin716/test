package com.gdd.ylz.modules.sys.controller;


import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.sys.service.ITSysPermissionService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/t-sys-menu")
@Api(tags={"sys"})
@CrossOrigin
public class MenuController extends UserController {

    @Autowired
    private ITSysPermissionService sysPermissionService;


    @ApiOperation(value="获取菜单",notes = "获取菜单")
    @PostMapping("/getmenu")
    public DataResult getMenu(){
        return sysPermissionService.getMenu(getUserId());
    }
}
