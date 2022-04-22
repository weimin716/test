package com.gdd.ylz.modules.sys.controller;


import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.modules.sys.service.ITPlattPermissionService;
import com.gdd.ylz.modules.sys.service.ITSysPermissionService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/t-platt-menu")
@Api(tags={"sys"})
@CrossOrigin
public class PlattMenuController extends UserController {

    @Autowired
    private ITPlattPermissionService plattPermissionService;


    @ApiOperation(value="获取菜单",notes = "获取菜单")
    @PostMapping("/getmenu")
    public DataResult getMenu(){
        return plattPermissionService.getMenu(getUserId());
    }
}
