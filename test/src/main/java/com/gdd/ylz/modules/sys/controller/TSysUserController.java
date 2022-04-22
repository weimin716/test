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
import com.gdd.ylz.modules.sys.entity.TSysRoleUser;
import com.gdd.ylz.modules.sys.entity.TSysUser;
import com.gdd.ylz.modules.sys.service.IFilestoreService;
import com.gdd.ylz.modules.sys.service.ITSysRoleUserService;
import com.gdd.ylz.modules.sys.service.ITSysUserService;
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
 * 用户信息表 前端控制器
 * </p>
 *
 * @author xzg
 * @since 2021-10-10
 */
@RestController
@RequestMapping("/sys/t-sys-user")
@Api(tags={"sys"})
@CrossOrigin
public class TSysUserController extends UserController {
    @Autowired
    private ITSysUserService tSysUserService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private IFilestoreService filestoreService;
    @Autowired
    private ITSysRoleUserService sysRoleUserService;



    @GetMapping("/list")
    @ApiOperation(value = "用户列表", notes = "用户列表")
    public DataResult getList( TSysUserQueryDTO tSysUserQueryDTO){
        return DataResult.success(tSysUserService.getList(tSysUserQueryDTO));
    }

    @PutMapping("/updatewithoutvcode")
    @ApiOperation(value="修改用户信息不用验证码",notes="修改用户信息不用验证码")
    @Transactional
    public DataResult doUpateUserWithoutVcode(@RequestBody TSysUserUpdateDTO tSyUser){
        TSysUser userInfo=tSysUserService.getById(tSyUser.getId());
        DataResult dataResult=tSysUserService.checkUpdateSysUserData(tSyUser);
        if(dataResult.hasError()){
            return dataResult;
        }
        TSysUser sysUser=new TSysUser();
        BeanUtils.copyProperties(tSyUser,sysUser);
        if(!StringUtils.isEmpty(tSyUser.getPassword())){
           String newPassword= PasswordUtils.encode(userInfo.getSalt(),sysUser.getPassword());
           sysUser.setPassword(newPassword);
        }
        //查询出原来的权限角色id集合
        List<TSysRoleUser> list = sysRoleUserService.list(new QueryWrapper<TSysRoleUser>().lambda().eq(TSysRoleUser::getSysUserId, tSyUser.getId()).eq(TSysRoleUser::getiFlag, 1));
        List<String> oldRoleIds = list.stream().map(o -> o.getSysRoleId()).collect(Collectors.toList());
        List<String> newRoleIds=null;
        if(!StringUtils.isEmpty(tSyUser.getRoleIds())){
             newRoleIds =  new ArrayList<>(Arrays.asList(tSyUser.getRoleIds().split(",")));
        }
        List<String> newRoleIdsTemp=new ArrayList<>();
        List<TSysRoleUser> sysRoleUserList=new ArrayList<>();
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
                TSysRoleUser sysRoleUser=new TSysRoleUser();
                sysRoleUser.setSysUserId(tSyUser.getId());
                sysRoleUser.setSysRoleId(o);
                sysRoleUserList.add(sysRoleUser);
            });
        }
        //修改
        if(!CollectionUtils.isEmpty(oldRoleIds)){
            sysRoleUserService.update(new UpdateWrapper<TSysRoleUser>().lambda().set(TSysRoleUser::getiFlag,0).eq(TSysRoleUser::getSysUserId,tSyUser.getId()).in(TSysRoleUser::getSysRoleId,oldRoleIds));
        }
        sysRoleUserService.saveBatch(sysRoleUserList);
        tSysUserService.updateById(sysUser);
        return DataResult.success();
    }

    @PutMapping("/updatewithvcode")
    @ApiOperation(value="修改用户信息用验证码",notes="修改用户信息用验证码")
    public DataResult doUpateUserWithVcode(@RequestBody TSysUserUpdateDTO tSyUser){
        //如果更换手机号，短信验证码将发到新的手机上
        TSysUser sysUser = tSysUserService.getById(tSyUser.getId());
        if(!StringUtils.isEmpty(tSyUser.getPhone())&&!tSyUser.getPhone().equals(sysUser.getPhone())){
              if(!redisService.get(tSyUser.getPhone()).equals(tSyUser.getVcode())){
                  return new DataResult(-1,"手机验证码不正确!");
            }

        }else{
            if(!redisService.get(sysUser.getPhone()).equals(tSyUser.getVcode())){
                return new DataResult(-1,"手机验证码不正确!");
            }
        }
        DataResult dataResult=tSysUserService.checkUpdateSysUserData(tSyUser);
        if(dataResult.hasError()){
            return dataResult;
        }
        TSysUser sysUserForUpdate=new TSysUser();
        BeanUtils.copyProperties(tSyUser,sysUserForUpdate);
        if(!StringUtils.isEmpty(tSyUser.getPassword())){
            String newPassword= PasswordUtils.encode(sysUser.getSalt(),sysUserForUpdate.getPassword());
            sysUserForUpdate.setPassword(newPassword);
        }
        tSysUserService.updateById(sysUserForUpdate);
        return DataResult.success();
    }
    @GetMapping("/getcurrentuserinfo")
    @ApiOperation(value="获取当前用户信息",notes="获取当前用户信息")
    public DataResult getCurrentUserinfo(){
       return DataResult.success(tSysUserService.getById(getUserId()));
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation(value="删除用户",notes="删除用户")
    @Transactional
    public DataResult deleteUser(@PathVariable("id") String id){
        //校验模块
        /**
         * TODO 什么情况下能删除，用户是否被使用
         */
          if(!tSysUserService.checkUserDeleteable(id)){
              return new DataResult(-1,"该用户正在使用中，不能删除");
          }
          sysRoleUserService.update(new UpdateWrapper<TSysRoleUser>().lambda().set(TSysRoleUser::getiFlag,0).eq(TSysRoleUser::getSysUserId,id));
          tSysUserService.update(new UpdateWrapper<TSysUser>().lambda().set(TSysUser::getIFlag,0).eq(TSysUser::getId,id));
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
            if(!tSysUserService.checkUserDeleteable(id)){
                TSysUser sysUser = tSysUserService.getById(id);
                return new DataResult(-1,"用户账号为"+sysUser.getUsername()+"该用户正在使用中，不能删除");
            }
        }

        sysRoleUserService.update(new UpdateWrapper<TSysRoleUser>().lambda().set(TSysRoleUser::getiFlag,0).in(TSysRoleUser::getSysUserId,idList));
        tSysUserService.update(new UpdateWrapper<TSysUser>().lambda().set(TSysUser::getIFlag,0).in(TSysUser::getId,idList));
        return DataResult.success();
    }

    @PostMapping("/add")
    @ApiOperation(value="增加用户",notes="增加用户")
    public DataResult addUser(@RequestBody TSysUser sysUser){
        TSysUserUpdateDTO tSysUserUpdateDTO=new TSysUserUpdateDTO();
        BeanUtils.copyProperties(sysUser,tSysUserUpdateDTO);
        DataResult dataResult=tSysUserService.checkUpdateSysUserData(tSysUserUpdateDTO);
        if(dataResult.hasError()){
            return dataResult;
        }
        /**
         * 凡是页面系统管理员新增的用户密码是888888
         */
        String salt=PasswordUtils.getSalt();
        sysUser.setPassword(PasswordUtils.encode("888888",salt));
        sysUser.setSalt(salt);
        List<Filestore> list = filestoreService.list(new QueryWrapper<Filestore>().lambda().eq(Filestore::getIsAvatar, 1));
        Random random = new Random();
        int n = random.nextInt(list.size());
        sysUser.setAvatar(list.get(n).getFilePath());
        tSysUserService.save(sysUser);
        return DataResult.success();
    }


    @PostMapping("/adduser")
    @ApiOperation(value="增加用户",notes="增加用户")
    @Transactional
    public DataResult add(@RequestBody SysUserAddDTO sysUserAddDTO){
        TSysUser sysUser=new TSysUser();
        BeanUtils.copyProperties(sysUserAddDTO,sysUser);
        TSysUserUpdateDTO tSysUserUpdateDTO=new TSysUserUpdateDTO();
        BeanUtils.copyProperties(sysUser,tSysUserUpdateDTO);
        DataResult dataResult=tSysUserService.checkUpdateSysUserData(tSysUserUpdateDTO);
        if(dataResult.hasError()){
            return dataResult;
        }
        String userId=StringUtils.getGUID();
        String salt=PasswordUtils.getSalt();
        sysUser.setPassword(PasswordUtils.encode(sysUser.getPassword(),salt));
        sysUser.setSalt(salt);
        List<Filestore> list = filestoreService.list(new QueryWrapper<Filestore>().lambda().eq(Filestore::getIsAvatar, 1));
        Random random = new Random();
        int n = random.nextInt(list.size());
        sysUser.setAvatar(list.get(n).getRelativePath());
        sysUser.setId(userId);
        //
        List<TSysRoleUser> sysRoleUserList=new ArrayList<>();
        List<String> roleIdList = Arrays.asList(sysUserAddDTO.getRoleIds().split(","));
        roleIdList.forEach(o->{
            TSysRoleUser tSysRoleUser=new TSysRoleUser();
            tSysRoleUser.setSysRoleId(o);
            tSysRoleUser.setSysUserId(userId);
            sysRoleUserList.add(tSysRoleUser);
        });
        tSysUserService.save(sysUser);
        sysRoleUserService.saveBatch(sysRoleUserList);
        return DataResult.success();
    }

    @PostMapping("/changestatusbat")
    @ApiOperation(value="批量修改用户状态",notes="批量修改用户状态")
    public DataResult changeStatusBat(@RequestBody List<String> ids){
        tSysUserService.update(new UpdateWrapper<TSysUser>().lambda().set(TSysUser::getStatus,2).in(TSysUser::getId,ids));
        return DataResult.success();
    }

    @GetMapping("/getdetail")
    @ApiOperation(value="查询用户详细信息",notes="查询用户详细信息")
    public DataResult getUserDetail(@RequestParam String id){
        return tSysUserService.getDetail(id);
    }
}

