package com.gdd.ylz.modules.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.gdd.ylz.common.base.UserController;
import com.gdd.ylz.common.util.StringUtils;
import com.gdd.ylz.config.jwt.PasswordUtils;
import com.gdd.ylz.config.redis.RedisService;
import com.gdd.ylz.modules.sys.dto.SysUserAddDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserQueryDTO;
import com.gdd.ylz.modules.sys.dto.TSysUserUpdateDTO;
import com.gdd.ylz.modules.sys.entity.Filestore;
import com.gdd.ylz.modules.sys.entity.TPlattRoleUser;
import com.gdd.ylz.modules.sys.entity.TPlattUser;
import com.gdd.ylz.modules.sys.service.IFilestoreService;
import com.gdd.ylz.modules.sys.service.ITPlattRoleUserService;
import com.gdd.ylz.modules.sys.service.ITPlattUserService;
import com.gdd.ylz.result.DataResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * <p>
 * 平台用户表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-12-10
 */
@RestController
@RequestMapping("/sys/t-platt-user")
@Api(tags={"sys"})
public class TPlattUserController extends UserController {
    @Autowired
    private ITPlattUserService tPlattUserService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IFilestoreService filestoreService;
    @Autowired
    private ITPlattRoleUserService plattRoleUserService;



    @GetMapping("/list")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public DataResult getList(TSysUserQueryDTO tSysUserQueryDTO){
        return DataResult.success(tPlattUserService.getList(tSysUserQueryDTO));
    }

    @PutMapping("/updatewithoutvcode")
    @ApiOperation(value="修改用户信息不用验证码",notes="修改用户信息不用验证码")
    @Transactional
    public DataResult doUpateUserWithoutVcode(@RequestBody TSysUserUpdateDTO tSyUser){
        TPlattUser userInfo=tPlattUserService.getById(tSyUser.getId());
        DataResult dataResult=tPlattUserService.checkUpdateplattUserData(tSyUser);
        if(dataResult.hasError()){
            return dataResult;
        }
        TPlattUser plattUser=new TPlattUser();
        BeanUtils.copyProperties(tSyUser,plattUser);
        if(!StringUtils.isEmpty(tSyUser.getPassword())){
            String newPassword= PasswordUtils.encode(userInfo.getSalt(),plattUser.getPassword());
            plattUser.setPassword(newPassword);
        }
        //查询出原来的权限角色id集合
        List<TPlattRoleUser> list = plattRoleUserService.list(new QueryWrapper<TPlattRoleUser>().lambda().eq(TPlattRoleUser::getPlattUserId, tSyUser.getId()).eq(TPlattRoleUser::getIFlag, 1));
        List<String> oldRoleIds = list.stream().map(o -> o.getPlattRoleId()).collect(Collectors.toList());
        List<String> newRoleIds=null;
        if(!StringUtils.isEmpty(tSyUser.getRoleIds())){
            newRoleIds =  new ArrayList<>(Arrays.asList(tSyUser.getRoleIds().split(",")));
        }

        List<String> newRoleIdsTemp=new ArrayList<>();
        List<TPlattRoleUser> plattRoleUserList=new ArrayList<>();
        if(!CollectionUtils.isEmpty(newRoleIds)){
            newRoleIds.forEach(o->{
                newRoleIdsTemp.add(o);
            });
        }
        if(!CollectionUtils.isEmpty(oldRoleIds)&&!CollectionUtils.isEmpty(newRoleIds)){
            newRoleIds.removeAll(oldRoleIds);
            oldRoleIds.removeAll(newRoleIdsTemp);
        }
        if(!CollectionUtils.isEmpty(newRoleIds)){
            newRoleIds.forEach(o->{
                TPlattRoleUser plattRoleUser=new TPlattRoleUser();
                plattRoleUser.setPlattUserId(tSyUser.getId());
                plattRoleUser.setPlattRoleId(o);
                plattRoleUserList.add(plattRoleUser);
            });
        }
        //修改
        if(!CollectionUtils.isEmpty(oldRoleIds)){
            plattRoleUserService.update(new UpdateWrapper<TPlattRoleUser>().lambda().set(TPlattRoleUser::getIFlag,0).eq(TPlattRoleUser::getPlattUserId,tSyUser.getId()).in(TPlattRoleUser::getPlattRoleId,oldRoleIds));
        }
        plattRoleUserService.saveBatch(plattRoleUserList);
        tPlattUserService.updateById(plattUser);
        return DataResult.success();
    }

    @PutMapping("/updatewithvcode")
    @ApiOperation(value="修改用户信息用验证码",notes="修改用户信息用验证码")
    public DataResult doUpateUserWithVcode(@RequestBody TSysUserUpdateDTO tSyUser){
        //如果更换手机号，短信验证码将发到新的手机上
        TPlattUser plattUser = tPlattUserService.getById(tSyUser.getId());
        if(!StringUtils.isEmpty(tSyUser.getPhone())&&!tSyUser.getPhone().equals(plattUser.getPhone())){
            if(!redisService.get(tSyUser.getPhone()).equals(tSyUser.getVcode())){
                return new DataResult(-1,"手机验证码不正确!");
            }

        }else{
            if(!redisService.get(plattUser.getPhone()).equals(tSyUser.getVcode())){
                return new DataResult(-1,"手机验证码不正确!");
            }
        }
        DataResult dataResult=tPlattUserService.checkUpdateplattUserData(tSyUser);
        if(dataResult.hasError()){
            return dataResult;
        }
        TPlattUser plattUserForUpdate=new TPlattUser();
        BeanUtils.copyProperties(tSyUser,plattUserForUpdate);
        if(!StringUtils.isEmpty(tSyUser.getPassword())){
            String newPassword= PasswordUtils.encode(plattUser.getSalt(),plattUserForUpdate.getPassword());
            plattUserForUpdate.setPassword(newPassword);
        }
        tPlattUserService.updateById(plattUserForUpdate);
        return DataResult.success();
    }
    @GetMapping("/getcurrentuserinfo")
    @ApiOperation(value="获取当前用户信息",notes="获取当前用户信息")
    public DataResult getCurrentUserinfo(){
        return DataResult.success(tPlattUserService.getById(getUserId()));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="删除用户",notes="删除用户")
    @Transactional
    public DataResult deleteUser(@PathVariable("id") String id){
        //校验模块
        /**
         * TODO 什么情况下能删除，用户是否被使用
         */
        if(!tPlattUserService.checkUserDeleteable(id)){
            return new DataResult(-1,"该用户正在使用中，不能删除");
        }
        plattRoleUserService.update(new UpdateWrapper<TPlattRoleUser>().lambda().set(TPlattRoleUser::getIFlag,0).eq(TPlattRoleUser::getPlattUserId,id));
        tPlattUserService.update(new UpdateWrapper<TPlattUser>().lambda().set(TPlattUser::getiFlag,0).eq(TPlattUser::getId,id));
        return DataResult.success();
    }

    @DeleteMapping("/batchdelete")
    @ApiOperation(value="批量删除用户",notes="批量删除用户")
    @Transactional
    public DataResult batchDeleteUser(@RequestParam("ids") String ids){
        if(StringUtils.isEmpty(ids)){
            return new DataResult(-1,"你并未选中任何用户");
        }
        List<String> idList = Arrays.asList(ids.split(","));
        //校验模块
        /**
         * TODO 什么情况下能删除，用户是否被使用
         */
        for(String id:idList){
            if(!tPlattUserService.checkUserDeleteable(id)){
                TPlattUser plattUser = tPlattUserService.getById(id);
                return new DataResult(-1,"用户账号为"+plattUser.getUsername()+"该用户正在使用中，不能删除");
            }
        }

        plattRoleUserService.update(new UpdateWrapper<TPlattRoleUser>().lambda().set(TPlattRoleUser::getIFlag,0).in(TPlattRoleUser::getPlattUserId,idList));
        tPlattUserService.update(new UpdateWrapper<TPlattUser>().lambda().set(TPlattUser::getiFlag,0).in(TPlattUser::getId,idList));
        return DataResult.success();
    }

    @PostMapping("/add")
    @ApiOperation(value="增加用户",notes="增加用户")
    public DataResult addUser(@RequestBody TPlattUser plattUser){
        TSysUserUpdateDTO tplattUserUpdateDTO=new TSysUserUpdateDTO();
        BeanUtils.copyProperties(plattUser,tplattUserUpdateDTO);
        DataResult dataResult=tPlattUserService.checkUpdateplattUserData(tplattUserUpdateDTO);
        if(dataResult.hasError()){
            return dataResult;
        }
        /**
         * 凡是页面系统管理员新增的用户密码是888888
         */
        String salt=PasswordUtils.getSalt();
        plattUser.setPassword(PasswordUtils.encode("888888",salt));
        plattUser.setSalt(salt);
        List<Filestore> list = filestoreService.list(new QueryWrapper<Filestore>().lambda().eq(Filestore::getIsAvatar, 1));
        Random random = new Random();
        int n = random.nextInt(list.size());
        plattUser.setAvatar(list.get(n).getFilePath());
        tPlattUserService.save(plattUser);
        return DataResult.success();
    }


    @PostMapping("/adduser")
    @ApiOperation(value="增加用户",notes="增加用户")
    @Transactional
    public DataResult add(@RequestBody SysUserAddDTO sysUserAddDTO){
        TPlattUser plattUser=new TPlattUser();
        BeanUtils.copyProperties(sysUserAddDTO,plattUser);
        TSysUserUpdateDTO tSysUserUpdateDTO=new TSysUserUpdateDTO();
        BeanUtils.copyProperties(plattUser,tSysUserUpdateDTO);
        DataResult dataResult=tPlattUserService.checkUpdateplattUserData(tSysUserUpdateDTO);
        if(dataResult.hasError()){
            return dataResult;
        }
        String userId=StringUtils.getGUID();
        String salt=PasswordUtils.getSalt();
        plattUser.setPassword(PasswordUtils.encode(plattUser.getPassword(),salt));
        plattUser.setSalt(salt);
        List<Filestore> list = filestoreService.list(new QueryWrapper<Filestore>().lambda().eq(Filestore::getIsAvatar, 1));
        Random random = new Random();
        int n = random.nextInt(list.size());
        plattUser.setAvatar(list.get(n).getRelativePath());
        plattUser.setId(userId);
        //
        List<TPlattRoleUser> plattRoleUserList=new ArrayList<>();
        List<String> roleIdList = Arrays.asList(sysUserAddDTO.getRoleIds().split(","));
        roleIdList.forEach(o->{
            TPlattRoleUser tplattRoleUser=new TPlattRoleUser();
            tplattRoleUser.setPlattRoleId(o);
            tplattRoleUser.setPlattUserId(userId);
            plattRoleUserList.add(tplattRoleUser);
        });
        tPlattUserService.save(plattUser);
        plattRoleUserService.saveBatch(plattRoleUserList);
        return DataResult.success();
    }

    @PostMapping("/changestatusbat")
    @ApiOperation(value="批量修改用户状态",notes="批量修改用户状态")
    public DataResult changeStatusBat(@RequestBody List<String> ids){
        tPlattUserService.update(new UpdateWrapper<TPlattUser>().lambda().set(TPlattUser::getStatus,2).in(TPlattUser::getId,ids));
        return DataResult.success();
    }

    @GetMapping("/getdetail")
    @ApiOperation(value="查询用户详细信息",notes="查询用户详细信息")
    public DataResult getUserDetail(@RequestParam String id){
        return tPlattUserService.getDetail(id);
    }
}

